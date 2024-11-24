package com.saddict.rentalfinder.discarded_codes

//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.jackson.JacksonConverterFactory
//import com.saddict.rentalfinder.prop.Constants.CREATE_USER_URL
//import com.saddict.rentalfinder.prop.Constants.LOGIN_URL
//import com.saddict.rentalfinder.prop.Prop.Prop.BASE_URL
//import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
//import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSourceImpl
//import com.saddict.rentalfinder.rentals.network.RentalService

//val requestInterceptor: Interceptor = Interceptor.invoke { chain ->
//    val request = chain.request()
//    val requestBuild = request.newBuilder()
//        .addHeader("Authorization", "Token 918986c29f72219caa2304f301c1a3ae3d2b34e7")
//        .header("Content-Type", "application/json")
//        .build()
//    chain.proceed(requestBuild)
//}

/* --------- VIEWMODEL ------------ */
//    LocationViewModel(
//        RemoteDataSourceImpl(
//            Retrofit
//                .Builder()
//                .baseUrl(BASE_URL)
//                .client(
//                    OkHttpClient()
//                        .newBuilder()
//                        .addInterceptor(requestInterceptor)
//                        .connectTimeout(60, TimeUnit.SECONDS)
//                        .readTimeout(60, TimeUnit.SECONDS)
//                        .writeTimeout(60, TimeUnit.SECONDS)
//                        .retryOnConnectionFailure(true)
//                        .build()
//                )
//                .addConverterFactory(JacksonConverterFactory.create())
//                .build()
//                .create(RentalService::class.java)
//        )
//    )
