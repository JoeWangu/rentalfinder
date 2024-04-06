package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saddict.rentalfinder.rentals.data.local.dao.ImageDao
import com.saddict.rentalfinder.rentals.data.local.dao.ImageRemoteKeysDao
import com.saddict.rentalfinder.rentals.data.local.dao.ManageRentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalManageRemoteKeysDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalRemoteKeysDao
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.ImageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalManageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.RentalRemoteKeysEntity

@Database(
    entities = [
        RentalEntity::class,
        RentalRemoteKeysEntity::class,
        ImageEntity::class,
        ImageRemoteKeysEntity::class,
        RentalManageRemoteKeysEntity::class,
        RentalManageEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun rentalDao(): RentalDao
    abstract fun imageDao(): ImageDao
    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao
    abstract fun rentalRemoteKeysDao(): RentalRemoteKeysDao
    abstract fun rentalManageRemoteKeysDao(): RentalManageRemoteKeysDao
    abstract fun manageRentalDao(): ManageRentalDao
}