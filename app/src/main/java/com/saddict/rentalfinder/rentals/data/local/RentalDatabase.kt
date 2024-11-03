package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saddict.rentalfinder.rentals.data.local.dao.ImageDao
import com.saddict.rentalfinder.rentals.data.local.dao.ImageRemoteKeysDao
import com.saddict.rentalfinder.rentals.data.local.dao.ManageRentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalManageRemoteKeysDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalRemoteKeysDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserProfileDao
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.remote.User
import com.saddict.rentalfinder.rentals.model.remote.UserProfile
import com.saddict.rentalfinder.utils.UserProfileDetailsConverter
import com.saddict.rentalfinder.utils.UserProfileTypeConverter

@Database(
    entities = [
        UserProfile::class,
        User::class,
        RentalEntity::class,
        RentalManageEntity::class,
        ImageEntity::class,
        RentalRemoteKeysEntity::class,
        RentalManageRemoteKeysEntity::class,
        ImageRemoteKeysEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(UserProfileTypeConverter::class, UserProfileDetailsConverter::class)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userDao(): UserDao
    abstract fun rentalDao(): RentalDao
    abstract fun imageDao(): ImageDao
    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao
    abstract fun rentalRemoteKeysDao(): RentalRemoteKeysDao
    abstract fun rentalManageRemoteKeysDao(): RentalManageRemoteKeysDao
    abstract fun manageRentalDao(): ManageRentalDao
}