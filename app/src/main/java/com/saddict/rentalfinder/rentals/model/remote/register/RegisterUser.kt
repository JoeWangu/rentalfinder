package com.saddict.rentalfinder.rentals.model.remote.register

import androidx.annotation.Keep

@Keep
data class RegisterUser(
    @Suppress("PropertyName")
    val first_name: String,
    @Suppress("PropertyName")
    val last_name: String,
    val email: String,
    val password: String,
    val username: String,
    @Suppress("PropertyName")
    val phone_number: String,
    val address: String,
    val dob: String?,
    val gender: String?
)