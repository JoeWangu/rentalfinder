package com.saddict.rentalfinder.rentals.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.prop.Constants.TIMEOUT_MILLIS
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.utils.mapToEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//sealed interface HomeUiState {
//    data class Success(val rentalResults: List<RentalResults>) : HomeUiState
//    data object Loading : HomeUiState
//    data object Error : HomeUiState
//}

//@SuppressLint("StaticFieldLeak")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appApi: RemoteDataSource,
    private val appDao: LocalDataSource,
//    private val appDatabase: RentalDatabase
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = fetchItemsFromApiIfDbEmpty()
                if (result.isFailure) {
                    _errorFlow.emit("Failed to load data: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }

    val rentalItems: StateFlow<List<RentalEntity>> = appDao.fetchRentals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyList()
        )

    suspend fun fetchItemsFromApiIfDbEmpty(): Result<Unit> {
        return try {
            val dbItems = appDao.fetchRentals().first()
            if (dbItems.isEmpty()) {
                val apiItems = appApi.getRentals(1).results
                appDao.insertRentals(apiItems.map { it.mapToEntity() })
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    private val _uiState = MutableSharedFlow<HomeUiState>()
//    val uiState: SharedFlow<HomeUiState> = _uiState

//    fun homeItems() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    _uiState.emit(HomeUiState.Loading)
//                    val items =
//                        appDao.fetchRentals().first().map { it.mapToResults() }
//                    if (items.isNotEmpty()) {
//                        _uiState.emit(HomeUiState.Success(items))
//                    } else {
//                        val response = appApi.getRentals(1).results
//                        appDao.insertRentals(response.map { it.mapToEntity() })
//                        _uiState.emit(HomeUiState.Success(response))
//                    }
//                } catch (e: Exception) {
//                    _uiState.emit(HomeUiState.Error)
//                    Log.e("Home Items", "Could not load home items ${e.message}")
//                }
//            }
//        }
//    }
}