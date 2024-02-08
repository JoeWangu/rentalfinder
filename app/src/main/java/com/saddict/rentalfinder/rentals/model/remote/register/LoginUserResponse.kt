package com.saddict.rentalfinder.rentals.model.remote.register

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class LoginUserResponse(
    @JsonProperty("user")
    val user: String,
    @JsonProperty("token")
    val token: String,
    @JsonProperty("created")
    val created: Boolean
)
