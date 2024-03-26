package com.saddict.rentalfinder.rentals.ui.rentals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface CreateRentalUiState {
    data class Success(val rental: RentalResults) : CreateRentalUiState
    data object Error : CreateRentalUiState
    data object SuccessError : CreateRentalUiState
    data object Loading : CreateRentalUiState
}

@HiltViewModel
class RentalEntryViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    private val _uiState = MutableSharedFlow<CreateRentalUiState>()
    val uiState: SharedFlow<CreateRentalUiState> = _uiState
    var entryUiState by mutableStateOf(RenEntryUiState())
        private set

    fun postRental() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(CreateRentalUiState.Loading)
                    if (validateInput()) {
                        val post =
                            remoteDataSource.postRental(entryUiState.renEntry.toCreateRental())
                        val response = post.body()
                        if (post.isSuccessful) {
                            _uiState.emit(CreateRentalUiState.Success(response!!))
                            Log.d("Success", response.toString())
                        } else {
                            val errorBody = post.raw()
                            _uiState.emit(CreateRentalUiState.SuccessError)
                            Log.e("post error", "post not successful $errorBody")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.emit(CreateRentalUiState.Error)
                    Log.e("PostRentalError", "cannot post rental $e")
                }
            }
        }
    }

    fun updateUiState(uiState: EntryDetails) {
        entryUiState = RenEntryUiState(
            renEntry = uiState,
            isEntryValid = validateInput(uiState)
        )
    }

    private fun validateInput(uiState: EntryDetails = entryUiState.renEntry): Boolean {
        return uiState.name.isNotBlank() && uiState.location.isNotBlank()
                && uiState.description.isNotBlank() && uiState.type.isNotBlank()
                && uiState.price.isNotBlank()
    }
}

data class RenEntryUiState(
    val renEntry: EntryDetails = EntryDetails(),
    val isEntryValid: Boolean = false
)

data class EntryDetails(
    val name: String = "",
    val image: Int = 1,
    val price: String = "",
    val description: String = "",
    val type: String = "single",
    val location: String = "M",
    val available: Boolean = false,
    val rating: String = "3",
    @Suppress("PropertyName")
    val total_units: String = "",
)

fun EntryDetails.toCreateRental(): CreateRental = CreateRental(
    name = name,
    image = image,
    price = price,
    description = description,
    type = type,
    location = location,
    available = available,
    rating = rating,
    total_units = total_units.toInt(),
)
//image = 8