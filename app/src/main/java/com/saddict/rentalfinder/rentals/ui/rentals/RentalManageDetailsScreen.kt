package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.local.RentalEntity
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize

object RentalManageDetailsNavigationDestination : NavigationDestination {
    override val route: String = "rentalmanagedetails"
    override val titleRes: Int = R.string.rental_manage_details
    const val RENTALIDARG = "itemId"
    val routeWithArgs = "$route/{$RENTALIDARG}"
}

@Composable
fun RentalManageDetailsScreen(
    navigateUp: () -> Unit,
    navigateToEditRental: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RentalManageDetailsViewModel = hiltViewModel(),
    uiState: State<RenDetailsUiState> = viewModel.uiState.collectAsState(),
    rental: RentalEntity = uiState.value.rentalDetails,
) {
    val state = rememberScrollState()
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
                    contact = rental.authorPhoneNumber.toString(),
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
                    rentalType = rental.category,
                    rentalPosted = rental.datePosted,
                    rentalDescription = rental.description
                )
            }
        }
        FloatingActionButton(
            onClick = { navigateToEditRental(uiState.value.rentalDetails.id) },
            shape = MaterialTheme.shapes.extraSmall,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit),
            )
        }
    }
}