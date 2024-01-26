package com.saddict.rentalfinder.rentals.model.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "rentals")
data class RentalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: Int?,
    val price: String?,
    val description: String,
    val type: String,
    val location: String?,
    val available: Boolean,
    val rating: Int?,
    @ColumnInfo(name = "total_units")
    val totalUnits: Int?,
    @ColumnInfo(name = "date_posted")
    val datePosted: String,
    @ColumnInfo(name = "date_modified")
    val dateModified: String,
    @ColumnInfo(name = "image_detail")
    val imageUrl: String,
)
