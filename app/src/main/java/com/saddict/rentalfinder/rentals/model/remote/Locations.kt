package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class Country(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<CountryResults>
)

@Keep
data class CountryResults(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("code")
    val code: String?
)

@Keep
data class State(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<StateResults>
)

@Keep
data class StateResults(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("country")
    val country: Int?
)

@Keep
data class City(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<CityResults>
)

@Keep
data class CityResults(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("state")
    val state: Int?,
    @JsonProperty("country")
    val country: Int?
)

@Keep
data class Neighborhood(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String?,
    @JsonProperty("previous")
    val previous: String?,
    @JsonProperty("results")
    val results: List<NeighborhoodResults>
)

@Keep
data class NeighborhoodResults(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("city")
    val city: Int?
)
