package com.saddict.rentalfinder.rentals.ui.registration.register

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object RegisterDestination : NavigationDestination {
    override val route: String = "register"
    override val titleRes: Int = R.string.register
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.register),
                canNavigateBack = false,
            )
        }
    ) {
        RegisterBody(modifier = Modifier.padding(it))
    }
}

@Composable
fun RegisterBody(modifier: Modifier = Modifier) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy((10).dp),
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier,
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
//            label = { Text(text = stringResource(id = R.string.first_name)) },
                    placeholder = { Text(text = stringResource(id = R.string.first_name)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
//            label = { Text(text = stringResource(id = R.string.last_name)) },
                    placeholder = { Text(text = stringResource(id = R.string.last_name)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                        .padding(start = 4.dp, end = 8.dp)
                )
            }
            Row(
                modifier = Modifier,
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
                        .weight(1F)
                        .padding(start = 8.dp, end = 4.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
//            label = { Text(text = stringResource(id = R.string.password)) },
                    placeholder = { Text(text = stringResource(id = R.string.password)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                        .padding(start = 8.dp, end = 4.dp)
                )
            }
            Row(
                modifier = Modifier,
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
//            label = { Text(text = stringResource(id = R.string.username)) },
                    placeholder = { Text(text = stringResource(id = R.string.username)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                        .padding(start = 8.dp, end = 4.dp)
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
//            label = { Text(text = stringResource(id = R.string.phone_number)) },
                    placeholder = { Text(text = stringResource(id = R.string.phone)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                        .padding(start = 8.dp, end = 4.dp)
                )
            }
            Row(
                modifier = Modifier,
            ) {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
//            label = { Text(text = stringResource(id = R.string.address)) },
                    placeholder = { Text(text = stringResource(id = R.string.address)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
                        .padding(start = 8.dp, end = 4.dp)
                )
                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
//            label = { Text(text = stringResource(id = R.string.dob)) },
                    placeholder = { Text(text = stringResource(id = R.string.dob)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
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
            }
        }
//        RadioButton(selected = , onClick = { /*TODO*/ })
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
//            label = { Text(text = stringResource(id = R.string.gender)) },
            placeholder = { Text(text = stringResource(id = R.string.gender)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
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
//                .weight(1F)
//                .padding(start = 8.dp, end = 4.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(start = 64.dp, end = 64.dp)
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    RegisterScreen()
}