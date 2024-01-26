package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {
    @Upsert
    suspend fun upsertAllRentals(rentals: List<RentalEntity>)

    @Query("SELECT * FROM rentals ORDER BY id ASC")
    fun fetchRentals(): Flow<List<RentalEntity>>

    @Query("SELECT * from rentals ORDER BY id DESC")
    fun fetchAllRentalsDesc(): Flow<List<RentalEntity>>

    @Query("SELECT * FROM rentals WHERE id = :id")
    fun fetchOneRentals(id: Int): Flow<RentalEntity>

    @Query("SELECT * FROM rentals ORDER BY id ASC")
    suspend fun getAllPaged(): List<RentalEntity>
}