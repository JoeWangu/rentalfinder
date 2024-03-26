package com.saddict.rentalfinder.rentals.data.local.locasitory

import com.saddict.rentalfinder.rentals.data.local.RentalDao
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val rentalDao: RentalDao
) : LocalDataSource {
    override suspend fun upsertAllRentals(rentals: List<RentalEntity>) {
        return rentalDao.insertAllRentals(rentals)
    }

    override fun fetchRentals(): Flow<List<RentalEntity>> {
        return rentalDao.fetchRentals()
    }

    override fun fetchAllRentalsDesc(): Flow<List<RentalEntity>> {
        return rentalDao.fetchRentals()
    }

    override fun fetchOneRentals(id: Int): Flow<RentalEntity> {
        return rentalDao.fetchOneRentals(id)
    }

    override suspend fun getAllPaged(): List<RentalEntity> {
        return rentalDao.getAllPaged()
    }
}