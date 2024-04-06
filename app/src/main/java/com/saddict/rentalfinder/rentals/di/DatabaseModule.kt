package com.saddict.rentalfinder.rentals.di

import android.content.Context
import androidx.room.Room
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.local.dao.ImageDao
import com.saddict.rentalfinder.rentals.data.local.dao.ManageRentalDao
import com.saddict.rentalfinder.rentals.data.local.dao.RentalDao
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
        return Room.databaseBuilder(context, RentalDatabase::class.java, "rental_database")
            .build()
    }

    @Provides
    @Singleton
    fun providesRentalDao(rentalDatabase: RentalDatabase): RentalDao {
        return rentalDatabase.rentalDao()
    }

    @Provides
    @Singleton
    fun providesManageRentalDao(rentalDatabase: RentalDatabase): ManageRentalDao {
        return rentalDatabase.manageRentalDao()
    }

    @Provides
    @Singleton
    fun providesImageDao(rentalDatabase: RentalDatabase): ImageDao {
        return rentalDatabase.imageDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        rentalDao: RentalDao,
        imageDao: ImageDao,
        manageRentalDao: ManageRentalDao
    ): LocalDataSource {
        return LocalDataSourceImpl(
            rentalDao = rentalDao,
            imageDao = imageDao,
            manageRentalDao = manageRentalDao
        )
    }
}