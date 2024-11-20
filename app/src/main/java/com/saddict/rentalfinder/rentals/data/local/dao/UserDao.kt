package com.saddict.rentalfinder.rentals.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    fun getUser(): Flow<UserEntity>

    @Query("DELETE FROM user")
    suspend fun deleteUser()

}