package com.saddict.rentalfinder.rentals.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.utils.mapToResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
    private val pager: Pager<Int, RentalEntity>,
//    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
//    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableSharedFlow<HomeUiState>()
    val uiState: SharedFlow<HomeUiState> = _uiState

    val rentalItemsPagedFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.mapToResults() }
        }.cachedIn(viewModelScope)


    fun recommendedItems() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(HomeUiState.Loading)
                    val items = localDataSource.fetchRentals().first().map { it.mapToResults() }
                    if (items.isNotEmpty()) {
                        _uiState.emit(HomeUiState.Success(items))
                    } else {
                        _uiState.emit(HomeUiState.Error)
                    }
                } catch (e: Exception) {
                    Log.e("Home Items", "Could not load home items")
                }
            }
        }
    }
//    private var doneLoading = false

    /*    init {
            loadHomeItemsFromOnline()
        }

        private fun loadHomeItemsFromOnline() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        _uiState.emit(HomeUiState.Loading)
                        val data = remoteDataSource.getRentals(1)
                        if (data.results.isNotEmpty()) {
    //                        doneLoading = true
                            homeItems()
                        }
                    } catch (e: Exception) {
                        _uiState.emit(HomeUiState.Error)
                    }
                }
            }
        }

            fun homeItems() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
    //                    _uiState.emit(HomeUiState.Loading)
    //                    val items = remoteDataSource.getRentals(1).results
                        val items = localDataSource.fetchRentals().first().map { it.mapToResults() }
                        if (items.isNotEmpty()) {
                            _uiState.emit(HomeUiState.Success(items))
                            Log.i("Home Items", "Could load home items")
                        } else {
                            _uiState.emit(HomeUiState.Error)
                            Log.d("Home Items", "Could not load home items")
                        }
                    } catch (e: Exception) {
                        _uiState.emit(HomeUiState.Error)
                        Log.e("Home Items", "Could not load home items")
                    }
                }
            }
        }*/

//    private fun loadHomeItemsFromOnline() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    _uiState.emit(HomeUiState.Loading)
////                    val items = remoteDataSource.getRentals(1).results
////                    val items = localDataSource.fetchRentals().first().map { it.mapToResults() }
//                    if (isOnline(context)) {
//                        val items = remoteDataSource.getRentals(1).results
//                        if (items.isNotEmpty()) {
//                            _uiState.emit(HomeUiState.Success(items))
//                            Log.i("Home Items", "loaded online home items")
//                        } else {
//                            _uiState.emit(HomeUiState.Error)
//                            Log.d("Home Items", "Could not load online home items")
//                        }
//                    } else {
//                        context.toastUtil("No internet connection!")
//                        val items = localDataSource.fetchRentals()
//                        if (items.first().isNotEmpty()) {
//                            _uiState.emit(
//                                HomeUiState.Success(
//                                    items.first().map { it.mapToResults() })
//                            )
//                            Log.i("Home Items", "Could load local home items")
//                        } else {
//                            _uiState.emit(HomeUiState.Error)
//                            Log.d("Home Items", "Could not load local home items")
//                        }
//                    }
//                } catch (e: Exception) {
//                    _uiState.emit(HomeUiState.Error)
//                    Log.e("Home Items", "Could not load home items")
//                }
//            }
//        }
//    }

//    fun homeItems() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    _uiState.emit(HomeUiState.Loading)
////                    val items = remoteDataSource.getRentals(1).results
////                    val items = localDataSource.fetchRentals().first().map { it.mapToResults() }
//                    val items = remoteDataSource.getRentals(1).results
//                    if (items.isNotEmpty()) {
//                        _uiState.emit(HomeUiState.Success(items))
//                        Log.i("Home Items", "Could load home items")
//                    } else {
//                        _uiState.emit(HomeUiState.Error)
//                        Log.d("Home Items", "Could not load home items")
//                    }
//                } catch (e: Exception) {
//                    _uiState.emit(HomeUiState.Error)
//                    Log.e("Home Items", "Could not load home items")
//                }
//            }
//        }
//    }
}

//fun recommendedItems() {
//    viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            try {
//                _uiState.emit(HomeUiState.Loading)
//                val items = localDataSource.fetchRentals().first().map { it.mapToResults() }
//                if (items.isNotEmpty()){
//                    _uiState.emit(HomeUiState.Success(items))
//                }else{
//                    _uiState.emit(HomeUiState.Error)
//                }
//            } catch (e: Exception) {
//                Log.e("Home Items", "Could not load home items")
//            }
//        }
//    }
//}