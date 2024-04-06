package com.saddict.rentalfinder.rentals.model.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "image_name")
    val imageName: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val author: String,
)