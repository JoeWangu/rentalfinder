package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.ImageRemoteKeys
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalRemoteKeys

@Database(
    entities = [
        RentalEntity::class,
        RentalRemoteKeys::class,
        ImageEntity::class,
        ImageRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun rentalDao(): RentalDao
    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao
    abstract fun rentalRemoteKeysDao(): RentalRemoteKeysDao
}