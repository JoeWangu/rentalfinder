package com.saddict.rentalfinder.rentals.data.local.locasitory

import androidx.paging.PagingSource
import com.saddict.rentalfinder.rentals.data.local.dao.ImageDao
import com.saddict.rentalfinder.rentals.data.local.dao.ManageRentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalDao
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalManageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val rentalDao: RentalDao,
    private val imageDao: ImageDao,
    private val manageRentalDao: ManageRentalDao,
) : LocalDataSource {
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