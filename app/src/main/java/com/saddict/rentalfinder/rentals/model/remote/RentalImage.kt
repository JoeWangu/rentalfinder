package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class RentalImage(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("image")
    val image: String
)
