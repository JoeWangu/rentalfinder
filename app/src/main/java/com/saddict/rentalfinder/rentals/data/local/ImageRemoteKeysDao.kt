package com.saddict.rentalfinder.rentals.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.ImageRemoteKeys

@Dao
interface ImageRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(imageRemoteKeys: List<ImageRemoteKeys>)

    @Query("SELECT * FROM image_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): ImageRemoteKeys

    @Query("DELETE FROM image_remote_keys")
    suspend fun deleteAllRemoteKeys()
}