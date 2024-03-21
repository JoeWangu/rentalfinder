package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RemoteKeys
import com.saddict.rentalfinder.rentals.model.local.RentalEntity

@Database(
    entities = [
        RentalEntity::class,
        ImageEntity::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun rentalDao(): RentalDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}