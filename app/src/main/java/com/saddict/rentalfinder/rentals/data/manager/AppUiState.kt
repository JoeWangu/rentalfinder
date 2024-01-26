package com.saddict.rentalfinder.rentals.data.manager

sealed interface AppUiState {
    data object Success: AppUiState
    data object Loading: AppUiState
    data object Error: AppUiState
}