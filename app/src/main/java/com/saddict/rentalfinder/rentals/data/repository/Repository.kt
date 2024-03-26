package com.saddict.rentalfinder.rentals.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.manager.ImageRemoteMediator
import com.saddict.rentalfinder.rentals.data.manager.RentalRemoteMediator
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.network.RentalService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val appApi: RentalService,
    private val appDatabase: RentalDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllImages(): Flow<PagingData<ImageEntity>> {
        val pagingSourceFactory = { appDatabase.rentalDao().getAllPagedImages() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 20
            ),
            remoteMediator = ImageRemoteMediator(appApi, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getAllRentals(): Flow<PagingData<RentalEntity>> {
        val pagingSourceFactory = { appDatabase.rentalDao().fetchAllPagedRentals() }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 20
            ),
            remoteMediator = RentalRemoteMediator(appApi, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}