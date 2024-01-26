package com.saddict.rentalfinder.rentals.ui.favourites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.FavButton

object FavouritesDestination : NavigationDestination {
    override val route: String = "favourite"
    override val titleRes: Int = R.string.favourites
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToExplore: () -> Unit,
    selectedBottomItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    Scaffold(
//        modifier = modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            RFATopBar(
//                title = stringResource(id = R.string.favourites),
//                canNavigateBack = false,
//                scrollBehavior = scrollBehavior
//            )
//        },
//        bottomBar = {
//            RFABottomBar(
//                navigateToHome = { navigateToHome() },
//                navigateToExplore = { navigateToExplore() },
//                navigateToFavourites = {},
//                navigateToProfile = { navigateToProfile() },
//                selectedItem = selectedBottomItem,
//                onItemSelected = onItemSelected
//            )
//        }
//    ) { innerPadding ->
////        FavouritesBody(
////            modifier = Modifier
////                .padding(innerPadding)
////        )
//    }
}

//@Composable
//fun FavouritesBody(modifier: Modifier = Modifier) {
//    LazyColumn(modifier = modifier) {
//        items(RentalDataSource.rentals.take(10)) { rental ->
//            FavouritesCard(rental = rental)
//        }
//    }
//}

@Composable
fun FavouritesCard(
    rental: RentalResults,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
//            .size(180.dp)
            .height(150.dp)
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(modifier = Modifier) {
            Box(modifier = Modifier) {
                Image(
                    painter = painterResource(id = rental.id),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(150.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = rental.name,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "${rental.price}Ksh/ Month",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = "${rental.type} in ${rental.location}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
//                            .padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1F))

            Surface(
                modifier = Modifier
                    .offset(x = (-5).dp, y = 5.dp)
                    .size(30.dp)
                    .clickable {},
                shape = CircleShape
            ) {
                FavButton(modifier = Modifier)
            }
        }

    }
}