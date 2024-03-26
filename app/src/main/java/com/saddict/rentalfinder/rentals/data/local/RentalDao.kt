package com.saddict.rentalfinder.rentals.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRentals(rentals: List<RentalEntity>)

    @Query("SELECT * FROM rentals ORDER BY id ASC")
    fun fetchRentals(): Flow<List<RentalEntity>>

    @Query("SELECT * FROM rentals")
    fun fetchAllPagedRentals(): PagingSource<Int, RentalEntity>

    @Query("SELECT * from rentals ORDER BY id DESC")
    fun fetchAllRentalsDesc(): Flow<List<RentalEntity>>

    @Query("SELECT * FROM rentals WHERE id = :id")
    fun fetchOneRentals(id: Int): Flow<RentalEntity>

    @Query("SELECT * FROM rentals ORDER BY id ASC")
    suspend fun getAllPaged(): List<RentalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(images: List<ImageEntity>)

    @Query("SELECT * FROM images ORDER BY id ASC")
    fun fetchImages(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images")
    fun getAllPagedImages(): PagingSource<Int, ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<ImageEntity>)

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()
}