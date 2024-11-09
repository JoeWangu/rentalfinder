package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageRemoteKeysEntity

@Dao
interface RentalManageRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteManageKeys(rentalManageRemoteKeyEntities: List<RentalManageRemoteKeysEntity>)

    @Query("SELECT * FROM rental_manage_remote_keys WHERE id = :id")
    suspend fun getRemoteManageKeys(id: Int): RentalManageRemoteKeysEntity

    @Query("DELETE FROM rental_manage_remote_keys")
    suspend fun deleteAllRemoteManageKeys()
}