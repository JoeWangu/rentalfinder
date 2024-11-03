package com.saddict.rentalfinder.rentals.model.local.rentals

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "manage_rentals")
data class RentalManageEntity(
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
//    @ColumnInfo(name = "author_first_name")
//    val authorFirstName: String?,
//    @ColumnInfo(name = "author_phone_number")
//    val authorPhoneNumber: String?,
    @ColumnInfo(name = "author_email")
    val authorEmail: String,
    @ColumnInfo(name = "image_detail")
    val imageUrl: String,
    @ColumnInfo(name = "image_name")
    val imageName: String,
    @ColumnInfo(name = "avg_rating")
    val avgRating: Int,
    val author: String,
)