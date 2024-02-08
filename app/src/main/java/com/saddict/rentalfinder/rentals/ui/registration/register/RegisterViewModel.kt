package com.saddict.rentalfinder.rentals.ui.registration.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.usecase.RegisterUseCase
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface RegisterUiState {
    data class Success(val userResponse: RegisterUserResponse) : RegisterUiState
    data object Error : RegisterUiState
    data object Loading : RegisterUiState
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    private val _uiState = MutableSharedFlow<RegisterUiState>()
    val uiState: SharedFlow<RegisterUiState> = _uiState
    var registerEntryUiState by mutableStateOf(RegisterEntryUiState())
        private set

    fun registerUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    if (validateRegisterInput()) {
                        _uiState.emit(RegisterUiState.Loading)
                        val user = registerEntryUiState.registerDetails.toRegisterUser()
                        Log.d("register tag", "registerUser: $user")
                        val register = RegisterUseCase(remoteDataSource).registerUser(user)
                        if (register.isSuccessful) {
                            val token = register.body()?.token
                            val response = register.body()
                            preferenceDataStore.setToken(token)
                            Log.d("Success", response.toString())
                            _uiState.emit(RegisterUiState.Success(response!!))
                        } else {
                            _uiState.emit(RegisterUiState.Error)
                            Log.e("register error", "register not successful")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RegisterUserError", "cannot register user $e")
                }
            }
        }
    }

    private fun validateRegisterInput(uiState: RegisterDetails = registerEntryUiState.registerDetails): Boolean {
        return with(uiState) {
            firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
                    && password.isNotBlank() && username.isNotBlank() && phoneNumber.isNotBlank()
                    && address.isNotBlank()
        }
    }

    fun updateUiState(registerDetails: RegisterDetails) {
        registerEntryUiState = RegisterEntryUiState(
            registerDetails = registerDetails,
            isRegisterValid = validateRegisterInput(registerDetails)
        )
    }
}

data class RegisterEntryUiState(
    val registerDetails: RegisterDetails = RegisterDetails(),
    val isRegisterValid: Boolean = false
)

data class RegisterDetails(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val dob: String? = "1996-04-24",
    val gender: String? = "M"
)

fun RegisterDetails.toRegisterUser(): RegisterUser {
    return RegisterUser(
        first_name = firstName,
        last_name = lastName,
        email = email,
        password = password,
        username = username,
        phone_number = phoneNumber,
        address = address,
        dob = dob,
        gender = gender
    )
}

//        firstName: String,
//        lastName: String,
//        email: String,
//        password: String,
//        username: String,
//        phoneNumber: String,
//        address: String,
//        dob: Any? = null,
//        gender: Any? = null

//                    val user = RegisterUser(
//                        first_name = firstName,
//                        last_name = lastName,
//                        email = email,
//                        password = password,
//                        username = username,
//                        phone_number = phoneNumber,
//                        address = address,
//                        dob = dob,
//                        gender = gender
//                    )