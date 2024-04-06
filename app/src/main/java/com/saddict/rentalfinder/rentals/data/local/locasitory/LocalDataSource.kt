package com.saddict.rentalfinder.rentals.data.local.locasitory

import androidx.paging.PagingSource
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalManageEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertRentals(rentals: List<RentalEntity>)
    suspend fun insertOneRental(rental: RentalEntity)
    fun fetchRentals(): Flow<List<RentalEntity>>
    fun fetchOneRental(id: Int): Flow<RentalEntity>
    fun fetchPagedRentals(): PagingSource<Int, RentalEntity>
    suspend fun deleteAllRentals()

    //    images
    suspend fun insertImages(images: List<ImageEntity>)
    fun fetchImages(): Flow<List<ImageEntity>>
    fun fetchPagedImages(): PagingSource<Int, ImageEntity>
    suspend fun deleteAllImages()

    //    manage rentals
    suspend fun insertManageRentals(rentals: List<RentalManageEntity>)
    suspend fun insertOneManageRental(rental: RentalManageEntity)
    fun fetchManageRentals(): Flow<List<RentalManageEntity>>
    fun fetchOneManageRental(id: Int): Flow<RentalManageEntity>
    fun fetchPagedManageRentals(): PagingSource<Int, RentalManageEntity>
    suspend fun deleteAllManageRentals()
}