package com.saddict.rentalfinder.rentals.data.manager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.utils.Constants.INITIAL_PAGE
import com.saddict.rentalfinder.utils.mapToEntity
import javax.inject.Inject

class CustomPagingSource @Inject constructor(
    private val rentalDatabase: RentalDatabase,
    private val remoteDataSource: RemoteDataSource
): PagingSource<Int, RentalEntity>() {
    override fun getRefreshKey(state: PagingState<Int, RentalEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RentalEntity> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = remoteDataSource.getRentals(page).results
            val entities = response.map { it.mapToEntity() }
            rentalDatabase.rentalDao().upsertAllRentals(entities)
            val dataLoaded = rentalDatabase.rentalDao().getAllPaged()
            LoadResult.Page(
                data = dataLoaded,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}