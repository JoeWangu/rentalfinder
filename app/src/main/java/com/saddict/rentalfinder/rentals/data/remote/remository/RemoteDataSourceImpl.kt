package com.saddict.rentalfinder.rentals.data.remote.remository

import com.saddict.rentalfinder.rentals.model.remote.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUserResponse
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import com.saddict.rentalfinder.rentals.network.RentalService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val rentalService: RentalService
) : RemoteDataSource {
    override suspend fun loginUser(user: LoginUser): Response<LoginUserResponse> {
        return rentalService.loginUser(user, "/")
    }

    override suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse> {
        return rentalService.registerUser(user)
    }

    override suspend fun getRentals(page: Int): Rental {
        return rentalService.getRentals("json", page)
    }

    override suspend fun getSingleRental(id: Int): RentalResults {
        return rentalService.getSingleRental(id)
    }

    override suspend fun postRental(body: CreateRental): Response<RentalResults> {
        return rentalService.postRental(body)
    }

    override suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults> {
        return rentalService.updateRental(id, body)
    }
}