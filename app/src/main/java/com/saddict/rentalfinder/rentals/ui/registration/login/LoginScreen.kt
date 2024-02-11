package com.saddict.rentalfinder.rentals.ui.registration.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LoginDestination : NavigationDestination {
    override val route: String = "login"
    override val titleRes: Int = R.string.login
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.login),
                canNavigateBack = false,
            )
        }
    ) {
        LoginBody(
            navigateToRegister = navigateToRegister,
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun LoginBody(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
//            label = { Text(text = stringResource(id = R.string.email)) },
            placeholder = { Text(text = stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
//            label = { Text(text = stringResource(id = R.string.password)) },
            placeholder = { Text(text = stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            shape = MaterialTheme.shapes.large,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Check else Icons.Default.Info,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    loginViewModel.loginUser(email = email, password = password)
                    loginViewModel.uiState.collect { state ->
                        when (state) {
                            LoginUiState.Error -> {
                                ctx.toastUtil("Incorrect username or password")
                            }

                            LoginUiState.Loading -> {
                                ctx.toastUtil("Waiting for response")
                            }

                            is LoginUiState.Success -> {
                                ctx.toastUtil("Login Success")
                                delay(2_000L)
                                navigateToHome()
                            }
                        }
                    }
                }
            },
            contentPadding = PaddingValues(start = 64.dp, end = 64.dp)
        ) {
            Text(text = stringResource(id = R.string.login))
        }
        Spacer(modifier = Modifier.padding(top = 32.dp))
        Row {
            Text(
                text = stringResource(id = R.string.no_account),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
            )
            Text(
                text = stringResource(id = R.string.reg_here),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = Color.Cyan,
                modifier = Modifier
                    .clickable { navigateToRegister() }
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(navigateToRegister = {}, navigateToHome = {})
}