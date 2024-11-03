package com.saddict.rentalfinder.rentals.model.remote

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("username")
    val username: String?,
    @ColumnInfo(name = "user_profile")
    @JsonProperty("user_profile")
    val userProfile: UserProfileDetails?,
//    @JsonProperty("first_name")
//    val firstName: String?,
//    @JsonProperty("last_name")
//    val lastName: String?,
//    @JsonProperty("phone_number")
//    val phoneNumber: String?,
//    @JsonProperty("address")
//    val address: String?,
//    @JsonProperty("dob")
//    val dob: String?,
//    @JsonProperty("gender")
//    val gender: String?,
)

@Keep
data class UserProfileDetails(
    @ColumnInfo(name = "phone_number")
    @JsonProperty("phone_number")
    val phoneNumber: String?,
)