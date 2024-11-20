package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.remote.RentalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRentalUser(user: RentalUser)

    @Query("SELECT * FROM rental_user")
    fun fetchRentalUser(): Flow<RentalUser>

    @Query("DELETE FROM rental_user")
    suspend fun deleteRentalUser()
}