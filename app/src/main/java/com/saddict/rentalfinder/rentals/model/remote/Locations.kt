package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class Country(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String
)

@Keep
data class State(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("country")
    val country: Int
)

@Keep
data class City(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("state")
    val state: Int
)

@Keep
data class Neighborhood(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("city")
    val city: Int
)
