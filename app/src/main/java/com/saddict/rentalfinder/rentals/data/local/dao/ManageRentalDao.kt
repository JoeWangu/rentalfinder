package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManageRentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManageRentals(rentals: List<RentalManageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneManageRental(rental: RentalManageEntity)

    @Query("SELECT * FROM manage_rentals ORDER BY id ASC")
    fun fetchManageRentals(): Flow<List<RentalManageEntity>>

    @Query("SELECT * FROM manage_rentals")
    fun fetchPagedManageRentals(): PagingSource<Int, RentalManageEntity>

    @Query("SELECT * FROM manage_rentals WHERE id = :id")
    fun fetchOneManageRental(id: Int): Flow<RentalManageEntity>

    @Query("DELETE FROM manage_rentals")
    suspend fun deleteAllManageRentals()
}