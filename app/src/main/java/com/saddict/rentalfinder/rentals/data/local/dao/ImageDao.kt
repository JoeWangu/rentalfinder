package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.images.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>)

    @Query("SELECT * FROM images ORDER BY id ASC")
    fun fetchImages(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images")
    fun fetchPagedImages(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()
}