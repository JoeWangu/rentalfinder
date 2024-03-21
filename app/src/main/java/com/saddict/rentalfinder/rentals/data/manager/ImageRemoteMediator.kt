package com.saddict.rentalfinder.rentals.data.manager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.ImageEntity
import com.saddict.rentalfinder.rentals.model.local.RemoteKeys
import com.saddict.rentalfinder.utils.Constants.INITIAL_PAGE
import com.saddict.rentalfinder.utils.mapToImageEntity
import kotlinx.coroutines.flow.first
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator @Inject constructor(
    private val rentalDatabase: RentalDatabase,
    private val remoteDataSource: RemoteDataSource,
) : RemoteMediator<Int, ImageEntity>() {
    private val rentalDao = rentalDatabase.rentalDao()
    private val keysDao = rentalDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.SKIP_INITIAL_REFRESH
//
//        return super.initialize()

        val cacheTimeout = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS)
        val images = rentalDao.fetchImages().first()
        return if (
            System.currentTimeMillis() - rentalDatabase.remoteKeysDao()
                .remoteKeyByQuery("imageKey").lastUpdated <= cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.LAUNCH_INITIAL_REFRESH

        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            if (images.isEmpty()) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            val currentTimeMillis = System.currentTimeMillis()
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    lastItem.id
//                    val page = INITIAL_PAGE
                    val remoteKey = rentalDatabase.withTransaction {
                        keysDao.remoteKeyByQuery("imageKey")
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPage
                }
            }
//            val page = Constants.INITIAL_PAGE
//            val page = state.config.pageSize / 10
            val response = remoteDataSource.getImages(page = loadKey ?: INITIAL_PAGE)
            rentalDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    keysDao.deleteByQuery("imageKey")
                }
                keysDao.insertOrReplace(
                    RemoteKeys(
                        label = "imageKey",
                        nextKey = response.next,
                        prevKey = response.previous,
                        nextPage = loadKey?.plus(1),
                        prevPage = loadKey?.minus(1),
                        lastUpdated = currentTimeMillis
                    )
                )
            }
            val images = response.imageList
            rentalDao.upsertAllImages(images.map { it.mapToImageEntity() })
//            val endOfPaginationReached = images.isEmpty()
            MediatorResult.Success(
                endOfPaginationReached = response.next == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}