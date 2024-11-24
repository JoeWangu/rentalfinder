package com.saddict.rentalfinder.rentals.ui.registration.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.usecase.LoginUseCase
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.register.LoginUserResponse
import com.saddict.rentalfinder.utils.mapToUserProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

sealed interface LoginUiState {
    data class Success(val userResponse: LoginUserResponse) : LoginUiState
    data object Error : LoginUiState
    data object NetError : LoginUiState
    data object Loading : LoginUiState
    data object Idle : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ViewModel() {
//    private val _uiState = MutableSharedFlow<LoginUiState>()
//    val uiState: SharedFlow<LoginUiState> = _uiState

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            withContext(Dispatchers.IO) {
                try {
                    val user = LoginUser(email = email, password = password)
                    val login = LoginUseCase(remoteDataSource).loginUser(user)
                    if (login.isSuccessful) {
                        val response = login.body()
                        val token = login.body()!!.token
                        preferenceDataStore.setToken(token)
                        val userEntity = UserEntity(
                            id = 1,
                            email = email,
                            username = response?.user ?: ""
                        )
                        localDataSource.deleteUser()
                        localDataSource.insertUser(userEntity)
//                        Log.d("Success", response.toString())
                        _uiState.value = LoginUiState.Success(response!!)
                        launch {
                            try {
                                val userProfile = remoteDataSource.getUserProfile()
                                    ?.mapToUserProfileEntity()
                                if (userProfile != null) {
                                    localDataSource.insertUserProfile(userProfile)
                                }
                            } catch (_: HttpException) {
                            } catch (_: Exception) {
                            }
                        }
                    } else {
                        _uiState.value = LoginUiState.Error
//                        val errorBody = login.raw()
//                        Log.e("NoSuccess", "Error: $errorBody")
                    }
                } catch (_: Exception) {
//                    Log.d("Login Failure", "cannot login the user: $e")
                    _uiState.value = LoginUiState.NetError
                }
            }
        }
    }
}