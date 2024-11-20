package com.saddict.rentalfinder.rentals.model.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val username: String,
)
