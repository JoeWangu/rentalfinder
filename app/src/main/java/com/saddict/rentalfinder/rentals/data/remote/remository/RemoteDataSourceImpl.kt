package com.saddict.rentalfinder.rentals.data.remote.remository

import com.saddict.rentalfinder.rentals.model.remote.City
import com.saddict.rentalfinder.rentals.model.remote.Country
import com.saddict.rentalfinder.rentals.model.remote.CreateUserProfile
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
import com.saddict.rentalfinder.rentals.network.RentalService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val rentalService: RentalService
) : RemoteDataSource {
    //    PROFILE DATA
    override suspend fun getUserProfile(): Response<UserProfile> {
        return rentalService.getUserProfile()
    }

    override suspend fun createUserProfile(body: CreateUserProfile): Response<UserProfile> {
        return rentalService.createUserProfile(body)
    }

    override suspend fun updateUserProfile(body: CreateUserProfile): Response<UserProfile> {
        return rentalService.updateUserProfile(body)
    }

    override suspend fun patchUserProfile(body: CreateUserProfile): Response<UserProfile> {
        return rentalService.patchUserProfile(body)
    }

    override suspend fun deleteUserProfile(): Response<Unit> {
        return rentalService.deleteUserProfile()
    }

    //    USER DATA
    override suspend fun loginUser(user: LoginUser): Response<LoginUserResponse> {
        return rentalService.loginUser(user)
    }

    override suspend fun registerUser(user: RegisterUser): Response<RegisterUserResponse> {
        return rentalService.registerUser(user)
    }

    //    RENTAL DATA
    override suspend fun getRentals(page: Int): RentalResponse {
        return rentalService.getRentals(page = page)
    }

//    override suspend fun getRentals(page: Int): RentalResponse {
//        return rentalService.getRentals(format = "json", page = page)
//    }

    override suspend fun getManageRentals(page: Int): RentalResponse {
        return rentalService.getManageRentals(page = page)
    }

//    override suspend fun getManageRentals(page: Int): RentalResponse {
//        return rentalService.getManageRentals(format = "json", page = page)
//    }

    override suspend fun getSingleRental(id: Int): RentalResults {
        return rentalService.getSingleRental(id)
    }

    override suspend fun postRental(body: CreateRental): Response<RentalResults> {
        return rentalService.postRental(body)
    }

    override suspend fun updateRental(id: Int, body: CreateRental): Response<RentalResults> {
        return rentalService.updateRental(id, body)
    }

    override suspend fun deleteRental(id: Int): Response<Unit> {
        return rentalService.deleteRental(id)
    }

    //    IMAGE DATA
    override suspend fun getImages(page: Int): ImageResponse {
        return rentalService.getImages(page = page)
    }

//    override suspend fun getImages(page: Int): ImageResponse {
//        return rentalService.getImages(format = "json", page = page)
//    }

    override suspend fun uploadImage(image: MultipartBody.Part): Response<RentalImageResults> {
        return rentalService.uploadImage(image)
    }

    //    LOCATION DATA
    override suspend fun getCountries(): Country {
        return rentalService.getCountries()
    }

    override suspend fun getStates(countryId: Int): State {
        return rentalService.getStates(countryId)
    }

    override suspend fun getCities(stateId: Int?, countryId: Int): City {
        return rentalService.getCities(stateId, countryId)
    }

    override suspend fun getNeighborhoods(cityId: Int): Neighborhood {
        return rentalService.getNeighborhoods(cityId)
    }
}