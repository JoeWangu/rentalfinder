package com.saddict.rentalfinder.rentals.ui.registration.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.set

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

@OptIn(ExperimentalComposeUiApi::class)
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
    val launchLoginActivity = remember { mutableStateOf(false) }
    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Loading -> {
                launchLoginActivity.value = true
                ctx.toastUtil("Waiting for response")
            }

            is LoginUiState.Error -> {
                launchLoginActivity.value = false
                ctx.toastUtil("Incorrect username or password")
            }

            is LoginUiState.NetError -> {
                launchLoginActivity.value = false
                ctx.toastUtil("Check your internet connection")
            }

            is LoginUiState.Success -> {
                launchLoginActivity.value = false
                ctx.toastUtil("Login Success")
                delay(2000L)
                navigateToHome()
            }

            LoginUiState.Idle -> {}
        }
    }

    if (launchLoginActivity.value) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier.padding(50.dp))
            Text(
                text = "Please wait while we login you in",
                style = MaterialTheme.typography.displaySmall,
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Autofill(
                autofillTypes = listOf(AutofillType.EmailAddress),
                onFill = { email = it }
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = stringResource(id = R.string.email)) },
                    placeholder = { Text(text = stringResource(id = R.string.email)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrectEnabled = false,
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
            }

//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text(text = stringResource(id = R.string.email)) },
//            placeholder = { Text(text = stringResource(id = R.string.email)) },
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.None,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Email,
//                imeAction = ImeAction.Next
//            ),
//            singleLine = true,
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Email,
//                    contentDescription = null
//                )
//            },
//            shape = MaterialTheme.shapes.large,
//            modifier = Modifier
//        )
            Spacer(modifier = Modifier.padding(8.dp))
            Autofill(
                autofillTypes = listOf(AutofillType.Password),
                onFill = { password = it }
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    placeholder = { Text(text = stringResource(id = R.string.password)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrectEnabled = false,
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
                                imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        loginViewModel.loginUser(email = email, password = password)
                    }
//                    coroutineScope.launch {
//                        loginViewModel.loginUser(email = email, password = password)
//                        loginViewModel.uiState.collect { state ->
//                            when (state) {
//                                is LoginUiState.Error -> {
//                                    launchLoginActivity.value = false
//                                    ctx.toastUtil("Incorrect username or password")
//                                }
//
//                                is LoginUiState.NetError -> {
//                                    launchLoginActivity.value = false
//                                    ctx.toastUtil("Check your internet connection")
//                                }
//
//                                is LoginUiState.Loading -> {
//                                    launchLoginActivity.value = true
//                                    ctx.toastUtil("Waiting for response")
//                                }
//
//                                is LoginUiState.Success -> {
//                                    ctx.toastUtil("Login Success")
//                                    delay(2000L)
//                                    navigateToHome()
////                                    launchLoginActivity.value = false
//                                }
//                            }
//                        }
//                    }
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
}

@ExperimentalComposeUiApi
@Composable
private fun Autofill(
    autofillTypes: List<AutofillType>,
    onFill: ((String) -> Unit),
    content: @Composable BoxScope.() -> Unit
) {
    val autofill = LocalAutofill.current
    val autofillTree = LocalAutofillTree.current
    val autofillNode =
        remember(autofillTypes, onFill) {
            AutofillNode(onFill = onFill, autofillTypes = autofillTypes)
        }

    Box(
        modifier =
        Modifier
            .onFocusChanged {
                if (it.isFocused) {
                    autofill?.requestAutofillForNode(autofillNode)
                } else {
                    autofill?.cancelAutofillForNode(autofillNode)
                }
            }
            .onGloballyPositioned { autofillNode.boundingBox = it.boundsInWindow() },
        content = content
    )

    DisposableEffect(autofillNode) {
        autofillTree.children[autofillNode.id] = autofillNode
        onDispose { autofillTree.children.remove(autofillNode.id) }
    }
}