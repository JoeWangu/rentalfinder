package com.saddict.rentalfinder.rentals.data.manager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalManageRemoteKeysEntity
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalRemoteKeysEntity
import com.saddict.rentalfinder.utils.mapToEntity
import com.saddict.rentalfinder.utils.mapToManageEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RentalRemoteMediator @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDatabase: RentalDatabase
) : RemoteMediator<Int, RentalEntity>() {
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

            val response = remoteDataSource.getRentals(currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllRentals()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { rental ->
                    RentalRemoteKeysEntity(
                        id = rental.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                val rentalEntity = response.results.map { it.mapToEntity() }
                remoteKeysDao.addRemoteKeys(rentalRemoteKeyEntities = keys)
                localDataSource.insertRentals(rentals = rentalEntity)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image ->
                remoteKeysDao.getRemoteKeys(id = image.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, RentalEntity>
    ): RentalRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}

//manage rentals
@OptIn(ExperimentalPagingApi::class)
class RentalManageRemoteMediator @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appDatabase: RentalDatabase
) : RemoteMediator<Int, RentalManageEntity>() {
    private val remoteManageKeysDao = appDatabase.rentalManageRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RentalManageEntity>
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

            val response = remoteDataSource.getManageRentals(currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    localDataSource.deleteAllRentals()
                    remoteManageKeysDao.deleteAllRemoteManageKeys()
                }
                val keys = response.results.map { rental ->
                    RentalManageRemoteKeysEntity(
                        id = rental.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                val rentalEntity = response.results.map { it.mapToManageEntity() }
                remoteManageKeysDao.addRemoteManageKeys(rentalManageRemoteKeyEntities = keys)
                localDataSource.insertManageRentals(rentals = rentalEntity)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RentalManageEntity>
    ): RentalManageRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteManageKeysDao.getRemoteManageKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, RentalManageEntity>
    ): RentalManageRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image ->
                remoteManageKeysDao.getRemoteManageKeys(id = image.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, RentalManageEntity>
    ): RentalManageRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                remoteManageKeysDao.getRemoteManageKeys(id = unsplashImage.id)
            }
    }
}