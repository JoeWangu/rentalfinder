package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep

@Keep
data class CreateRental(
    val name: String,
    val image: Int,
    val price: String,
    val description: String,
    val type: String,
    val location: String,
    val available: Boolean,
    val rating: String,
    @Suppress("PropertyName")
    val total_units: Int,
)
