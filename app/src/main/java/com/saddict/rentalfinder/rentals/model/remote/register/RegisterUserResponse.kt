package com.saddict.rentalfinder.rentals.model.remote.register

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class RegisterUserResponse(
    @JsonProperty("user")
    val user: RegisterUserResponseUser,
    @JsonProperty("token")
    val token: String,
)