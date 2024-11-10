package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.prop.Constants.TIMEOUT_MILLIS
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
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
        localDataSource.fetchOneRental(rentalId)
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
        image = 1,
        price = 1.0f,
        totalUnits = 0,
        title = "",
        description = "",
        category = "",
//        location = "",
        datePosted = "",
        dateModified = "",
        timePosted = "",
        timeModified = "",
        available = true,
        isActive = true,
        authorId = 1,
        authorEmail = "",
        authorUsername = "",
        authorPhoneNumber = "",
//        authorFirstName = "",
//        authorLastName = "",
//        authorPhoneNumber = "",
//        authorAddress = "",
//        authorDob = "",
//        authorGender = "",
        imageUrl = "",
        imageName = "",
        avgRating = 0,
        author = "",
        countryName = "",
        stateName = "",
        cityName = "",
        neighborhoodName = "",
        countryId = 0,
        stateId = 0,
        cityId = 0,
        neighborhoodId = 0,
        countryCode = ""
    )
)
