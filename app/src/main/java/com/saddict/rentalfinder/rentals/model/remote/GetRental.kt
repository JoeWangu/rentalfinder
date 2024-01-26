package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class GetRental(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("image")
    val image: Int?,
    @JsonProperty("price")
    val price: String?,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("location")
    val location: String?,
    @JsonProperty("available")
    val available: Boolean,
    @JsonProperty("rating")
    val rating: Int?,
    @JsonProperty("total_units")
    val totalUnits: Int?,
    @JsonProperty("date_posted")
    val datePosted: String,
    @JsonProperty("date_modified")
    val dateModified: String
)
