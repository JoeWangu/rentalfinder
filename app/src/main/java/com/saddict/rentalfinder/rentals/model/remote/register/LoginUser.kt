package com.saddict.rentalfinder.rentals.model.remote.register

import androidx.annotation.Keep

@Keep
data class LoginUser(
    val email: String,
    val password: String,
)
