package com.saddict.rentalfinder.rentals.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.manager.ImageRemoteMediator
import com.saddict.rentalfinder.rentals.data.manager.RentalManageRemoteMediator
import com.saddict.rentalfinder.rentals.data.manager.RentalRemoteMediator
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalManageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDatabase: RentalDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getPagedRentals(): Flow<PagingData<RentalEntity>> {
        val pagingSourceFactory = { localDataSource.fetchPagedRentals() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 20
            ),
            remoteMediator = RentalRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPagedManageRentals(): Flow<PagingData<RentalManageEntity>> {
        val pagingSourceFactory = { localDataSource.fetchPagedManageRentals() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 20
            ),
            remoteMediator = RentalManageRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPagedImages(): Flow<PagingData<ImageEntity>> {
        val pagingSourceFactory = { localDataSource.fetchPagedImages() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 20
            ),
            remoteMediator = ImageRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}