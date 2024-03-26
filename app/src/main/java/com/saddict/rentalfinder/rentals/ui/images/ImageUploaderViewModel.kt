package com.saddict.rentalfinder.rentals.ui.images

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.HttpException
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.SocketTimeoutException
import javax.inject.Inject

sealed interface UploadImageUiState {
    data object Success : UploadImageUiState
    data object Loading : UploadImageUiState
    data object NoSuccess : UploadImageUiState
    data object Error : UploadImageUiState
}

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ImageUploaderViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableSharedFlow<UploadImageUiState>()
    val uiState: SharedFlow<UploadImageUiState> = _uiState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun uploadImage(
        bitmap: Bitmap,
        uri: Uri
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(UploadImageUiState.Loading)
                    val part = createPartFromBitmap(context, bitmap, uri)
                    val upload = remoteDataSource.postImage(
                        part
                    )
                    if (upload.isSuccessful) {
                        _uiState.emit(UploadImageUiState.Success)
                    } else {
                        _uiState.emit(UploadImageUiState.NoSuccess)
                        Log.e("upload fail", "uploadImage: ${upload.errorBody()}")
                    }
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    _uiState.emit(UploadImageUiState.Error)
                } catch (e: IOException) {
                    _uiState.emit(UploadImageUiState.Error)
                } catch (e: HttpException) {
                    Log.e("error uploading", "${e.message}")
                    _uiState.emit(UploadImageUiState.Error)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.emit(UploadImageUiState.Error)
                }
            }
        }
    }

    private fun createPartFromBitmap(
        context: Context,
        bitmap: Bitmap,
        uri: Uri
    ): MultipartBody.Part {
        val fileName = getFileNameFromUri(context, uri)

        // Convert the bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()

        // Write the bytes in file
        val file = File(context.cacheDir, fileName ?: "android-file-image")
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        // Create a RequestBody instance from the file
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        // Create MultipartBody.Part using file request-body and part name
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    //    @SuppressLint("Range")
    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (it != null) {
                    if (cursor != null && it.moveToFirst()) {
                        result =
                            it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }
        return result
    }

}