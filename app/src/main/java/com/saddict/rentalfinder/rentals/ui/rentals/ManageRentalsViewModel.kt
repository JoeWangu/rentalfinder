package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.lifecycle.ViewModel
import com.saddict.rentalfinder.rentals.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageRentalsViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    val getAllRentals = repository.getPagedManageRentals()
}