package com.saddict.rentalfinder.rentals.data.local.locasitory

import androidx.paging.PagingSource
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalUser
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    //    user
    suspend fun insertUser(user: UserEntity)
    fun fetchUser(): Flow<UserEntity>
    suspend fun deleteUser()

    //    user profile
    suspend fun insertUserProfile(userProfileEntity: UserProfileEntity)
    fun fetchUserProfile(): Flow<UserProfileEntity>
    suspend fun deleteUserProfile()

    //    rental user
    suspend fun insertRentalUser(user: RentalUser)
    fun fetchRentalUser(): Flow<RentalUser>
    suspend fun deleteRentalUser()

    //    rentals
    suspend fun insertRentals(rentals: List<RentalEntity>)
    suspend fun insertOneRental(rental: RentalEntity)
    fun fetchRentals(): Flow<List<RentalEntity>>
    fun fetchOneRental(id: Int): Flow<RentalEntity>
    fun fetchPagedRentals(): PagingSource<Int, RentalEntity>
    suspend fun deleteAllRentals()

    //    manage rentals
    suspend fun insertManageRentals(rentals: List<RentalManageEntity>)
    suspend fun insertOneManageRental(rental: RentalManageEntity)
    fun fetchManageRentals(): Flow<List<RentalManageEntity>>
    fun fetchOneManageRental(id: Int): Flow<RentalManageEntity>
    fun fetchPagedManageRentals(): PagingSource<Int, RentalManageEntity>
    suspend fun deleteAllManageRentals()

    //    images
    suspend fun insertImages(images: List<ImageEntity>)
    fun fetchImages(): Flow<List<ImageEntity>>
    fun fetchPagedImages(): PagingSource<Int, ImageEntity>
    suspend fun deleteAllImages()
}