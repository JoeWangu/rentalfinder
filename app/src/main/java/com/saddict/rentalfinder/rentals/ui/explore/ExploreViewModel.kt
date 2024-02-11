package com.saddict.rentalfinder.rentals.ui.explore

import androidx.lifecycle.ViewModel
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    localDataSource: LocalDataSource
) : ViewModel() {
    val exploreItems: Flow<List<RentalEntity>> = localDataSource.fetchRentals()
}