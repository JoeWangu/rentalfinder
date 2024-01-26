package com.saddict.rentalfinder.rentals.di

import android.content.Context
import androidx.room.Room
import com.saddict.rentalfinder.rentals.data.local.RentalDao
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RentalDatabase {
        return Room.databaseBuilder(context, RentalDatabase::class.java, "rental_database").build()
    }

    @Provides
    @Singleton
    fun providesRentalDao(rentalDatabase: RentalDatabase): RentalDao {
        return rentalDatabase.rentalDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(productDao: RentalDao): LocalDataSource {
        return LocalDataSourceImpl(productDao)
    }
}