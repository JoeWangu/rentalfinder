package com.saddict.rentalfinder.rentals.data.manager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.RentalRemoteKeys
import com.saddict.rentalfinder.rentals.network.RentalService
import com.saddict.rentalfinder.utils.mapToEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)

class RentalRemoteMediator @Inject constructor(
    private val appApi: RentalService,
    private val appDatabase: RentalDatabase
) : RemoteMediator<Int, RentalEntity>() {
    private val appDao = appDatabase.rentalDao()
    private val remoteKeysDao = appDatabase.rentalRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RentalEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = appApi.getRentals("json", currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    appDao.deleteAllImages()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { rental ->
                    RentalRemoteKeys(
                        id = rental.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                val rentalEntity = response.results.map { it.mapToEntity() }
                remoteKeysDao.addAllRemoteKeys(rentalRemoteKeys = keys)
                appDao.insertAllRentals(rentals = rentalEntity)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image ->
                remoteKeysDao.getRemoteKeys(id = image.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}