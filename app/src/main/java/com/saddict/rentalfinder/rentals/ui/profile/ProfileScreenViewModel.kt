package com.saddict.rentalfinder.rentals.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.prop.Constants.TIMEOUT_MILLIS
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.local.UserProfileEntity
import com.saddict.rentalfinder.utils.mapToCreateUserProfile
import com.saddict.rentalfinder.utils.mapToProfileUiState
import com.saddict.rentalfinder.utils.mapToUserProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

sealed interface ProfileUiScreenState {
    data class SuccessCreating(val userProfile: UserProfileEntity) : ProfileUiScreenState
    data class SuccessUpdating(val userProfile: UserProfileEntity) : ProfileUiScreenState
    data object Error : ProfileUiScreenState
    data object NetworkError : ProfileUiScreenState
    data object Loading : ProfileUiScreenState
    data object Idle : ProfileUiScreenState
}

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : ViewModel() {
    var editProfileState by mutableStateOf(ProfileDetailsUiState())
        private set

    private var _uiState = MutableStateFlow<ProfileUiScreenState>(ProfileUiScreenState.Idle)
    val uiState: StateFlow<ProfileUiScreenState> = _uiState

    init {
        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
            editProfileState = localDataSource.fetchUserProfile()
                .filterNotNull()
                .first()
                .mapToProfileUiState()
//            }
        }
    }

    fun updateUiState(profileState: ProfileState) {
        editProfileState = ProfileDetailsUiState(userProfileState = profileState)
    }

    val profileUiState: StateFlow<ProfileUiState> =
        localDataSource.fetchUser().map {
            ProfileUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfileUiState()
        )

    val profileDetailsUiState: StateFlow<ProfileState> =
        localDataSource.fetchUserProfile().map {
            ProfileState(
                id = it.id,
                firstName = it.firstName ?: "",
                lastName = it.lastName ?: "",
                phoneNumber = it.phoneNumber ?: "",
                address = it.address ?: "",
                dob = it.dob ?: "",
                gender = it.gender ?: "",
                profilePicture = it.profilePicture ?: "",
                userBio = it.userBio ?: "",
                userCountry = it.userCountry ?: "",
                userState = it.userState ?: "",
                userCity = it.userCity ?: "",
                userNeighborhood = it.userNeighborhood ?: "",
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfileState()
        )

    fun createOrupdateUserProfile() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = ProfileUiScreenState.Loading
                try {
                    val profile = editProfileState.mapToCreateUserProfile()
                    val isProfileNull = remoteDataSource.getUserProfile()
                    if (isProfileNull == null) {
                        val response = remoteDataSource.createUserProfile(profile)
                        if (response.isSuccessful) {
                            val responseBody = response.body()?.mapToUserProfileEntity()
                            localDataSource.insertUserProfile(responseBody!!)
                            _uiState.value = ProfileUiScreenState.SuccessCreating(responseBody)
                        }
                    } else {
                        val response = remoteDataSource.patchUserProfile(profile)
                        if (response.isSuccessful) {
                            val responseBody = response.body()?.mapToUserProfileEntity()
                            localDataSource.insertUserProfile(responseBody!!)
                            _uiState.value = ProfileUiScreenState.SuccessUpdating(responseBody)
                        }
                    }
                } catch (_: HttpException) {
                    _uiState.value = ProfileUiScreenState.NetworkError
                } catch (_: Exception) {
                    _uiState.value = ProfileUiScreenState.Error
                }
            }
        }
    }
}

data class ProfileUiState(val user: UserEntity = UserEntity(id = 1, email = "", username = "User"))
data class ProfileDetailsUiState(val userProfileState: ProfileState = ProfileState())

data class ProfileState(
    val id: Int = 1,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val dob: String? = null,
    val gender: String = "",
    val profilePicture: String = "",
    val userBio: String = "",
    val userCountry: String? = null,
    val userState: String? = null,
    val userCity: String? = null,
    val userNeighborhood: String? = null,
    val country: Int? = null,
    val state: Int? = null,
    val city: Int? = null,
    val neighborhood: Int? = null
)
