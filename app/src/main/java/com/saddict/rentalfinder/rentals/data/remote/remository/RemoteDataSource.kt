package com.saddict.rentalfinder.rentals.data.remote.remository

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

interface RemoteDataSource {
    //    PROFILE DATA
    suspend fun getUserProfile(): UserProfile
    suspend fun createUserProfile(body: UserProfile): Response<UserProfile>
    suspend fun updateUserProfile(body: UserProfile): Response<UserProfile>
    suspend fun patchUserProfile(body: UserProfile): Response<UserProfile>
    suspend fun deleteUserProfile(): Response<Unit>

    //    USER DATA
    suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse>
    suspend fun loginUser(user: LoginUser): Response<LoginUserResponse>

    //    RENTAL DATA
    suspend fun getRentals(page: Int): RentalResponse
    suspend fun getManageRentals(page: Int): RentalResponse
    suspend fun getSingleRental(id: Int): RentalResults
    suspend fun postRental(body: CreateRental): Response<RentalResults>
    suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults>
    suspend fun deleteRental(id: Int): Response<Unit>

    //    IMAGE DATA
    suspend fun getImages(page: Int): ImageResponse
    suspend fun uploadImage(image: MultipartBody.Part): Response<RentalImageResults>

    //    LOCATION DATA
    suspend fun getCountries(): Country
    suspend fun getStates(countryId: Int): State
    suspend fun getCities(stateId: Int?, countryId: Int): City
    suspend fun getNeighborhoods(cityId: Int): Neighborhood
}
