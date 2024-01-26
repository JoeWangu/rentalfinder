package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class GetUser(
    @JsonProperty("user")
    val user: String,
    @JsonProperty("token")
    val token: String,
    @JsonProperty("created")
    val created: Boolean
)
