package com.saddict.rentalfinder.rentals.data.remote.remository

import com.saddict.rentalfinder.rentals.model.remote.images.ImageResponse
import com.saddict.rentalfinder.rentals.model.remote.images.RentalImageResults
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUserResponse
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import com.saddict.rentalfinder.rentals.model.remote.rentals.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResponse
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
import com.saddict.rentalfinder.rentals.network.RentalService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val rentalService: RentalService
) : RemoteDataSource {
    override suspend fun loginUser(user: LoginUser): Response<LoginUserResponse> {
        return rentalService.loginUser(user)
    }

    override suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse> {
        return rentalService.registerUser(user)
    }

    override suspend fun getRentals(page: Int): RentalResponse {
        return rentalService.getRentals(format = "json", page = page)
    }

    override suspend fun getManageRentals(page: Int): RentalResponse {
        return rentalService.getManageRentals(format = "json", page = page)
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

    override suspend fun getImages(page: Int): ImageResponse {
        return rentalService.getImages(format = "json", page = page)
    }

    override suspend fun uploadImage(image: MultipartBody.Part): Response<RentalImageResults> {
        return rentalService.uploadImage(image)
    }
}