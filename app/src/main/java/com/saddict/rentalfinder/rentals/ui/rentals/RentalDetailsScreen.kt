package com.saddict.rentalfinder.rentals.ui.rentals

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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
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
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.FavButton
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize

object RentalDetailsNavigationDestination : NavigationDestination {
    override val route: String = "rentaldetails"
    override val titleRes: Int = R.string.rental_details
    const val RENTALIDARG = "itemId"
    val routeWithArgs = "$route/{$RENTALIDARG}"
}

@Composable
fun RentalDetailsScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RentalDetailsViewModel = hiltViewModel(),
    uiState: State<RenDetailsUiState> = viewModel.uiState.collectAsState(),
    rental: RentalEntity = uiState.value.rentalDetails,
) {
    val state = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(state)
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
                text = "${everyFirstLetterCapitalize(rental.name)} | ${
                    everyFirstLetterCapitalize(
                        rental.type
                    )
                }",
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FontFamily(Font(R.font.montserrat_medium))
            )
            PropertyRow(
                rentalTotalUnits = rental.totalUnits.toString(),
                rentalRating = rental.rating.toString(),
                rentalPrice = rental.price.toString()
            )
            Divider(
                thickness = 2.dp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .alpha(0.2F)
            )
            PropertyInfo(
                rentalLocation = rental.location.toString(),
                rentalType = rental.type,
                rentalPosted = rental.datePosted,
                rentalDescription = rental.description
            )
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
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {/*TODO*/ }
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
    rentalRating: String,
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
                text = "${stringResource(id = R.string.bedroom).capitalize(Locale.current)}s",
                modifier = Modifier
                    .alpha(0.3F)
            )
        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .size(width = 1.dp, height = 35.dp)
        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = rentalRating
            )
            Text(
                text = "House ${stringResource(id = R.string.rating).capitalize(Locale.current)}",
                modifier = Modifier
                    .alpha(0.3F)
            )
        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .size(width = 1.dp, height = 35.dp)
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
                text =
                when (rentalLocation) {
                    "N" -> "Ngomongo"
                    "D" -> "Diaspora"
                    "M" -> "Mjini"
                    else -> "Not Added"
                },
                modifier = Modifier
                    .alpha(0.4F)
            )
        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .alpha(0.2F)
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
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .alpha(0.2F)
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