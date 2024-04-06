package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class User(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("first_name")
    val firstName: String?,
    @JsonProperty("phone_number")
    val phoneNumber: String?,
    @JsonProperty("email")
    val email: String,
)
