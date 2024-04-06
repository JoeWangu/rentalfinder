package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class RatingsResponse(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<RatingsResults>
)

@Keep
data class RatingsResults(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("stars")
    val stars: Int,
    @JsonProperty("rental")
    val rental: Int,
    @JsonProperty("user")
    val user: String,
)

@Keep
data class CreateRatings(
    @JsonProperty("stars")
    val stars: Int,
    @JsonProperty("rental")
    val rental: Int,
)
