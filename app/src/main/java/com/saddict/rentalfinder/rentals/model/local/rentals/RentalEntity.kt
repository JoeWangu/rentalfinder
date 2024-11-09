package com.saddict.rentalfinder.rentals.model.local.rentals

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saddict.rentalfinder.rentals.model.remote.UserProfileDetails

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
//    val location: String?,
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
    @ColumnInfo(name = "author_profile_details")
    val authorProfileDetails: UserProfileDetails,
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
    @ColumnInfo(name = "image_detail")
    val imageUrl: String,
    @ColumnInfo(name = "image_name")
    val imageName: String,
    @ColumnInfo(name = "avg_rating")
    val avgRating: Int,
    val author: String,
//    val country: String,
//    val state: String,
//    val city: String,
//    val neighborhood: String,
)
