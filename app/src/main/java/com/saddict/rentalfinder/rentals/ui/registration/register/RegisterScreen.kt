package com.saddict.rentalfinder.rentals.ui.registration.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.toastUtilLong
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.launch
import kotlin.collections.set

object RegisterDestination : NavigationDestination {
    override val route: String = "register"
    override val titleRes: Int = R.string.register
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.register),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) {
        RegisterBody(
            navigateToLogin = navigateToLogin,
            navigateToHome = navigateToHome,
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterBody(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    registerDetails: RegisterDetails = registerViewModel.registerEntryUiState.registerDetails,
    onValueChange: (RegisterDetails) -> Unit = registerViewModel::updateUiState,
    enabled: Boolean = true
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val coroutine = rememberCoroutineScope()
//    var showErrorDialog by remember { mutableStateOf(false) }
    val errorMessages by registerViewModel.errorMessages.collectAsState()
    val launchRegisterActivity = remember { mutableStateOf(false) }
    val uiState by registerViewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            RegisterUiState.Loading -> {
                launchRegisterActivity.value = true
                ctx.toastUtil("please wait! registering user")
            }

            is RegisterUiState.Error -> {
                launchRegisterActivity.value = false
//                                errorMessage = state.errorMessages
//                                showErrorDialog = true
                ctx.toastUtil("sorry could not register user")
            }

            is RegisterUiState.Success -> {
                launchRegisterActivity.value = false
                ctx.toastUtilLong("successfully registered")
//                delay(2_000L)
                navigateToHome()
            }

            RegisterUiState.Idle -> {}
        }
    }

    if (launchRegisterActivity.value) {
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
                text = "Please wait while we register you",
                style = MaterialTheme.typography.displaySmall,
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = registerDetails.username,
                onValueChange = { onValueChange(registerDetails.copy(username = it)) },
                label = { Text(text = stringResource(id = R.string.username)) },
                placeholder = { Text(text = stringResource(id = R.string.username)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
//                .weight(1F)
                    .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            Autofill(
                autofillTypes = listOf(AutofillType.EmailAddress),
                onFill = { onValueChange(registerDetails.copy(email = it)) }
            ) {
                OutlinedTextField(
                    value = registerDetails.email,
                    onValueChange = { onValueChange(registerDetails.copy(email = it)) },
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
//                .weight(1F)
                        .padding(start = 8.dp, end = 4.dp),
                    enabled = enabled
                )
            }
            Autofill(
                autofillTypes = listOf(AutofillType.Password),
                onFill = { onValueChange(registerDetails.copy(password = it)) }
            ) {
                OutlinedTextField(
                    value = registerDetails.password,
                    onValueChange = { onValueChange(registerDetails.copy(password = it)) },
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
                                imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else
                                    Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
//                .weight(1F)
                        .padding(start = 8.dp, end = 4.dp),
                    enabled = enabled
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Display error messages if any
            if (errorMessages.isNotEmpty()) {
                Text(
//                text = errorMessages.joinToString("\n"),
                    text = errorMessages,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = {
                    coroutine.launch {
                        registerViewModel.registerUser()
//                        registerViewModel.uiState.collect { state ->
//                            when (state) {
//                                RegisterUiState.Loading -> {
//                                    launchRegisterActivity.value = true
//                                    ctx.toastUtil("please wait! registering user")
//                                }
//
//                                is RegisterUiState.Error -> {
//                                    launchRegisterActivity.value = false
////                                errorMessage = state.errorMessages
////                                showErrorDialog = true
//                                    ctx.toastUtil("sorry could not register user")
//                                }
//
//                                is RegisterUiState.Success -> {
////                                    launchRegisterActivity.value = false
//                                    ctx.toastUtilLong("successfully registered")
//                                    delay(2_000L)
//                                    navigateToHome()
//                                }
//                            }
//                        }
                    }
                },
                contentPadding = PaddingValues(start = 64.dp, end = 64.dp),
                enabled = registerViewModel.registerEntryUiState.isRegisterValid
            ) {
                Text(text = stringResource(id = R.string.register))
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.have_account),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                )
                Text(
                    text = stringResource(id = R.string.login_here),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Cyan,
                    modifier = Modifier
                        .clickable { navigateToLogin() }
                )
            }
        }
    }
}

//    var gender by remember { mutableStateOf(registerDetails.gender) }
//    var isGenderExpanded by remember { mutableStateOf(false) }

// Firstname and Lastname row
/*
Row(
modifier = Modifier,
) {
    OutlinedTextField(
        value = registerDetails.firstName,
        onValueChange = { onValueChange(registerDetails.copy(firstName = it)) },
        label = { Text(text = stringResource(id = R.string.first_name)) },
        placeholder = { Text(text = stringResource(id = R.string.first_name)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
    OutlinedTextField(
        value = registerDetails.lastName,
        onValueChange = { onValueChange(registerDetails.copy(lastName = it)) },
        label = { Text(text = stringResource(id = R.string.last_name)) },
        placeholder = { Text(text = stringResource(id = R.string.last_name)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 4.dp, end = 8.dp),
        enabled = enabled
    )
}*/

// Username and Phonenumber row
/*
Row(
modifier = Modifier,
) {
    OutlinedTextField(
        value = registerDetails.username,
        onValueChange = { onValueChange(registerDetails.copy(username = it)) },
        label = { Text(text = stringResource(id = R.string.username)) },
        placeholder = { Text(text = stringResource(id = R.string.username)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
    OutlinedTextField(
        value = registerDetails.phoneNumber,
        onValueChange = { onValueChange(registerDetails.copy(phoneNumber = it)) },
        label = { Text(text = stringResource(id = R.string.phone_number)) },
        placeholder = { Text(text = stringResource(id = R.string.phone)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
//                    maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
}*/

// Address and DOB row
/*
Row(
modifier = Modifier,
) {
    OutlinedTextField(
        value = registerDetails.address,
        onValueChange = { onValueChange(registerDetails.copy(address = it)) },
        label = { Text(text = stringResource(id = R.string.address)) },
        placeholder = { Text(text = stringResource(id = R.string.address)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
    OutlinedTextField(
        value = registerDetails.dob.toString(),
        onValueChange = { onValueChange(registerDetails.copy(dob = it)) },
        label = { Text(text = stringResource(id = R.string.dob)) },
        placeholder = { Text(text = stringResource(id = R.string.dob)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp)
    )
}*/

// Email and Password row
/*
Row(
modifier = Modifier,
) {
    OutlinedTextField(
        value = registerDetails.email,
        onValueChange = { onValueChange(registerDetails.copy(email = it)) },
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
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
    OutlinedTextField(
        value = registerDetails.password,
        onValueChange = { onValueChange(registerDetails.copy(password = it)) },
        label = { Text(text = stringResource(id = R.string.password)) },
        placeholder = { Text(text = stringResource(id = R.string.password)) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
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
            .weight(1F)
            .padding(start = 8.dp, end = 4.dp),
        enabled = enabled
    )
}*/

/*
Column(
verticalArrangement = Arrangement.spacedBy((10).dp),
modifier = Modifier
.padding(top = 16.dp)
) {
}*/

// Gender row
/*
Spacer(modifier = Modifier.padding(8.dp))
OutlinedTextField(
value = registerDetails.gender.toString(),
onValueChange = { onValueChange(registerDetails.copy(gender = it)) },
label = { Text(text = stringResource(id = R.string.gender)) },
placeholder = { Text(text = stringResource(id = R.string.gender)) },
keyboardOptions = KeyboardOptions(
capitalization = KeyboardCapitalization.None,
autoCorrectEnabled = false,
keyboardType = KeyboardType.Text,
imeAction = ImeAction.Done
),
singleLine = true,
leadingIcon = {
    Icon(
        imageVector = Icons.Default.Face,
        contentDescription = null
    )
},
shape = MaterialTheme.shapes.large,
modifier = Modifier
)
ExposedDropdownMenuBox(
expanded = isGenderExpanded,
onExpandedChange = { isGenderExpanded = it },
modifier = Modifier
//                .weight(1f)
) {
    TextField(
        value = gender!!,
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderExpanded)
        },
        colors = ExposedDropdownMenuDefaults.textFieldColors(),
        modifier = Modifier
            .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled)
    )
    ExposedDropdownMenu(
        expanded = isGenderExpanded,
        onDismissRequest = { isGenderExpanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = "Male")
            },
            onClick = {
                gender = "Male"
                isGenderExpanded = false
                onValueChange(registerDetails.copy(gender = "Male"))
            }
        )
        DropdownMenuItem(
            text = {
                Text(text = "Female")
            },
            onClick = {
                gender = "Female"
                isGenderExpanded = false
                onValueChange(registerDetails.copy(gender = "Female"))
            }
        )
        DropdownMenuItem(
            text = {
                Text(text = "Others")
            },
            onClick = {
                gender = "Others"
                isGenderExpanded = false
                onValueChange(registerDetails.copy(gender = "Others"))
            }
        )
    }
}*/

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