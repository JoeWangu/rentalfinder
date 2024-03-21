package com.saddict.rentalfinder.rentals.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.manager.CustomPagingSource
import com.saddict.rentalfinder.rentals.data.manager.ImageRemoteMediator
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(
        rentalDatabase: RentalDatabase,
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): Pager<Int, RentalEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = true
            ),
            remoteMediator = null,
            pagingSourceFactory = {
                CustomPagingSource(
                    rentalDatabase, remoteDataSource, context
                )
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideImagePager(
        rentalDatabase: RentalDatabase,
        remoteDataSource: RemoteDataSource,
    ): Pager<Int, ImageEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            remoteMediator = ImageRemoteMediator(
                rentalDatabase, remoteDataSource
            ),
            pagingSourceFactory = {
                rentalDatabase.rentalDao().pagingSourceImages()
//                ImagePagingSource(
//                    rentalDatabase = rentalDatabase,
//                    remoteDataSource = remoteDataSource
//                )
            }
        )
    }
}