package com.saddict.rentalfinder.rentals.ui.images

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.UriUtils
import com.saddict.rentalfinder.utils.rememberGalleryThumbnail
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ImageUploaderNavigationDestination : NavigationDestination {
    override val route: String = "imageupload"
    override val titleRes: Int = R.string.upload_image
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageUploaderScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.upload_image),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) {
        ImageUploaderBody(
            navigateUp = navigateUp,
            modifier = Modifier.padding(it)
        )
    }
}

val dimen_5dp = 5.dp
val dimen_18dp = 18.dp

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ImageUploaderBody(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    imageUploaderViewModel: ImageUploaderViewModel = hiltViewModel(),
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val ctx = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        var selectedImage by remember { mutableStateOf<Uri?>(null) }

        /** Create a remembered variable to store the loaded image bitmap
         *
         */
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var imageBitmapBitmap by remember { mutableStateOf<Bitmap?>(null) }

        /** Create a remembered variable to track whether an image is
         * loaded
         */
        var isImageLoaded by remember { mutableStateOf(false) }
//        var file by remember { mutableStateOf<File?>(null) }
        /**  Create an activity result launcher for
         * picking visual media (images in this case)
         */
        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    // Grant read URI permission to access the selected URI
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    ctx.contentResolver.takePersistableUriPermission(uri, flag)
                    selectedImage = uri

                    // Convert the URI to a Bitmap and set it as the imageBitmap
                    imageBitmap = UriUtils().uriToBitmap(ctx, it)?.asImageBitmap()
                    imageBitmapBitmap = imageBitmap?.asAndroidBitmap()

                    // Set isImageLoaded to true
                    isImageLoaded = true
                }
            }
        if (imageBitmap != null) {
            /** Create a Card composable to wrap the image and button */
            // Check if an image is loaded
            if (isImageLoaded) {
                /** Display the loaded image using the Image composable */
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(
                                RoundedCornerShape(
                                    dimen_18dp
                                )
                            )
                    )
                }
                // Add spacing
                Spacer(modifier = Modifier.height(dimen_5dp))
            }
        }
        /** Display a button with an icon to trigger the image selection */
        TextButtonWithIcon(
            onClick = {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        )

        if (imageBitmap != null) {
            ElevatedButton(
                onClick = {
                    coroutineScope.launch {
                        imageUploaderViewModel.uploadImage(
                            imageBitmapBitmap!!,
                            selectedImage!!
                        )
                        imageUploaderViewModel.uiState.collect { state ->
                            when (state) {
                                UploadImageUiState.Loading -> {
                                    ctx.toastUtil("uploading")
                                }

                                UploadImageUiState.Error -> {
                                    ctx.toastUtil("cannot upload error occured")
                                }

                                UploadImageUiState.NoSuccess -> {
                                    ctx.toastUtil("did not upload check internet")
                                }

                                is UploadImageUiState.Success -> {
                                    ctx.toastUtil("uploaded successfully")
                                    delay(1000)
                                    navigateUp()
                                }
                            }
                        }

                    }
                },
                modifier = Modifier
            ) {
                Text(text = "upload image")
            }
        }
    }
}

/**
 * A composable function that creates a button with an icon for selecting an image from the gallery.
 *
 * @param onClick The lambda expression that will be invoked when the button is clicked.
 */
@Composable
fun TextButtonWithIcon(onClick: () -> Unit) {
    // Create a Button with the specified onClick lambda
    Button(
        onClick = { onClick.invoke() }
    ) {
        // Create a Row to arrange the icon and text horizontally
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Display an icon using the rememberGalleryThumbnail() function
            Icon(
                imageVector = rememberGalleryThumbnail(),
                contentDescription = "Gallery Icon"
            )
            // Add spacing between the icon and the text
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            // Display text for the button
            Text(text = "Select image from Gallery")
        }
    }
}