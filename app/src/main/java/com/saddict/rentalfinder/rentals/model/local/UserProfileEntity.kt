package com.saddict.rentalfinder.rentals.model.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "last_name")
    val lastName: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    val dob: String?,
    val gender: String?,
    val address: String?,
    @ColumnInfo(name = "profile_picture")
    val profilePicture: String?,
    @ColumnInfo(name = "user_bio")
    val userBio: String?,
    @ColumnInfo(name = "user_country")
    val userCountry: String?,
    @ColumnInfo(name = "user_state")
    val userState: String?,
    @ColumnInfo(name = "user_city")
    val userCity: String?,
    @ColumnInfo(name = "user_neighborhood")
    val userNeighborhood: String?,
)
