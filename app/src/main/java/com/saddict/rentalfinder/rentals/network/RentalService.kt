package com.saddict.rentalfinder.rentals.network

import com.saddict.rentalfinder.prop.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.prop.Constants.LOGIN_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_CITY_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_COUNTRY_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_IMAGE_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_MANAGE_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_NEIGHBORHOOD_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_STATE_URL
import com.saddict.rentalfinder.prop.Constants.RENTAL_URL
import com.saddict.rentalfinder.prop.Constants.SINGLE_RENTAL_URL
import com.saddict.rentalfinder.prop.Constants.USER_PROFILE_URL
import com.saddict.rentalfinder.rentals.model.remote.City
import com.saddict.rentalfinder.rentals.model.remote.Country
import com.saddict.rentalfinder.rentals.model.remote.Neighborhood
import com.saddict.rentalfinder.rentals.model.remote.State
import com.saddict.rentalfinder.rentals.model.remote.UserProfile
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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RentalService {
    //  USER PROFILE API METHODS
    @GET(USER_PROFILE_URL)
    suspend fun getUserProfile(): UserProfile?

    @POST(USER_PROFILE_URL)
    suspend fun createUserProfile(@Body user: UserProfile): Response<UserProfile>

    @PUT(USER_PROFILE_URL)
    suspend fun updateUserProfile(@Body user: UserProfile): Response<UserProfile>

    @PATCH(USER_PROFILE_URL)
    suspend fun patchUserProfile(@Body user: UserProfile): Response<UserProfile>

    @DELETE(USER_PROFILE_URL)
    suspend fun deleteUserProfile(): Response<Unit> // Usually no body for DELETE

    //  AUTHENTICATION AND USER API METHODS
    @POST(CREATE_USER_URL)
    suspend fun registerUser(@Body user: RegisterUser): Response<RegisterUserResponse>

    @POST(LOGIN_URL)
    suspend fun loginUser(
        @Body user: LoginUser,
//        @Url url: String
    ): Response<LoginUserResponse>

    //  RENTAL API METHODS
    @GET(RENTAL_URL)
    suspend fun getRentals(
//        @Query("format") format: String,
        @Query("page") page: Int,
    ): RentalResponse

    @GET(RENTAL_MANAGE_URL)
    suspend fun getManageRentals(
//        @Query("format") format: String,
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

    @DELETE(SINGLE_RENTAL_URL)
    suspend fun deleteRental(@Path("id") id: Int): Response<Unit>

    //  RENTAL IMAGE API METHODS
    @GET(RENTAL_IMAGE_URL)
    suspend fun getImages(
//        @Query("format") format: String,
        @Query("page") page: Int,
    ): ImageResponse

    @Multipart
    @POST(RENTAL_IMAGE_URL)
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<RentalImageResults>

    //  LOCATION API METHODS
    @GET(RENTAL_COUNTRY_URL)
    suspend fun getCountries(): Country

    @GET(RENTAL_STATE_URL)
    suspend fun getStates(@Query("country") countryId: Int): State

    @GET(RENTAL_CITY_URL)
    suspend fun getCities(
        @Query("state") stateId: Int?,
        @Query("country") countryId: Int
    ): City

    @GET(RENTAL_NEIGHBORHOOD_URL)
    suspend fun getNeighborhoods(@Query("city") cityId: Int): Neighborhood
}
