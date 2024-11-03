package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.images.ImageRemoteKeysEntity

@Dao
interface ImageRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(imageRemoteKeyEntities: List<ImageRemoteKeysEntity>)

    @Query("SELECT * FROM image_remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): ImageRemoteKeysEntity

    @Query("DELETE FROM image_remote_keys")
    suspend fun deleteAllRemoteKeys()
}