package com.saddict.rentalfinder.rentals.ui.rentals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
import com.saddict.rentalfinder.utils.mapToEntity
import com.saddict.rentalfinder.utils.toCreateRental
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
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ViewModel() {
    var entryUiState by mutableStateOf(RenEntryUiState())
        private set
    private val _uiState = MutableSharedFlow<CreateRentalUiState>()
    val uiState: SharedFlow<CreateRentalUiState> = _uiState

    fun updateUiState(entryDetails: EntryDetails) {
        entryUiState =
            RenEntryUiState(renEntry = entryDetails, isEntryValid = validateInput(entryDetails))
    }

    fun postRental() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(CreateRentalUiState.Loading)
                    if (validateInput()) {
                        Log.d("createRentalsTag", "${entryUiState.renEntry.toCreateRental()}")
                        val post = remoteDataSource.postRental(
                            entryUiState.renEntry.toCreateRental()
                        )
                        val response = post.body()
                        if (post.isSuccessful) {
                            _uiState.emit(CreateRentalUiState.Success(response!!))
                            Log.d("Success", response.toString())
                            localDataSource.insertOneRental(response.mapToEntity())
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

    private fun validateInput(entryDetails: EntryDetails = entryUiState.renEntry): Boolean {
        return entryDetails.title.isNotBlank() && entryDetails.description.isNotBlank()
                && entryDetails.category.isNotBlank() && entryDetails.price.isNotBlank()
    }
}

data class RenEntryUiState(
    val renEntry: EntryDetails = EntryDetails(),
    val isEntryValid: Boolean = false
)

data class EntryDetails(
    val image: Int = 1,
    val price: String = "",
    @Suppress("PropertyName")
    val total_units: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "single",
//    val location: String = "Mjini",
    val available: Boolean = true,
    val isActive: Boolean = true,
    val country: Int? = null,
    val state: Int? = null,
    val city: Int? = null,
    val neighborhood: Int? = null,
    val countryName: String? = null,
    val stateName: String? = null,
    val cityName: String? = null,
    val neighborhoodName: String? = null,
    val countryCode: String? = null,
)
