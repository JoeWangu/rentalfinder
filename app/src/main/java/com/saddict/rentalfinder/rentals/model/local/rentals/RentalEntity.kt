package com.saddict.rentalfinder.rentals.model.local.rentals

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "rentals")
data class RentalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: Int?,
    val price: Float?,
    @ColumnInfo(name = "total_units")
    val totalUnits: Int?,
    val title: String,
    val description: String,
    val category: String,
    @ColumnInfo(name = "date_posted")
    val datePosted: String,
    @ColumnInfo(name = "time_modified")
    val dateModified: String,
    @ColumnInfo(name = "time_posted")
    val timePosted: String,
    @ColumnInfo(name = "date_modified")
    val timeModified: String,
    val available: Boolean,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    @ColumnInfo(name = "author_id")
    val authorId: Int,
    @ColumnInfo(name = "author_email")
    val authorEmail: String,
    @ColumnInfo(name = "author_username")
    val authorUsername: String,
    @ColumnInfo(name = "author_phone_number")
    val authorPhoneNumber: String?,
    @ColumnInfo(name = "image_detail")
    val imageUrl: String,
    @ColumnInfo(name = "image_name")
    val imageName: String,
    @ColumnInfo(name = "avg_rating")
    val avgRating: Int,
    val author: String,
    @ColumnInfo(name = "country_name")
    val countryName: String?,
    @ColumnInfo(name = "state_name")
    val stateName: String?,
    @ColumnInfo(name = "city_name")
    val cityName: String?,
    @ColumnInfo(name = "neighborhood_name")
    val neighborhoodName: String?,
    @ColumnInfo(name = "country_id")
    val countryId: Int?,
    @ColumnInfo(name = "state_id")
    val stateId: Int?,
    @ColumnInfo(name = "city_id")
    val cityId: Int?,
    @ColumnInfo(name = "neighborhood_id")
    val neighborhoodId: Int?,
    @ColumnInfo(name = "country_code")
    val countryCode: String?,
//    val location: String?,
//    @ColumnInfo(name = "author_first_name")
//    val authorFirstName: String?,
//    @ColumnInfo(name = "author_last_name")
//    val authorLastName: String?,
//    @ColumnInfo(name = "author_phone_number")
//    val authorPhoneNumber: String?,
//    @ColumnInfo(name = "author_address")
//    val authorAddress: String?,
//    @ColumnInfo(name = "author_dob")
//    val authorDob: String?,
//    @ColumnInfo(name = "author_gender")
//    val authorGender: String?,
)
