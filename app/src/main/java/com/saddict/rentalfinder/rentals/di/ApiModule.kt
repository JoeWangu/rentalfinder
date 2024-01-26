package com.saddict.rentalfinder.rentals.di

import com.saddict.rentalfinder.prop.Prop.Prop.BASE_URL
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSourceImpl
import com.saddict.rentalfinder.rentals.network.RentalService
import com.saddict.rentalfinder.utils.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.utils.Constants.LOGIN_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideNetworkService(preferenceDataStore: PreferenceDataStore): RentalService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val requestInterceptor = Interceptor.invoke { chain ->
            val request = chain.request()
            val token = preferenceDataStore.getToken()
            println("Outgoing request to ${request.url}")
            println("Token is $token")
            return@invoke if (
                !request.url.encodedPath.contains(LOGIN_URL) &&
                !request.url.encodedPath.contains(CREATE_USER_URL)
            ) {
                val requestBuild = request.newBuilder()
                    .addHeader("Authorization", "Token $token")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(requestBuild)
            } else {
                val requestBuild = request.newBuilder().build()
                chain.proceed(requestBuild)
            }
        }
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RentalService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(rentalService: RentalService): RemoteDataSource {
        return RemoteDataSourceImpl(rentalService)
    }
}