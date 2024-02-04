package com.saddict.rentalfinder.rentals.ui.registration.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.saddict.rentalfinder.rentals.data.manager.AppUiState
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.usecase.LoginUseCase
import com.saddict.rentalfinder.rentals.model.remote.LoginUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

sealed interface LoginUiState {
    data class Success(val userResponse: LoginUser) : LoginUiState
    data object Error : LoginUiState
    data object Loading : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    suspend fun loginUser(user: LoginUser) {
        try {
            withContext(Dispatchers.IO) {
                val login = LoginUseCase(remoteDataSource).loginUser(user)
                if (login.isSuccessful) {
                    val token = login.body()?.token
                    preferenceDataStore.setToken(token)
                    AppUiState.Success
                } else {
                    AppUiState.Error
                }
            }
        } catch (e: IOException) {
            Log.d("Login Failure", "cannot login the user")
            AppUiState.Error
        }
    }
}