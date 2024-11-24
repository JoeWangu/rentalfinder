package com.saddict.rentalfinder.rentals.ui.profile.account

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.location.LocationSelector
import com.saddict.rentalfinder.rentals.ui.location.LocationViewModel
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.rentals.ui.profile.ProfileDetailsUiState
import com.saddict.rentalfinder.rentals.ui.profile.ProfileScreenViewModel
import com.saddict.rentalfinder.rentals.ui.profile.ProfileState
import com.saddict.rentalfinder.rentals.ui.profile.ProfileUiScreenState
import com.saddict.rentalfinder.utils.DatePickerFieldToModal
import com.saddict.rentalfinder.utils.GenderTabView
import com.saddict.rentalfinder.utils.ImageLoaderProvider
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

object AccountDestination : NavigationDestination {
    override val route: String = "account"
    override val titleRes: Int = R.string.account
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navigateUp: () -> Unit,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    onValueChange: (ProfileState) -> Unit = profileScreenViewModel::updateUiState,
    profileDetailsUiState: ProfileDetailsUiState = profileScreenViewModel.editProfileState,
    uiState: ProfileState = profileDetailsUiState.userProfileState,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.account__title),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp,
                textStyle = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        },
    ) { contentPadding ->
        AccountBody(
            profileScreenViewModel = profileScreenViewModel,
            locationViewModel = locationViewModel,
            uiState = uiState,
//            screenState = screenState,
            onValueChange = onValueChange,
            ctx = ctx,
            snackbarHostState = snackbarHostState,
            scope = scope,
            modifier = Modifier
                .padding(contentPadding)
        )
    }
}

//        <---------- ACCOUNT BODY ------------>
@Composable
fun AccountBody(
    profileScreenViewModel: ProfileScreenViewModel,
    locationViewModel: LocationViewModel,
    uiState: ProfileState,
    onValueChange: (ProfileState) -> Unit,
    scope: CoroutineScope,
    ctx: Context,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val state = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(state)
    ) {
        AccountHeader(
            subTitle = stringResource(id = R.string.account__sub_title),
            modifier = Modifier
        )
        AccountInputs(
            profileScreenViewModel = profileScreenViewModel,
            locationViewModel = locationViewModel,
            uiState = uiState,
            onValueChange = onValueChange,
            scope = scope,
            ctx = ctx,
            snackbarHostState = snackbarHostState,
            modifier = Modifier
        )
    }
}

