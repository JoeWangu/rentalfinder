package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.utils.Constants.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RentalDetailsViewModel @Inject constructor(
    localDataSource: LocalDataSource,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val rentalId: Int =
        checkNotNull(savedStateHandle[RentalDetailsNavigationDestination.RENTALIDARG])
    val uiState: StateFlow<RenDetailsUiState> =
        localDataSource.fetchOneRentals(rentalId)
            .filterNotNull()
            .map { RenDetailsUiState(rentalDetails = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RenDetailsUiState()
            )
}

data class RenDetailsUiState(
    val rentalDetails: RentalEntity = RentalEntity(
        id = 0,
        name = "",
        image = 1,
        price = "",
        description = "",
        type = "",
        location = "",
        available = false,
        rating = 0,
        totalUnits = 0,
        datePosted = "",
        dateModified = "",
        imageUrl = "",
        imageName = ""
    )
)
