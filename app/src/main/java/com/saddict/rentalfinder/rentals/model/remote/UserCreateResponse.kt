package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class UserCreateResponse(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("username")
    val username: String
)
