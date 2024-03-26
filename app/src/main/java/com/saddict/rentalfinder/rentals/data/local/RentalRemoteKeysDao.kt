package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.RentalRemoteKeys

@Dao
interface RentalRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(rentalRemoteKeys: List<RentalRemoteKeys>)

    @Query("SELECT * FROM rental_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): RentalRemoteKeys

    @Query("DELETE FROM rental_remote_keys")
    suspend fun deleteAllRemoteKeys()
}