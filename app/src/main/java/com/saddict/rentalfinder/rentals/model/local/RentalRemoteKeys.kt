package com.saddict.rentalfinder.rentals.model.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "rental_remote_keys")
data class RentalRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "next_page")
    val nextPage: Int?,
    @ColumnInfo(name = "previous_page")
    val prevPage: Int?,
)
