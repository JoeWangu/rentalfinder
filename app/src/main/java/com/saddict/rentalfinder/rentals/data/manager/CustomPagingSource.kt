package com.saddict.rentalfinder.rentals.data.manager

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.RemoteKeys
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.utils.Constants.INITIAL_PAGE
import com.saddict.rentalfinder.utils.mapToEntity
import com.saddict.rentalfinder.utils.toastUtil
import javax.inject.Inject

class CustomPagingSource @Inject constructor(
    private val rentalDatabase: RentalDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val context: Context
) : PagingSource<Int, RentalEntity>() {
    private val keysDao = rentalDatabase.remoteKeysDao()
    override fun getRefreshKey(state: PagingState<Int, RentalEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RentalEntity> {
        val page = params.key ?: INITIAL_PAGE
        val currentTimeMillis = System.currentTimeMillis()
        return try {
            rentalDatabase.withTransaction {
                keysDao.insertOrReplace(
                    RemoteKeys(
                        label = "imageKey",
                        nextKey = null,
                        prevKey = null,
                        nextPage = null,
                        prevPage = null,
                        lastUpdated = currentTimeMillis
                    )
                )
            }
            val response = remoteDataSource.getRentals(page).results
//            println(response)
            val entities = response.map { it.mapToEntity() }
            rentalDatabase.rentalDao().upsertAllRentals(entities)
            val dataLoaded = rentalDatabase.rentalDao().getAllPaged()
            LoadResult.Page(
                data = dataLoaded,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            val dataFromDb = rentalDatabase.rentalDao().getAllPaged()
            if (dataFromDb.isEmpty()) {
                context.toastUtil("database is empty : check internet connection")
                LoadResult.Error(e)
            } else {
                context.toastUtil("Cannot load data, check internet connection")
                val numberOfPages = dataFromDb.size / 10
                LoadResult.Page(
                    data = dataFromDb,
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (page > numberOfPages) null else page + 1
                )
            }
        }
    }
}