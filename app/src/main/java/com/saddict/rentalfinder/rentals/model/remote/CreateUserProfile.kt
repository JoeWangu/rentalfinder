package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonInclude

@Keep
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateUserProfile(
    @Suppress("PropertyName")
    val first_name: String?,
    @Suppress("PropertyName")
    val last_name: String?,
    @Suppress("PropertyName")
    val phone_number: String?,
    val dob: String?,
    val gender: String?,
    val address: String?,
//    @Suppress("PropertyName")
//    val profile_picture: String?,
    val bio: String?,
    val country: Int?,
    val state: Int?,
    val city: Int?,
    val neighborhood: Int?,
)