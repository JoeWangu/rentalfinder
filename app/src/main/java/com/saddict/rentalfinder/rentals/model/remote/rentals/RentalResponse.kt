package com.saddict.rentalfinder.rentals.model.remote.rentals

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class RentalResponse(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<RentalResults>
)
