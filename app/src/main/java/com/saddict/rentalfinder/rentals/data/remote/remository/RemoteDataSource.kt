package com.saddict.rentalfinder.rentals.data.remote.remository

import com.saddict.rentalfinder.rentals.model.remote.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.ImageList
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.rentals.model.remote.RentalImage
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUserResponse
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loginUser(user: LoginUser): Response<LoginUserResponse>
    suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse>
    suspend fun getRentals(page: Int): Rental
    suspend fun getSingleRental(id: Int): RentalResults
    suspend fun postRental(body: CreateRental): Response<RentalResults>
    suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults>
    suspend fun getImages(page: Int): ImageList
    suspend fun postImage(image: MultipartBody.Part): Response<RentalImage>
}