//        <---------- ACCOUNT HEADER ------------>
@Composable
fun AccountHeader(subTitle: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = subTitle.capitalize(locale = Locale.current),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInputs(
    profileScreenViewModel: ProfileScreenViewModel,
    locationViewModel: LocationViewModel,
    onValueChange: (ProfileState) -> Unit,
    uiState: ProfileState,
    scope: CoroutineScope,
    ctx: Context,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val launchProfileActivity = remember { mutableStateOf(false) }
    val screenState by profileScreenViewModel.uiState.collectAsState()

    LaunchedEffect(screenState) {
        when (screenState) {
            ProfileUiScreenState.Idle -> {}

            ProfileUiScreenState.Loading -> {
                launchProfileActivity.value = true
                ctx.toastUtil("please wait! posting rental")
            }

            ProfileUiScreenState.NetworkError -> {
                launchProfileActivity.value = false
                snackbarHostState.showSnackbar(
                    message = "unable to update profile, please check internet connection",
//                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Short
                )
            }

            ProfileUiScreenState.Error -> {
                launchProfileActivity.value = false
                snackbarHostState.showSnackbar(
                    message = "unable to update profile, please ensure your input is correct",
                    duration = SnackbarDuration.Short
                )
            }

            is ProfileUiScreenState.SuccessCreating -> {
                launchProfileActivity.value = false
                ctx.toastUtil("Successfully created your profile")
            }

            is ProfileUiScreenState.SuccessUpdating -> {
                launchProfileActivity.value = false
                ctx.toastUtil("Successfully updated your profile")
            }
        }
    }

    if (launchProfileActivity.value) {
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
                text = "Updating Profile",
                style = MaterialTheme.typography.displaySmall,
            )
        }
    } else {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            ProfileImage(
                ctx = ctx,
                profilePicture = uiState.profilePicture
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
//        <---------- NAMES ROW ------------>
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(.5f, fill = false)
                        .padding(end = 2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.first_name),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    OutlinedTextField(
                        value = uiState.firstName,
                        onValueChange = { onValueChange(uiState.copy(firstName = it)) },
                        shape = MaterialTheme.shapes.extraLarge,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                unfocusedBorderColor = PrimaryColor,
//                textColor = PrimaryColor),
                        modifier = Modifier
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(.5f, fill = false)
                        .padding(start = 2.dp)
                ) {
                    Text(
                        text = stringResource(R.string.last_name),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    OutlinedTextField(
                        value = uiState.lastName,
                        onValueChange = { onValueChange(uiState.copy(lastName = it)) },
                        shape = MaterialTheme.shapes.extraLarge,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                    )
                }
            }
            //        <---------- GENDER ROW ------------>
            Text(
                text = stringResource(R.string.gender)
                    .capitalize(locale = Locale.current),
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
            GenderTabView(
                selectedGender = uiState.gender,
                onGenderSelected = { selectedGender ->
                    onValueChange(uiState.copy(gender = selectedGender))
                }
            )
            //        <---------- DOB ROW ------------>
            Text(
                text = everyFirstLetterCapitalize(stringResource(R.string.date_of_birth)),
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
            DatePickerFieldToModal(
                selectedDate = if (!uiState.dob.isNullOrEmpty()) SimpleDateFormat(
                    "yyyy-MM-dd",
                    java.util.Locale.getDefault()
                ).parse(uiState.dob)?.time else null,
                onDateSelected = { selectedDate ->
                    onValueChange(uiState.copy(dob = selectedDate))
                }
            )
            //        <---------- PHONE NUMBER ROW ------------>
            Text(
                text = stringResource(R.string.phone_number),
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
            OutlinedTextField(
                value = uiState.phoneNumber,
                onValueChange = { onValueChange(uiState.copy(phoneNumber = it)) },
                shape = MaterialTheme.shapes.extraLarge,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            //        <---------- BIO ROW ------------>
            Text(
                text = stringResource(R.string.account__bio),
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
            OutlinedTextField(
                value = uiState.userBio,
                onValueChange = { onValueChange(uiState.copy(userBio = it)) },
                shape = MaterialTheme.shapes.small,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            //        <---------- LOCATION ROW ------------>

            Text(
                text = "Current location: " + listOfNotNull(
                    uiState.userCountry?.takeIf { it.isNotEmpty() } ?: "None",
                    uiState.userState?.takeIf { it.isNotEmpty() },
                    uiState.userCity?.takeIf { it.isNotEmpty() } ?: "None",
                    uiState.userNeighborhood?.takeIf { it.isNotEmpty() } ?: "None"
                ).joinToString(", "),
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )

            LocationSelector(
                selectCountry = { /* country = it.id ?: 1 */ onValueChange(
                    uiState.copy(
                        country = it.id ?: 1
                    )
                )
                },
                selectState = { /* state = it.id ?: 1 */ onValueChange(
                    uiState.copy(
                        state = it.id ?: 1
                    )
                )
                },
                selectCity = { /* city = it.id ?: 1 */ onValueChange(
                    uiState.copy(
                        city = it.id ?: 1
                    )
                )
                },
                selectNeighborhood = { /* neighborhood = it.id ?: 1 */ onValueChange(
                    uiState.copy(
                        neighborhood = it.id ?: 1
                    )
                )
                },
                viewModel = locationViewModel,
                modifier = Modifier
            )
            //        <---------- SUBMIT BUTTON ROW ------------>
            Button(
                onClick = {
                    scope.launch {
                        profileScreenViewModel.createOrupdateUserProfile()
                    }
                },
                enabled = true,
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            ) {
                Text(
                    text = "Update Profile"
                )
            }
        }
    }
}

//        <---------- PROFILE IMAGE VIEW ------------>
@Composable
fun ProfileImage(
    ctx: Context,
    profilePicture: String = "",
    modifier: Modifier = Modifier
) {
    val imageLoader = ImageLoaderProvider.getInstance(ctx)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AsyncImage(
                model = profilePicture,
//                model = "https://picsum.photos/200",
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                placeholder = painterResource(id = R.drawable.profile),
                error = painterResource(id = R.drawable.profile),
                imageLoader = imageLoader,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge),
            )
            Surface(
                modifier = Modifier
                    .size(26.dp)
                    .offset(x = (-5).dp, y = (-5).dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Box(modifier = Modifier) {
                    Surface(
                        modifier = Modifier
                            .size(23.dp)
                            .offset(x = 1.5.dp, y = 1.5.dp),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable { ctx.toastUtil("Coming soon!") }
                                .background(color = MaterialTheme.colorScheme.inversePrimary),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(15.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}
