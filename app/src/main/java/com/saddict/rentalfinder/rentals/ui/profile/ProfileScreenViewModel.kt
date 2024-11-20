package com.saddict.rentalfinder.rentals.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.prop.Constants.TIMEOUT_MILLIS
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.utils.mapToUserProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : ViewModel() {
    init {
        viewModelScope.launch {
            loadUserProfile()
        }
    }

    private suspend fun loadUserProfile() {
        try {
            val userProfile = remoteDataSource.getUserProfile()
                ?.mapToUserProfileEntity()
            if (userProfile != null) {
                localDataSource.insertUserProfile(userProfile)
            }
        } catch (e: HttpException) {
        } catch (e: Exception) {
        }
    }

    val profileUiState: StateFlow<ProfileUiState> =
        localDataSource.fetchUser().map {
            ProfileUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfileUiState()
        )

    val profileDetailsUiState: StateFlow<ProfileDetailsUiState> =
        localDataSource.fetchUserProfile().map {
            ProfileDetailsUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfileDetailsUiState()
        )
}

data class ProfileUiState(val user: UserEntity = UserEntity(id = 1, email = "", username = "User"))
data class ProfileDetailsUiState(
    val userProfileEntity: UserProfileEntity? = UserProfileEntity(
        id = 1,
        firstName = "",
        lastName = "",
        phoneNumber = "",
        address = "",
        dob = "",
        gender = "",
        profilePicture = "",
        userBio = "",
        userCountry = "",
        userState = "",
        userCity = "",
        userNeighborhood = ""
    )
)