package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class CreateUserResponse(
    @JsonProperty("user")
    val user: UserCreateResponse,
    @JsonProperty("token")
    val token: String,
)

@Keep
data class UserCreateResponse(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("username")
    val username: String
)
