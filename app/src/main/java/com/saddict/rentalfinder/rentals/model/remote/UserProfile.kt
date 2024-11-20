package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class UserProfile(
    @JsonProperty("first_name")
    val firstName: String?,
    @JsonProperty("last_name")
    val lastName: String?,
    @JsonProperty("phone_number")
    val phoneNumber: String?,
    @JsonProperty("dob")
    val dob: String?,
    @JsonProperty("gender")
    val gender: String?,
    @JsonProperty("address")
    val address: String?,
    @JsonProperty("profile_picture")
    val profilePicture: String?,
    @JsonProperty("bio")
    val bio: String,
    @JsonProperty("country")
    val country: Int,
    @JsonProperty("state")
    val state: Int?,
    @JsonProperty("city")
    val city: Int?,
    @JsonProperty("neighborhood")
    val neighborhood: Int?,
    @JsonProperty("countryDetails")
    val countryDetails: CountryResults,
    @JsonProperty("stateDetails")
    val stateDetails: StateResults?,
    @JsonProperty("cityDetails")
    val cityDetails: CityResults?,
    @JsonProperty("neighborhoodDetails")
    val neighborhoodDetails: NeighborhoodResults?
)
