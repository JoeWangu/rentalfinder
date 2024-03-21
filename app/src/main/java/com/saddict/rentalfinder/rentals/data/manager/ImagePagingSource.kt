package com.saddict.rentalfinder.rentals.data.manager
//
//import android.content.Context
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.saddict.rentalfinder.rentals.data.local.RentalDatabase
//import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
//import com.saddict.rentalfinder.rentals.model.remote.RentalImage
//import com.saddict.rentalfinder.utils.Constants
//import com.saddict.rentalfinder.utils.mapToEntity
//import com.saddict.rentalfinder.utils.mapToImageEntity
//import com.saddict.rentalfinder.utils.mapToRentalImage
//import com.saddict.rentalfinder.utils.toastUtil
//import javax.inject.Inject
//
//class ImagePagingSource @Inject constructor(
//    private val rentalDatabase: RentalDatabase,
//    private val remoteDataSource: RemoteDataSource,
////    private val context: Context
//) : PagingSource<Int, RentalImage>() {
//    override fun getRefreshKey(state: PagingState<Int, RentalImage>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RentalImage> {
//        val page = params.key ?: Constants.INITIAL_PAGE
//        return try {
//            val response = remoteDataSource.getImages(page)
////            println(response)
//            val entities = response.imageList.map { it.mapToImageEntity() }
//            rentalDatabase.rentalDao().upsertAllImages(entities)
////            val dataLoaded = rentalDatabase.rentalDao().getAllPaged()
//            LoadResult.Page(
//                data = response.imageList,
//                prevKey = response.previous?.toInt(),
//                nextKey = response.next?.toInt()
////                prevKey = if (page == Constants.INITIAL_PAGE) null else page - 1,
////                nextKey = if (response.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            val dataFromDb = rentalDatabase.rentalDao().getAllImages()
//                .map { it.mapToRentalImage() }
//            if (dataFromDb.isEmpty()) {
////                context.toastUtil("database is empty : check internet connection")
//                LoadResult.Error(e)
//            } else {
////                context.toastUtil("Cannot load data, check internet connection")
//                val numberOfPages = dataFromDb.size / 10
//                LoadResult.Page(
//                    data = dataFromDb,
//                    prevKey = if (page == Constants.INITIAL_PAGE) null else page - 1,
//                    nextKey = if (page > numberOfPages) null else page + 1
//                )
//            }
//        }
//    }
//}