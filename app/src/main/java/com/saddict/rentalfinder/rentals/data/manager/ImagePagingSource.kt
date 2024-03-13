package com.saddict.rentalfinder.rentals.data.manager

//class ImagePagingSource @Inject constructor(
//    private val remoteDataSource: RemoteDataSource,
//    private val context: Context
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
//            val response = remoteDataSource.getRentals(page).results
////            println(response)
//            val entities = response.map { it.mapToEntity() }
//            rentalDatabase.rentalDao().upsertAllRentals(entities)
//            val dataLoaded = rentalDatabase.rentalDao().getAllPaged()
//            LoadResult.Page(
//                data = dataLoaded,
//                prevKey = if (page == Constants.INITIAL_PAGE) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            val dataFromDb = rentalDatabase.rentalDao().getAllPaged()
//            if (dataFromDb.isEmpty()) {
//                context.toastUtil("database is empty : check internet connection")
//                LoadResult.Error(e)
//            } else {
//                context.toastUtil("Cannot load data, check internet connection")
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