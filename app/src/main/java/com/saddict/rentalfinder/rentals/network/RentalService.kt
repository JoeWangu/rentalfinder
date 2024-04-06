package com.saddict.rentalfinder.rentals.network

import com.saddict.rentalfinder.rentals.model.remote.images.ImageResponse
import com.saddict.rentalfinder.rentals.model.remote.images.RentalImageResults
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUserResponse
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import com.saddict.rentalfinder.rentals.model.remote.rentals.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResponse
import com.saddict.rentalfinder.rentals.model.remote.rentals.RentalResults
import com.saddict.rentalfinder.utils.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.utils.Constants.LOGIN_URL
import com.saddict.rentalfinder.utils.Constants.RENTAL_IMAGE_URL
import com.saddict.rentalfinder.utils.Constants.RENTAL_MANAGE_URL
import com.saddict.rentalfinder.utils.Constants.RENTAL_URL
import com.saddict.rentalfinder.utils.Constants.SINGLE_RENTAL_URL
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RentalService {
    @POST(LOGIN_URL)
    suspend fun loginUser(
        @Body user: LoginUser,
//        @Url url: String
    ): Response<LoginUserResponse>

    @POST(CREATE_USER_URL)
    suspend fun registerUser(@Body user: RegisterUser): Response<RegisterUserResponse>

    @GET(RENTAL_URL)
    suspend fun getRentals(
        @Query("format") format: String,
        @Query("page") page: Int,
    ): RentalResponse

    @GET(RENTAL_MANAGE_URL)
    suspend fun getManageRentals(
        @Query("format") format: String,
        @Query("page") page: Int,
    ): RentalResponse

    @GET(SINGLE_RENTAL_URL)
    suspend fun getSingleRental(@Path("id") id: Int): RentalResults

    @POST(RENTAL_MANAGE_URL)
    suspend fun postRental(@Body body: CreateRental): Response<RentalResults>

    @PUT(SINGLE_RENTAL_URL)
    suspend fun updateRental(
        @Path("id") id: Int,
        @Body body: CreateRental
    ): Response<RentalResults>

    @GET(RENTAL_IMAGE_URL)
    suspend fun getImages(
        @Query("format") format: String,
        @Query("page") page: Int,
    ): ImageResponse

    @Multipart
    @POST(RENTAL_IMAGE_URL)
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<RentalImageResults>
}
