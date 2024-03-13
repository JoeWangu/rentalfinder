package com.saddict.rentalfinder.rentals.ui.images

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.RentalImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface ImagesUiState {
    data class Success(val images: List<RentalImage>) : ImagesUiState
    data object Error : ImagesUiState
    data object Empty : ImagesUiState
    data object Loading : ImagesUiState
}

@HiltViewModel
class RentalImageViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    private val _uiState = MutableSharedFlow<ImagesUiState>()
    val uiState: SharedFlow<ImagesUiState> = _uiState

    fun getImages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(ImagesUiState.Loading)
                    val images = remoteDataSource.getImages(1)
                    if (images.imageList.isNotEmpty()) {
                        _uiState.emit(ImagesUiState.Success(images.imageList))
                    } else {
                        _uiState.emit(ImagesUiState.Empty)
                    }
                } catch (e: Exception) {
                    Log.d("Cannot load images", "getImages: $e")
                    _uiState.emit(ImagesUiState.Error)
                }
            }
        }
    }
}