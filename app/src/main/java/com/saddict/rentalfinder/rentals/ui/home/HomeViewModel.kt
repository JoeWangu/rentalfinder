package com.saddict.rentalfinder.rentals.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
import com.saddict.rentalfinder.utils.mapToEntity
import com.saddict.rentalfinder.utils.mapToResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface HomeUiState {
    data class Success(val rentalResults: List<RentalResults>) : HomeUiState
    data object Loading : HomeUiState
    data object Error : HomeUiState
}

//@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appApi: RemoteDataSource,
    private val appDao: LocalDataSource,
//    private val appDatabase: RentalDatabase
) : ViewModel() {
    private val _uiState = MutableSharedFlow<HomeUiState>()
    val uiState: SharedFlow<HomeUiState> = _uiState

    fun homeItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(HomeUiState.Loading)
                    val items =
                        appDao.fetchRentals().first().map { it.mapToResults() }
                    if (items.isNotEmpty()) {
                        _uiState.emit(HomeUiState.Success(items))
                    } else {
                        val response = appApi.getRentals(1).results
                        appDao.insertRentals(response.map { it.mapToEntity() })
                        _uiState.emit(HomeUiState.Success(response))
                    }
                } catch (e: Exception) {
                    _uiState.emit(HomeUiState.Error)
                    Log.e("Home Items", "Could not load home items ${e.message}")
                }
            }
        }
    }
}