package com.saddict.rentalfinder.rentals.di

import com.saddict.rentalfinder.prop.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.prop.Constants.LOGIN_URL
import com.saddict.rentalfinder.prop.Prop.Prop.BASE_URL
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSourceImpl
import com.saddict.rentalfinder.rentals.network.RentalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(preferenceDataStore: PreferenceDataStore): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val requestInterceptor = Interceptor.invoke { chain ->
            val request = chain.request()
            val token = preferenceDataStore.getToken()
            println("Outgoing request to ${request.url}")
            return@invoke if (
                !request.url.encodedPath.contains(LOGIN_URL) &&
                !request.url.encodedPath.contains(CREATE_USER_URL)
            ) {
                val requestBuild = request.newBuilder()
                    .addHeader("Authorization", "Token $token")
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(requestBuild)
            } else {
                val requestBuild = request.newBuilder().build()
                chain.proceed(requestBuild)
            }
        }
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRentalService(retrofit: Retrofit): RentalService {
        return retrofit.create(RentalService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(rentalService: RentalService): RemoteDataSource {
        return RemoteDataSourceImpl(rentalService)
    }
}