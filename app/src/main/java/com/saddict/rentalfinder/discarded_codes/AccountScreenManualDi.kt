package com.saddict.rentalfinder.discarded_codes

/*import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.prop.Constants.CREATE_USER_URL
import com.saddict.rentalfinder.prop.Constants.LOGIN_URL
import com.saddict.rentalfinder.prop.Prop.Prop.BASE_URL
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSourceImpl
import com.saddict.rentalfinder.rentals.network.RentalService
import com.saddict.rentalfinder.rentals.ui.location.LocationSelector
import com.saddict.rentalfinder.rentals.ui.location.LocationViewModel
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit*/

//object AccountDestination : NavigationDestination {
//    override val route: String = "account"
//    override val titleRes: Int = R.string.account
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountScreen(
//    navigateUp: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//    Scaffold(
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            RFATopBar(
//                title = stringResource(id = R.string.account__title),
//                canNavigateBack = true,
//                scrollBehavior = scrollBehavior,
//                navigateUp = navigateUp,
//                textStyle = MaterialTheme.typography.displaySmall,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif,
//            )
//        },
//    ) { contentPadding ->
//        AccountBody(
//            modifier = Modifier
//                .padding(contentPadding)
//        )
//    }
//}
//
////        <---------- ACCOUNT BODY ------------>
//val requestInterceptor: Interceptor = Interceptor.invoke { chain ->
//    val request = chain.request()
//    val requestBuild = request.newBuilder()
//        .addHeader("Authorization", "Token 918986c29f72219caa2304f301c1a3ae3d2b34e7")
//        .header("Content-Type", "application/json")
//        .build()
//    chain.proceed(requestBuild)
//}
//
//@Composable
//fun AccountBody(
//    locationViewModel: LocationViewModel =
//        LocationViewModel(
//            RemoteDataSourceImpl(
//                Retrofit
//                    .Builder()
//                    .baseUrl(BASE_URL)
//                    .client(
//                        OkHttpClient()
//                            .newBuilder()
//                            .addInterceptor(requestInterceptor)
//                            .connectTimeout(60, TimeUnit.SECONDS)
//                            .readTimeout(60, TimeUnit.SECONDS)
//                            .writeTimeout(60, TimeUnit.SECONDS)
//                            .retryOnConnectionFailure(true)
//                            .build()
//                    )
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .build()
//                    .create(RentalService::class.java)
//            )
//        ),
//    modifier: Modifier = Modifier
//) {
//    val state = rememberScrollState()
//
//    Column(
//        modifier = modifier.verticalScroll(state)
//    ) {
//        AccountHeader(
//            subTitle = stringResource(id = R.string.account__sub_title),
//        )
//        AccountInputs(
//            locationViewModel = locationViewModel
//        )
//    }
//}
//
////        <---------- ACCOUNT HEADER ------------>
//@Composable
//fun AccountHeader(subTitle: String, modifier: Modifier = Modifier) {
//    Row(
//        horizontalArrangement = Arrangement.Center,
//        modifier = modifier.fillMaxSize()
//    ) {
//        Text(
//            text = subTitle.capitalize(locale = Locale.current),
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Center,
//            color = Color.Gray,
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountInputs(
//    locationViewModel: LocationViewModel,
//    modifier: Modifier = Modifier
//) {
////    val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
//
//    //        <---------- PROFILE STATES ------------>
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var phoneNumber by remember { mutableStateOf("") }
//    var dob by remember { mutableStateOf("") }
//    var gender by remember { mutableStateOf("") }
////    var address by remember { mutableStateOf("") }
////    var profilePicture by remember { mutableStateOf("") }
//    var bio by remember { mutableStateOf("") }
////    var country by remember { mutableIntStateOf(0) }
////    var state by remember { mutableIntStateOf(0) }
////    var city by remember { mutableIntStateOf(0) }
////    var neighborhood by remember { mutableIntStateOf(0) }
//
//    Column(
//        modifier = modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//    ) {
////        <---------- NAMES ROW ------------>
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(.5f, fill = false)
//                    .padding(end = 2.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.first_name),
//                    modifier = Modifier.padding(bottom = 5.dp)
//                )
//                OutlinedTextField(
//                    value = firstName,
//                    onValueChange = { firstName = it },
//                    shape = MaterialTheme.shapes.extraLarge,
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        capitalization = KeyboardCapitalization.Words,
//                        autoCorrectEnabled = false,
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    ),
////            colors = TextFieldDefaults.outlinedTextFieldColors(
////                unfocusedBorderColor = PrimaryColor,
////                textColor = PrimaryColor),
//                    modifier = Modifier
//                )
//            }
//            Column(
//                modifier = Modifier
//                    .weight(.5f, fill = false)
//                    .padding(start = 2.dp)
//            ) {
//                Text(
//                    text = stringResource(R.string.last_name),
//                    modifier = Modifier.padding(bottom = 5.dp)
//                )
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = { lastName = it },
//                    shape = MaterialTheme.shapes.extraLarge,
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        capitalization = KeyboardCapitalization.Words,
//                        autoCorrectEnabled = false,
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Done
//                    ),
//                    modifier = Modifier
//                )
//            }
//        }
//        //        <---------- GENDER ROW ------------>
//        Text(
//            text = stringResource(R.string.gender)
//                .capitalize(locale = Locale.current),
//            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
//        )
//        GenderTabView(
//            selectedGender = gender,
//            onGenderSelected = { selectedGender ->
//                gender = selectedGender
//            }
//        )
//        //        <---------- DOB ROW ------------>
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(R.string.date_of_birth)),
//            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
//        )
//        DatePickerFieldToModal(
//            onDateSelected = { selectedDate ->
//                dob = selectedDate
//            }
//        )
//        //        <---------- PHONE NUMBER ROW ------------>
//        Text(
//            text = stringResource(R.string.phone_number),
//            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
//        )
//        OutlinedTextField(
//            value = phoneNumber,
//            onValueChange = { phoneNumber = it },
//            shape = MaterialTheme.shapes.extraLarge,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions.Default.copy(
//                capitalization = KeyboardCapitalization.None,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Phone,
//                imeAction = ImeAction.Next
//            ),
//            modifier = Modifier.fillMaxWidth()
//        )
//        //        <---------- BIO ROW ------------>
//        Text(
//            text = stringResource(R.string.account__bio),
//            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
//        )
//        OutlinedTextField(
//            value = bio,
//            onValueChange = { bio = it },
//            shape = MaterialTheme.shapes.small,
//            keyboardOptions = KeyboardOptions.Default.copy(
//                capitalization = KeyboardCapitalization.Sentences,
//                autoCorrectEnabled = true,
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Next
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//        )
//        //        <---------- LOCATION ROW ------------>
//        LocationSelector(
//            selectCountry = {},
//            selectState = {},
//            selectCity = {},
//            selectNeighborhood = {},
//            viewModel = locationViewModel,
//            modifier = Modifier
//        )
//        //        <---------- SUBMIT BUTTON ROW ------------>
//        Button(
//            onClick = { /*TODO*/
//                Log.d(
//                    "Update Profile", "" +
//                            "$firstName $lastName $phoneNumber $dob $gender $$$"
//                )
//            },
//            enabled = true,
//            shape = MaterialTheme.shapes.extraLarge,
//            colors = ButtonDefaults.buttonColors(Color.Black),
//            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
//            border = BorderStroke(1.dp, Color.Black),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp),
//        ) {
//            Text(
//                text = "Update Profile"
//            )
//        }
//    }
//}
//
////        <---------- PREVIEW ------------>
//@Preview()
//@Composable
//fun AccountPreview() {
//    AccountScreen(
//        navigateUp = {},
//        modifier = Modifier
//    )
//}
//
////val dob: String? = "1996-04-24",
////        <---------- DATE PICKER ------------>
//@Composable
//fun DatePickerFieldToModal(
//    selectedDate: Long? = null,
//    onDateSelected: (date: String) -> Unit,
//    modifier: Modifier = Modifier
//) {
////    var selectedDate by remember { mutableStateOf<Long?>(null) }
//    var showModal by remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
//        onValueChange = { onDateSelected(it) },
//        shape = MaterialTheme.shapes.extraLarge,
////        label = { Text("DOB") },
//        placeholder = { Text("YYYY/MM/DD") },
//        trailingIcon = {
//            Icon(Icons.Default.DateRange, contentDescription = "Select date")
//        },
//        modifier = modifier
//            .fillMaxWidth()
//            .pointerInput(selectedDate) {
//                awaitEachGesture {
//                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
//                    // in the Initial pass to observe events before the text field consumes them
//                    // in the Main pass.
//                    awaitFirstDown(pass = PointerEventPass.Initial)
//                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
//                    if (upEvent != null) {
//                        showModal = true
//                    }
//                }
//            }
//    )
//
//    if (showModal) {
//        DatePickerModal(
////            onDateSelected = { selectedDate = it },
//            onDateSelected = { it?.let { millis -> convertMillisToDate(millis) } },
//            onDismiss = { showModal = false }
//        )
//    }
//}
//
//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
////    val formatter = SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault())
//    return formatter.format(Date(millis))
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DatePickerModal(
//    onDateSelected: (Long?) -> Unit,
//    onDismiss: () -> Unit
//) {
//    val datePickerState = rememberDatePickerState()
//
//    DatePickerDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            TextButton(onClick = {
//                onDateSelected(datePickerState.selectedDateMillis)
//                onDismiss()
//            }) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    ) {
//        DatePicker(state = datePickerState)
//    }
//}
//
////        <---------- GENDER PILL VIEWS ------------>
//@Composable
//fun GenderTabView(
//    selectedGender: String,
//    onGenderSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val genderOptions = listOf("Male", "Female", "Other")
//    val genderIcons = listOf(Icons.Default.Male, Icons.Default.Female, Icons.Default.Opacity)
//
//    Row(
//        modifier = modifier
////            .widthIn(max = 600.dp)
//            .fillMaxWidth()
////            .padding(horizontal = 8.dp, vertical = 20.dp)
//            .wrapContentHeight(),
//        horizontalArrangement = Arrangement.spacedBy(5.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        genderOptions.forEachIndexed { index, gender ->
//            GenderPill(
//                modifier = Modifier.weight(1f),
//                isSelected = selectedGender == gender,
//                onClick = { onGenderSelected(gender) },
//                icon = genderIcons[index],
//                title = gender
//            )
//        }
//    }
//}
//
//@Composable
//fun GenderPill(
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    icon: ImageVector,
//    title: String,
//    modifier: Modifier = Modifier
//) {
//    OutlinedButton(
//        onClick = onClick,
//        border = BorderStroke(1.dp, if (isSelected) Color.Black else Color.Gray),
//        colors = ButtonDefaults.outlinedButtonColors(
//            containerColor = Color.Transparent,
//            contentColor = if (isSelected) Color.Black else Color.Gray
//        ),
//        shape = MaterialTheme.shapes.extraLarge,
//        modifier = modifier
////            .padding(horizontal = 4.dp)
//            .height(48.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = if (isSelected) Color.Black else Color.Gray,
//            modifier = Modifier
//                .size(20.dp)
////                .padding(end = 8.dp)
//        )
//        Text(
//            text = title,
//            color = if (isSelected) Color.Black else Color.Gray,
//            style = MaterialTheme.typography.bodySmall,
//            maxLines = 1,
//            softWrap = false,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .weight(1f)
//        )
//    }
//}
//
////        <---------- COMMENTED OUT CODE ------------>
///*
//Column(
//modifier = modifier.verticalScroll(state)
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(170.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .size(100.dp),
//            contentAlignment = Alignment.BottomEnd
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.profile),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.Center,
//                modifier = Modifier
//            )
//            Surface(
//                modifier = Modifier
//                    .size(25.dp)
//                    .offset(x = (-5).dp, y = (-5).dp),
//                shape = MaterialTheme.shapes.medium,
//            ) {
//                Box(modifier = Modifier) {
//                    Surface(
//                        modifier = Modifier
//                            .size(24.dp)
//                            .offset(x = 1.5.dp, y = 1.5.dp),
//                        shape = MaterialTheme.shapes.medium,
//                        color = MaterialTheme.colorScheme.background
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .clickable { }
//                                .background(color = MaterialTheme.colorScheme.inversePrimary),
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Edit,
//                                contentDescription = null,
////                    tint = Color.Unspecified,
//                                modifier = Modifier
//                                    .size(15.dp)
//                                    .align(Alignment.Center)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(id = R.string.change_picture)),
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier
//                .padding(top = 16.dp),
//            color = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//    }
//    HorizontalDivider(
//        thickness = 1.dp,
//        color = Color.LightGray
//    )
//// --------------------------- start of details column ------------------- //
//    Column(
//        modifier = Modifier
//            .padding(top = 16.dp)
//    ) {
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(id = R.string.name)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            placeholder = { Text(text = "Full Name") },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.Words,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Next
//            ),
//            singleLine = true,
////                shape = MaterialTheme.shapes.extraSmall,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
//        )
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(id = R.string.email)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            placeholder = { Text(text = stringResource(id = R.string.email)) },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.MailOutline,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.None,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Email,
//                imeAction = ImeAction.Next
//            ),
//            singleLine = true,
////                shape = MaterialTheme.shapes.extraSmall,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
//        )
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(id = R.string.password)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            placeholder = { Text(text = "Password") },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Lock,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.None,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Password,
//                imeAction = ImeAction.Next
//            ),
//            singleLine = true,
//            visualTransformation = if (passwordVisibility) VisualTransformation.None
//            else PasswordVisualTransformation(),
//            trailingIcon = {
//                val visibilityIcon = if (passwordVisibility) Icons.Default.CheckCircle
//                else Icons.Default.Warning
//                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
//                    Icon(
//                        imageVector = visibilityIcon,
//                        contentDescription = null
//                    )
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
////                shape = MaterialTheme.shapes.extraSmall
//        )
//        Text(
//            text = everyFirstLetterCapitalize(stringResource(id = R.string.phone_number)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
//            fontWeight = FontWeight.Bold
//        )
//        OutlinedTextField(
//            value = phoneNumber,
//            onValueChange = { phoneNumber = it },
//            placeholder = { Text(text = "Phone Number") },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Call,
//                    contentDescription = null
//                )
//            },
//            keyboardOptions = KeyboardOptions(
//                capitalization = KeyboardCapitalization.None,
//                autoCorrectEnabled = false,
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Done
//            ),
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
//        )
//        Button(
//            onClick = {
// TODO account page save user details
// },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
//            shape = MaterialTheme.shapes.extraSmall
//        ) {
//            Text(
//                text = everyFirstLetterCapitalize(stringResource(id = R.string.save_changes)),
//                style = MaterialTheme.typography.labelLarge,
//                fontWeight = FontWeight.Bold,
//                fontSize = 15.sp
//            )
//        }
//    }
//}
//*/
