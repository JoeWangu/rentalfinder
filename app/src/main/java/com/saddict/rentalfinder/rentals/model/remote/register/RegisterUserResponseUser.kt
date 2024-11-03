package com.saddict.rentalfinder.rentals.model.remote.register

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class RegisterUserResponseUser(
//    @JsonProperty("first_name")
//    val firstName: String?,
//    @JsonProperty("last_name")
//    val lastName: String?,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("username")
    val username: String,
//    @JsonProperty("phone_number")
//    val phoneNumber: String?,
//    @JsonProperty("address")
//    val address: String?,
//    @JsonProperty("dob")
//    val dob: String?,
//    @JsonProperty("gender")
//    val gender: String?
)
