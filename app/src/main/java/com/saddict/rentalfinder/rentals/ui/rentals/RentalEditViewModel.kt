package com.saddict.rentalfinder.rentals.ui.rentals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.network.RentalService
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
    data object Success : EditRentalUiState
    data object Error : EditRentalUiState
    data object SuccessError : EditRentalUiState
    data object Loading : EditRentalUiState
}

@HiltViewModel
class RentalEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appApi: RentalService,
    private val appDatabase: RentalDatabase
) : ViewModel() {
    var editUiState by mutableStateOf(RenEntryUiState())
        private set
    private val rentalId: Int =
        checkNotNull(savedStateHandle[RentalEditNavigationDestination.RENTALIDARG])
    private val _uiState = MutableSharedFlow<EditRentalUiState>()
    val uiState: SharedFlow<EditRentalUiState> = _uiState

    fun updateUiState(editDetails: EntryDetails) {
        editUiState = RenEntryUiState(
            renEntry = editDetails,
            isEntryValid = validateInput(editDetails)
        )
    }

    init {
        viewModelScope.launch {
            editUiState = appDatabase.rentalDao().fetchOneRentals(rentalId)
                .filterNotNull()
                .first()
                .toRentalEditUiState(true)
        }
    }

    suspend fun updateRental() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(EditRentalUiState.Loading)
                    if (validateInput()) {
                        val post = appApi.updateRental(
                            id = rentalId,
                            body = editUiState.renEntry.toCreateRental()
                        )
//                            remoteDataSource.postRental(entryUiState.renEntry.toCreateRental())
                        val response = post.body()
                        if (post.isSuccessful) {
                            _uiState.emit(EditRentalUiState.Success)
                            Log.d("Success", response.toString())
                        } else {
                            val errorBody = post.raw()
                            _uiState.emit(EditRentalUiState.SuccessError)
                            Log.e("post error", "post not successful $errorBody")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.emit(EditRentalUiState.Error)
                    Log.e("PostRentalError", "cannot post rental $e")
                }
            }
        }
    }

    private fun validateInput(uiState: EntryDetails = editUiState.renEntry): Boolean {
        return uiState.name.isNotBlank() && uiState.location.isNotBlank()
                && uiState.description.isNotBlank() && uiState.type.isNotBlank()
                && uiState.price.isNotBlank()
    }
}

fun RentalEntity.toEditDetails(): EntryDetails = EntryDetails(
    name = name,
    image = image ?: 1,
    price = price ?: "no price",
    description = description,
    type = type,
    location = location ?: "no price",
    available = available,
    rating = rating.toString(),
    total_units = totalUnits.toString()
)

fun RentalEntity.toRentalEditUiState(isEntryValid: Boolean = false):
        RenEntryUiState = RenEntryUiState(
    renEntry = this.toEditDetails(),
    isEntryValid = isEntryValid
)