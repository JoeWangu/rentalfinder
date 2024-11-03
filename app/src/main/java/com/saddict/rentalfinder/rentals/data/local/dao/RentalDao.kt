package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRentals(rentals: List<RentalEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneRental(rental: RentalEntity)

    @Query("SELECT * FROM rentals ORDER BY id ASC")
    fun fetchRentals(): Flow<List<RentalEntity>>

    @Query("SELECT * FROM rentals")
    fun fetchPagedRentals(): PagingSource<Int, RentalEntity>

    @Query("SELECT * FROM rentals WHERE id = :id")
    fun fetchOneRental(id: Int): Flow<RentalEntity>

    @Query("DELETE FROM rentals")
    suspend fun deleteAllRentals()
}