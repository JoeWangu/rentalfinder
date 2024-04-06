package com.saddict.rentalfinder.rentals.model.remote.images

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class ExtraImagesResponse(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val imageList: List<ExtraRentalImageResults>
)
