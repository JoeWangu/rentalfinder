package com.saddict.rentalfinder.rentals.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.saddict.rentalfinder.rentals.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    val getAllRentals = repository.getPagedRentals().cachedIn(viewModelScope)
}