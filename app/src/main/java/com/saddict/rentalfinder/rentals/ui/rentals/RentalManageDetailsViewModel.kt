package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.prop.Constants
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RentalManageDetailsViewModel @Inject constructor(
    localDataSource: LocalDataSource,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val rentalId: Int =
        checkNotNull(savedStateHandle[RentalManageDetailsNavigationDestination.RENTALIDARG])
    val uiState: StateFlow<RenDetailsUiState> =
        localDataSource.fetchOneRental(rentalId)
            .filterNotNull()
            .map { RenDetailsUiState(rentalDetails = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(Constants.TIMEOUT_MILLIS),
                initialValue = RenDetailsUiState()
            )
}