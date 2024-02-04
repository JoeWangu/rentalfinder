package com.saddict.rentalfinder.rentals.data.remote.remository

import com.saddict.rentalfinder.rentals.model.remote.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.CreateUser
import com.saddict.rentalfinder.rentals.model.remote.CreateUserResponse
import com.saddict.rentalfinder.rentals.model.remote.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.LoginUserResponse
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loginUser(user: LoginUser): Response<LoginUserResponse>
    suspend fun registerUser(user: CreateUser): Response<CreateUserResponse>
    suspend fun getRentals(page: Int): Rental
    suspend fun getSingleRental(id: Int): RentalResults
    suspend fun postRental(body: CreateRental): Response<RentalResults>
    suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults>
}