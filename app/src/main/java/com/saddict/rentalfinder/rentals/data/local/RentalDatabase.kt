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
import com.saddict.rentalfinder.rentals.data.local.dao.RentalUserDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserProfileDao
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalUser
import com.saddict.rentalfinder.utils.RentalUserProfileDetailsConverter
import com.saddict.rentalfinder.utils.RentalUserProfileTypeConverter

@Database(
    entities = [
        UserEntity::class,
        UserProfileEntity::class,
        RentalUser::class,
        RentalEntity::class,
        RentalManageEntity::class,
        ImageEntity::class,
        RentalRemoteKeysEntity::class,
        RentalManageRemoteKeysEntity::class,
        ImageRemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RentalUserProfileTypeConverter::class, RentalUserProfileDetailsConverter::class)
abstract class RentalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun rentalUserDao(): RentalUserDao
    abstract fun rentalDao(): RentalDao
    abstract fun imageDao(): ImageDao
    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao
    abstract fun rentalRemoteKeysDao(): RentalRemoteKeysDao
    abstract fun rentalManageRemoteKeysDao(): RentalManageRemoteKeysDao
    abstract fun manageRentalDao(): ManageRentalDao
}