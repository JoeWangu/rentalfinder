package com.saddict.rentalfinder.rentals.model.remote.rentals

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import com.saddict.rentalfinder.rentals.model.remote.User
import com.saddict.rentalfinder.rentals.model.remote.images.RentalImageResults

@Keep
data class RentalResults(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("image")
    val image: Int?,
    @JsonProperty("price")
    val price: Float?,
    @JsonProperty("total_units")
    val totalUnits: Int?,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("category")
    val category: String,
    @JsonProperty("location")
    val location: String?,
    @JsonProperty("date_posted")
    val datePosted: String,
    @JsonProperty("date_modified")
    val dateModified: String,
    @JsonProperty("time_posted")
    val timePosted: String,
    @JsonProperty("time_modified")
    val timeModified: String,
    @JsonProperty("available")
    val available: Boolean,
    @JsonProperty("is_active")
    val isActive: Boolean,
    @JsonProperty("author_detail")
    val authorDetail: User,
    @JsonProperty("image_detail")
    val imageDetail: RentalImageResults,
    @JsonProperty("avg_rating")
    val avgRating: Int,
    @JsonProperty("author")
    val author: String,
)
