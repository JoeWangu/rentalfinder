package com.saddict.rentalfinder.rentals.model.remote.images

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class ExtraRentalImageResults(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("image_name")
    val imageName: String,
    @JsonProperty("image")
    val imageUrl: String,
    @JsonProperty("rental")
    val rental: Int,
    @JsonProperty("author")
    val author: String,
)
