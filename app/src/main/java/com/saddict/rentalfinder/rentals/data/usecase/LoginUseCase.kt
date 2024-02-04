package com.saddict.rentalfinder.rentals.data.usecase

import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.LoginUserResponse
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun loginUser(user: LoginUser): Response<LoginUserResponse> {
        return remoteDataSource.loginUser(user)
    }
}