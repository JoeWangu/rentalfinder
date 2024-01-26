package com.saddict.rentalfinder.rentals.network

import com.saddict.rentalfinder.rentals.model.remote.CreateRental
import com.saddict.rentalfinder.rentals.model.remote.CreateUser
import com.saddict.rentalfinder.rentals.model.remote.CreateUserResponse
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.rentals.model.remote.GetUser
import com.saddict.rentalfinder.rentals.model.remote.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.utils.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.utils.Constants.RENTAL_URL
import com.saddict.rentalfinder.utils.Constants.SINGLE_RENTAL_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RentalService {
    @POST
    suspend fun loginUser(@Body user: LoginUser): Response<GetUser>

    @POST(CREATE_USER_URL)
    suspend fun registerUser(@Body user: CreateUser): Response<CreateUserResponse>

    @GET(RENTAL_URL)
    suspend fun getRentals(
        @Query("format") format: String,
        @Query("page") page: Int,
    ): Rental

    @GET(SINGLE_RENTAL_URL)
    suspend fun getSingleRental(@Path("id") id: Int): RentalResults

    @POST(RENTAL_URL)
    suspend fun postRental(@Body body: CreateRental): Response<RentalResults>

    @PUT(SINGLE_RENTAL_URL)
    suspend fun updateRental(
        @Path("id") id: Int,
        @Body body: CreateRental
    ): Response<RentalResults>
}