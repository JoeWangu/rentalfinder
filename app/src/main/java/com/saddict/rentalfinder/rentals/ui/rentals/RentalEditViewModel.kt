package com.saddict.rentalfinder.rentals.ui.rentals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
import com.saddict.rentalfinder.utils.mapToEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface EditRentalUiState {
    data class Success(val rental: RentalResults) : EditRentalUiState
    data object Error : EditRentalUiState
    data object SuccessError : EditRentalUiState
    data object Loading : EditRentalUiState
}

@HiltViewModel
class RentalEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ViewModel() {
    var editUiState by mutableStateOf(RenEntryUiState())
        private set
    private val _uiState = MutableSharedFlow<EditRentalUiState>()
    val uiState: SharedFlow<EditRentalUiState> = _uiState

    private val rentalId: Int =
        checkNotNull(savedStateHandle[RentalEditNavigationDestination.RENTALIDARG])

    init {
        viewModelScope.launch {
            editUiState = localDataSource.fetchOneRental(rentalId)
                .filterNotNull()
                .first()
                .toRentalEditUiState(true)
        }
    }

    fun updateUiState(entryDetails: EntryDetails) {
        editUiState = RenEntryUiState(
            renEntry = entryDetails,
            isEntryValid = validateInput(entryDetails)
        )
    }

    fun updateRental() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(EditRentalUiState.Loading)
                    if (validateInput()) {
//                        Log.d("updateRentalsTag", "updateRental: ${editUiState.renEntry.toCreateRental()}")
                        val post = remoteDataSource.updateRental(
                            id = rentalId,
                            body = editUiState.renEntry.toCreateRental()
                        )
//                            remoteDataSource.postRental(entryUiState.renEntry.toCreateRental())
                        val response = post.body()
                        if (post.isSuccessful) {
                            _uiState.emit(EditRentalUiState.Success(response!!))
                            Log.d("Success Updating", response.toString())
//                            if (response != null) {
                            localDataSource.insertOneRental(response.mapToEntity())
//                            }
                        } else {
                            val errorBody = post.raw()
                            _uiState.emit(EditRentalUiState.SuccessError)
                            Log.e("update error", "post not successful $errorBody")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.emit(EditRentalUiState.Error)
                    Log.e("UpdateRentalError", "cannot post rental $e")
                }
            }
        }
    }

    private fun validateInput(entryDetails: EntryDetails = editUiState.renEntry): Boolean {
        return entryDetails.title.isNotBlank() && entryDetails.description.isNotBlank()
                && entryDetails.category.isNotBlank() && entryDetails.price.isNotBlank()
    }
}

fun RentalEntity.toEntryDetails(): EntryDetails = EntryDetails(
    image = image ?: 1,
    price = price.toString(),
    total_units = totalUnits.toString(),
    title = title,
    description = description,
    category = category,
//    location = location.toString(),
    available = available,
    isActive = isActive,
    country = countryId,
    state = stateId,
    city = cityId,
    neighborhood = neighborhoodId,
    countryName = countryName,
    stateName = stateName,
    cityName = cityName,
    neighborhoodName = neighborhoodName,
    countryCode = countryCode
)

fun RentalEntity.toRentalEditUiState(isEntryValid: Boolean = false):
        RenEntryUiState = RenEntryUiState(
    renEntry = this.toEntryDetails(),
    isEntryValid = isEntryValid
)