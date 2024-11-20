package com.saddict.rentalfinder.rentals.data.local.locasitory

import androidx.paging.PagingSource
import com.saddict.rentalfinder.rentals.data.local.dao.ImageDao
import com.saddict.rentalfinder.rentals.data.local.dao.ManageRentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalUserDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserDao
import com.saddict.rentalfinder.rentals.data.local.dao.UserProfileDao
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.remote.RentalUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userProfileDao: UserProfileDao,
    private val rentalUserDao: RentalUserDao,
    private val rentalDao: RentalDao,
    private val manageRentalDao: ManageRentalDao,
    private val imageDao: ImageDao,
) : LocalDataSource {
    //    user
    override suspend fun insertUser(user: UserEntity) {
        return userDao.insertUser(user)
    }

    override fun fetchUser(): Flow<UserEntity> {
        return userDao.getUser()
    }

    override suspend fun deleteUser() {
        return userDao.deleteUser()
    }

    //    user profile
    override suspend fun insertUserProfile(userProfileEntity: UserProfileEntity) {
        return userProfileDao.insertUserProfile(userProfileEntity)
    }

    override fun fetchUserProfile(): Flow<UserProfileEntity> {
        return userProfileDao.fetchUserProfile()
    }

    override suspend fun deleteUserProfile() {
        return userProfileDao.deleteUserProfile()
    }

    //    rental user
    override suspend fun insertRentalUser(user: RentalUser) {
        return rentalUserDao.insertRentalUser(user)
    }

    override fun fetchRentalUser(): Flow<RentalUser> {
        return rentalUserDao.fetchRentalUser()
    }

    override suspend fun deleteRentalUser() {
        return rentalUserDao.deleteRentalUser()
    }

    //    rentals
    override suspend fun insertRentals(rentals: List<RentalEntity>) {
        return rentalDao.insertRentals(rentals)
    }

    override suspend fun insertOneRental(rental: RentalEntity) {
        return rentalDao.insertOneRental(rental)
    }

    override fun fetchRentals(): Flow<List<RentalEntity>> {
        return rentalDao.fetchRentals()
    }

    override fun fetchOneRental(id: Int): Flow<RentalEntity> {
        return rentalDao.fetchOneRental(id)
    }

    override fun fetchPagedRentals(): PagingSource<Int, RentalEntity> {
        return rentalDao.fetchPagedRentals()
    }

    override suspend fun deleteAllRentals() {
        return rentalDao.deleteAllRentals()
    }

    //    images
    override suspend fun insertImages(images: List<ImageEntity>) {
        return imageDao.insertImages(images)
    }

    override fun fetchImages(): Flow<List<ImageEntity>> {
        return imageDao.fetchImages()
    }

    override fun fetchPagedImages(): PagingSource<Int, ImageEntity> {
        return imageDao.fetchPagedImages()
    }

    override suspend fun deleteAllImages() {
        return imageDao.deleteAllImages()
    }

    //    manage rentals
    override suspend fun insertManageRentals(rentals: List<RentalManageEntity>) {
        return manageRentalDao.insertManageRentals(rentals)
    }

    override suspend fun insertOneManageRental(rental: RentalManageEntity) {
        return manageRentalDao.insertOneManageRental(rental)
    }

    override fun fetchManageRentals(): Flow<List<RentalManageEntity>> {
        return manageRentalDao.fetchManageRentals()
    }

    override fun fetchOneManageRental(id: Int): Flow<RentalManageEntity> {
        return manageRentalDao.fetchOneManageRental(id)
    }

    override fun fetchPagedManageRentals(): PagingSource<Int, RentalManageEntity> {
        return manageRentalDao.fetchPagedManageRentals()
    }

    override suspend fun deleteAllManageRentals() {
        return manageRentalDao.deleteAllManageRentals()
    }

}