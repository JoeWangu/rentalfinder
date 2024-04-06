package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.RentalRemoteKeysEntity

@Dao
interface RentalRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(rentalRemoteKeyEntities: List<RentalRemoteKeysEntity>)

    @Query("SELECT * FROM rental_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): RentalRemoteKeysEntity

    @Query("DELETE FROM rental_remote_keys")
    suspend fun deleteAllRemoteKeys()
}