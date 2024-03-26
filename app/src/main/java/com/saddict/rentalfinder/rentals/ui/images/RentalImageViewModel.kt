package com.saddict.rentalfinder.rentals.ui.images

import androidx.lifecycle.ViewModel
import com.saddict.rentalfinder.rentals.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RentalImageViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    val getAllImages = repository.getAllImages()
}