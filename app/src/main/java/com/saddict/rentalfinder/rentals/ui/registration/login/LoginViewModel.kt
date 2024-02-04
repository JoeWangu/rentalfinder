package com.saddict.rentalfinder.rentals.ui.registration.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.usecase.LoginUseCase
import com.saddict.rentalfinder.rentals.model.remote.LoginUser
import com.saddict.rentalfinder.rentals.model.remote.LoginUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

sealed interface LoginUiState {
    data class Success(val userResponse: LoginUserResponse) : LoginUiState
    data object Error : LoginUiState
    data object Loading : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    private val _uiState = MutableSharedFlow<LoginUiState>()
    val uiState: SharedFlow<LoginUiState> = _uiState
    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiState.emit(LoginUiState.Loading)
                    val user = LoginUser(email = email, password = password)
                    val login = LoginUseCase(remoteDataSource).loginUser(user)
                    if (login.isSuccessful) {
                        val response = login.body()
                        val token = login.body()!!.token
                        preferenceDataStore.setToken(token)
                        Log.d("Success", response.toString())
                        _uiState.emit(LoginUiState.Success(response!!))
                    } else {
                        _uiState.emit(LoginUiState.Error)
                        val errorBody = login.raw()
                        Log.e("NotSent", "Error: $errorBody")
                    }
                } catch (e: IOException) {
                    Log.d("Login Failure", "cannot login the user: $e")
                    _uiState.emit(LoginUiState.Error)
                }
            }
        }
    }
}