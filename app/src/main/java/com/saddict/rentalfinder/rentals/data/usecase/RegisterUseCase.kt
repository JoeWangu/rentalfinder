package com.saddict.rentalfinder.rentals.data.usecase

import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import retrofit2.Response
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse> {
        return remoteDataSource.registerUser(user)
    }
}
