package com.saddict.rentalfinder.rentals.data.local.locasitory

import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun upsertAllRentals(rentals: List<RentalEntity>)
    suspend fun fetchRentals(): Flow<List<RentalEntity>>
    suspend fun fetchAllRentalsDesc(): Flow<List<RentalEntity>>
    fun fetchOneRentals(id: Int): Flow<RentalEntity>
    suspend fun getAllPaged(): List<RentalEntity>

}