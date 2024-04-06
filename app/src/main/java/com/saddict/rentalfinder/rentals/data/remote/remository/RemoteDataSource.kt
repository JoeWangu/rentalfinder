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
import okhttp3.MultipartBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loginUser(user: LoginUser): Response<LoginUserResponse>
    suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse>
    suspend fun getRentals(page: Int): RentalResponse
    suspend fun getManageRentals(page: Int): RentalResponse
    suspend fun getSingleRental(id: Int): RentalResults
    suspend fun postRental(body: CreateRental): Response<RentalResults>
    suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults>
    suspend fun getImages(page: Int): ImageResponse
    suspend fun uploadImage(image: MultipartBody.Part): Response<RentalImageResults>
}
