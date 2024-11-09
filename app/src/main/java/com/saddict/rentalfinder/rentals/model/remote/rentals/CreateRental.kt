package com.saddict.rentalfinder.rentals.model.remote.rentals

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonInclude

@Keep
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateRental(
    val title: String,
    val image: Int,
    val price: Float,
    val description: String,
    val category: String,
    val available: Boolean,
    @Suppress("PropertyName")
    val total_units: Int,
//    val location: String,
    @Suppress("PropertyName")
    val is_active: Boolean,
    val country: Int?,
    val state: Int?,
    val city: Int?,
    val neighborhood: Int?,
)
