package com.saddict.rentalfinder.rentals.ui.rentals

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.local.rentals.RentalEntity
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.FavButton
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.toastUtil

object RentalDetailsNavigationDestination : NavigationDestination {
    override val route: String = "rentaldetails"
    override val titleRes: Int = R.string.rental_details
    const val RENTALIDARG = "itemId"
    val routeWithArgs = "$route/{$RENTALIDARG}"
}

@Composable
fun RentalDetailsScreen(
    navigateUp: () -> Unit,
//    navigateToEditRental: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RentalDetailsViewModel = hiltViewModel(),
    uiState: State<RenDetailsUiState> = viewModel.uiState.collectAsState(),
    rental: RentalEntity = uiState.value.rentalDetails,
) {
    val state = rememberScrollState()
    val rentalLocation = if (rental.cityName == null || rental.neighborhoodName == null) {
        "Not Provided"
    } else {
        "${rental.cityName}, ${rental.neighborhoodName}"
    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(state)
                .padding(bottom = 100.dp)
        ) {
            PropertyImage(
                rentalUrl = rental.imageUrl,
                rentalPrice = rental.price.toString(),
                navigateUp = navigateUp
            )
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = "${everyFirstLetterCapitalize(rental.title)} | ${
                        everyFirstLetterCapitalize(
                            rental.category
                        )
                    }",
                    style = MaterialTheme.typography.displaySmall,
                    fontFamily = FontFamily(Font(R.font.montserrat_medium))
                )
                PropertyRow(
                    rentalTotalUnits = "${rental.totalUnits.toString()} (rooms)",
                    contact = rental.authorPhoneNumber ?: "Not Added",
                    rentalPrice = rental.price.toString()
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .alpha(0.2F),
                    thickness = 2.dp
                )
                PropertyInfo(
                    rentalLocation = rentalLocation,
                    rentalType = rental.category,
                    rentalPosted = rental.datePosted,
                    rentalDescription = rental.description
                )
            }
        }
        if (rental.authorPhoneNumber.isNullOrBlank()) {
            PhoneCallButton(
                phoneNumber = "",
                enabled = false,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 5.dp, bottom = 30.dp)
            )
        } else {
            PhoneCallButton(
                phoneNumber = rental.authorPhoneNumber,
                enabled = true,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 5.dp, bottom = 30.dp)
            )
        }
    }
}

@Composable
fun PhoneCallButton(
    phoneNumber: String,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Button(onClick = {
            // on below line we are opening the dialer of our 
            // phone and passing phone number.
            // Use format with "tel:" and phoneNumber created is
            // stored in u.
            val u = Uri.parse("tel:$phoneNumber")

            // Create the intent and set the data for the
            // intent as the phone number.
            val i = Intent(Intent.ACTION_DIAL, u)
            try {
                // Launch the Phone app's dialer with a phone
                // number to dial a call.
                context.startActivity(i)
            } catch (s: SecurityException) {
                // show() method display the toast with
                // exception message.
                context.toastUtil("An error occurred ${s.message}")
            }
        }, enabled = enabled) {
            Text(text = "Book Now")
        }
    }
}


@Composable
fun PropertyImage(
    navigateUp: () -> Unit,
    rentalUrl: String,
    rentalPrice: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = rentalUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            modifier = Modifier
                .height(300.dp)
        )
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {/* TODO favourite button action */ }
                    .offset(x = (-15).dp, y = 5.dp),
                shape = CircleShape,
                color = Color.Black
            ) {
                FavButton(modifier = Modifier)
            }
        }
        Surface(
            modifier = Modifier
                .size(width = 150.dp, height = 30.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-15).dp, y = (-15).dp)
                .alpha(1f),
            shape = RoundedCornerShape(8.dp),
            color = Color.LightGray.copy(alpha = 0.9f)
        ) {
            Box(
                modifier = Modifier
            ) {
                Text(
                    text = "$rentalPrice Kshs",
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PropertyRow(
    rentalTotalUnits: String,
    contact: String,
    rentalPrice: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = rentalTotalUnits
            )
            Text(
                text = stringResource(id = R.string.total).capitalize(Locale.current),
                modifier = Modifier
                    .alpha(0.3F)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .size(width = 1.dp, height = 45.dp),
            thickness = 50.dp,
        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = contact
            )
            Text(
                text = stringResource(id = R.string.contact).capitalize(Locale.current),
                modifier = Modifier
                    .alpha(0.3F)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .size(width = 1.dp, height = 45.dp),
            thickness = 50.dp,
        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = rentalPrice
            )
            Text(
                text = stringResource(id = R.string.ksh).capitalize(Locale.current),
                modifier = Modifier
                    .alpha(0.3F)
            )
        }

    }
}

@Composable
fun PropertyInfo(
    rentalLocation: String,
    rentalType: String,
    rentalPosted: String,
    rentalDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 8.dp)
    ) {
        Text(
            text = everyFirstLetterCapitalize(stringResource(id = R.string.property_info)),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily(Font(R.font.montserrat_medium))
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.location).capitalize(Locale.current),
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = rentalLocation,
                modifier = Modifier.alpha(0.4F)
            )
//            Text(
//                text = rentalLocation,
////                when (rentalLocation) {
////                    "N" -> "Ngomongo"
////                    "D" -> "Diaspora"
////                    "M" -> "Mjini"
////                    else -> "Not Added"
////                },
//                modifier = Modifier
//                    .alpha(0.4F)
//            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .alpha(0.2F),
            thickness = 1.dp
        )
        Row {
            Text(
                text = stringResource(id = R.string.type).capitalize(Locale.current)
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = rentalType,
                modifier = Modifier
                    .alpha(0.4F)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .alpha(0.2F),
            thickness = 1.dp
        )
        Row {
            Text(
                text = stringResource(id = R.string.posted_on).capitalize(Locale.current)
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = rentalPosted,
                modifier = Modifier
                    .alpha(0.4F)
            )
        }
        Text(
            text = stringResource(id = R.string.description).capitalize(Locale.current),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
            modifier = Modifier
                .padding(top = 20.dp, bottom = 8.dp)
        )
        Text(
            text = rentalDescription.capitalize(Locale.current),
            modifier = Modifier
                .padding(top = 10.dp, start = 8.dp)
        )
    }
}