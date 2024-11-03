package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    @JsonProperty("first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    @JsonProperty("last_name")
    val lastName: String?,
    @ColumnInfo(name = "phone_number")
    @JsonProperty("phone_number")
    val phoneNumber: String?,
    @JsonProperty("address")
    val address: String?,
    @JsonProperty("dob")
    val dob: String?,
    @JsonProperty("gender")
    val gender: String?,
    @ColumnInfo(name = "profile_picture")
    @JsonProperty("profile_picture")
    val profilePicture: String?,
    @JsonProperty("bio")
    val userBio: String?,
    @JsonProperty("country")
    val userCountry: String?,
    @JsonProperty("state")
    val userState: String?,
    @JsonProperty("city")
    val userCity: String?,
    @JsonProperty("neighborhood")
    val userNeighborhood: String?,
)
