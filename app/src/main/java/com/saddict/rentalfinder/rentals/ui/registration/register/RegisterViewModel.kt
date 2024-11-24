package com.saddict.rentalfinder.rentals.ui.registration.register

import androidx.compose.runtime.getValue
//import android.util.Log
//import android.content.Context
//import android.os.Handler
//import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.local.locasitory.LocalDataSource
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.usecase.RegisterUseCase
import com.saddict.rentalfinder.rentals.model.local.UserEntity
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUser
import com.saddict.rentalfinder.rentals.model.remote.register.RegisterUserResponse
import com.saddict.rentalfinder.utils.mapToUserProfileEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject


// ToDo
//  1. remove logging in production code

sealed interface RegisterUiState {
    data class Success(val userResponse: RegisterUserResponse) : RegisterUiState
    data object Error : RegisterUiState
    data object Loading : RegisterUiState
    data object Idle : RegisterUiState
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    var registerEntryUiState by mutableStateOf(RegisterEntryUiState())
        private set

    private val _errorMessages = MutableStateFlow("")
    val errorMessages: StateFlow<String> = _errorMessages.asStateFlow()

    fun registerUser() {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            withContext(Dispatchers.IO) {
                try {
                    if (validateRegisterInput()) {
                        val user = registerEntryUiState.registerDetails.toRegisterUser()
//                        Log.d("register tag", "registerUser: $user")
                        val register = RegisterUseCase(remoteDataSource).registerUser(user)
                        if (register.isSuccessful) {
                            val token = register.body()?.token
                            val response = register.body()
                            preferenceDataStore.setToken(token)
                            val userEntity = UserEntity(
                                id = 1,
                                email = response?.user?.email ?: "",
                                username = response?.user?.username ?: ""
                            )
                            localDataSource.deleteUser()
                            localDataSource.insertUser(userEntity)
//                            Log.d("Success", response.toString())
                            _uiState.value = RegisterUiState.Success(response!!)
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
                            // Attempt to read and parse the error body
                            val errorBody = register.errorBody()?.string()
                            val errorMessage = errorBody?.let {
                                try {
                                    val jsonObject = JSONObject(it)
                                    val errorMessages = StringBuilder()

                                    jsonObject.keys().forEach { key ->
                                        val messages = jsonObject.getJSONArray(key)
                                        for (i in 0 until messages.length()) {
                                            errorMessages.append("${messages.getString(i)}\n")
                                        }
                                    }

                                    errorMessages.toString().trim() // remove last newline
                                } catch (_: Exception) {
//                                    Log.e("ErrorParsing", "Failed to parse error message: $e")
                                    "Unknown error occurred"
                                }
                            } ?: "Unknown error occurred"
//                            val errorMessage = register.errorBody()?.string()
//                            val errors: List<String> = errorMessage?.let {
//                                try {
//                                    val jsonObject = JSONObject(it)
//                                    val allErrors = mutableListOf<String>()
//                                    jsonObject.getJSONArray("username").getString(0)
//                                        .let { usernameError ->
//                                            allErrors.add(usernameError)
//                                        }
//                                    jsonObject.getJSONArray("email").getString(0)
//                                        .let { emailError ->
//                                            allErrors.add(emailError)
//                                        }
//                                    jsonObject.getJSONArray("password").getString(0)
//                                        .let { passwordError ->
//                                            allErrors.add(passwordError)
//                                        }
//                                    allErrors
//                                } catch (e: Exception) {
//                                    emptyList()
//                                }
//                            } ?: emptyList()
                            _errorMessages.value = errorMessage
                            _uiState.value = RegisterUiState.Error
//                            Handler(Looper.getMainLooper()).post {
//                                Toast.makeText(context, "${register.errorBody()?.string()}", Toast.LENGTH_LONG).show()
//                            }
                        }
                    }
                } catch (_: Exception) {
//                    Log.e("RegisterUserError", "cannot register user $e")
                    _uiState.value = RegisterUiState.Error
                }
            }
        }
    }

//    fun clearErrors() {
//        _errorMessages.value = ""
//    }

    private fun validateRegisterInput(uiState: RegisterDetails = registerEntryUiState.registerDetails): Boolean {
        return with(uiState) {
//            firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
//                    && password.isNotBlank() && username.isNotBlank() && phoneNumber.isNotBlank()
//                    && address.isNotBlank()
            email.isNotBlank() && password.isNotBlank() && username.isNotBlank()
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
//    val firstName: String = "",
//    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val username: String = "",
//    val phoneNumber: String = "",
//    val address: String = "",
//    val dob: String? = "1996-04-24",
//    val gender: String? = "Male"
)

fun RegisterDetails.toRegisterUser(): RegisterUser {
    return RegisterUser(
//        first_name = firstName,
//        last_name = lastName,
        email = email,
        password = password,
        username = username,
//        phone_number = phoneNumber,
//        address = address,
//        dob = dob,
//        gender = gender
    )
}