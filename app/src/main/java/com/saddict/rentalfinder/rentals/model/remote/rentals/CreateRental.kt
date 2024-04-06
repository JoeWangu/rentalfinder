package com.saddict.rentalfinder.rentals.model.remote.rentals

import androidx.annotation.Keep

@Keep
data class CreateRental(
    val image: Int,
    val price: Float,
    @Suppress("PropertyName")
    val total_units: Int,
    val title: String,
    val description: String,
    val category: String,
    val location: String,
    val available: Boolean,
    @Suppress("PropertyName")
    val is_active: Boolean,
)
