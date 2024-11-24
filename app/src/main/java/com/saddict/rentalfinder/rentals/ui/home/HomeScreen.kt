package com.saddict.rentalfinder.rentals.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.explore.ExploreCard
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.LoadingPlaceholderCardItem
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.utilscreens.RFABottomBar
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar


object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name_3
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
//    navigateUp: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToFavourites: () -> Unit,
    navigateToProfile: () -> Unit,
    selectedBottomItem: Int,
    onItemSelected: (Int) -> Unit,
    navigateToRentalDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.app_name_2),
                image = R.drawable.rfa_logo,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            RFABottomBar(
                navigateToHome = {},
                navigateToExplore = { navigateToExplore() },
                navigateToFavourites = { navigateToFavourites() },
                navigateToProfile = { navigateToProfile() },
                selectedItem = selectedBottomItem,
                onItemSelected = onItemSelected
            )
        },
    ) { contentPadding ->
        HomeBody(
            navigateToRentalDetails = navigateToRentalDetails,
            modifier = Modifier
                .padding(contentPadding),
        )
    }
}

@Composable
fun HomeBody(
    navigateToRentalDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
//    LaunchedEffect(key1 = Unit) {
//        homeViewModel.homeItems()
//    }
//    val uiState = homeViewModel.uiState.collectAsState(initial = HomeUiState.Loading).value
    val uiState by homeViewModel.rentalItems.collectAsState()
    val state = rememberScrollState()
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.errorFlow.collect { message ->
            ctx.toastUtil(message)
        }
    }
    val imageList = listOf(
        R.drawable.proxy_image_1,
        R.drawable.proxy_image_2,
        R.drawable.proxy_image_3,
        R.drawable.proxy_image_4
    )
    val categoryList = listOf(
        R.string.bedsitter,
        R.string.single,
        R.string.double_room,
        R.string.bedroom1
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = stringResource(id = R.string.categories).capitalize(Locale.current),
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 12.dp)
//            )
            Row {
                imageList.forEachIndexed { index, image ->
                    CategoryCard(
                        image = image,
                        text = stringResource(id = categoryList[index]),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
// --------------------------- end of categories start of popular  --------------------- //
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.popular_near).capitalize(Locale.current),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1F))

                TextButton(onClick = {
                    /* TODO see all action */
                    ctx.toastUtil("Coming Soon")
                }) {
                    Text(
                        text = everyFirstLetterCapitalize(stringResource(id = R.string.see_all))
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.see_all)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier,
                ) {
                    if (uiState.isEmpty()) {
                        items(count = 4) { LoadingPlaceholderCardItem() }
                    } else {
                        items(
                            count = uiState.take(4).size,
                            key = { index -> uiState[index].id }) { index ->
                            val rentalItem = uiState[index]
                            ExploreCard(
                                imageUrl = rentalItem.imageUrl,
                                title = rentalItem.title,
                                price = rentalItem.price ?: 0f,
                                category = rentalItem.category,
                                modifier = Modifier
                                    .clickable { navigateToRentalDetails(rentalItem.id) }
                            )
                        }
                    }
//                    when (uiState) {
//                        HomeUiState.Loading -> items(count = 4) { LoadingPlaceholderCardItem() }
//                        HomeUiState.Error -> items(count = 4) { ErrorPlaceholderCardItem() }
//                        is HomeUiState.Success -> items(
//                            count = uiState.rentalResults.take(4).size,
//                            key = { index -> uiState.rentalResults[index].id }
//                        ) { index ->
//                            val rentalItem = uiState.rentalResults[index]
//                            ExploreCard(
//                                imageUrl = rentalItem.imageDetail.imageUrl,
//                                title = rentalItem.title,
//                                price = rentalItem.price ?: 0f,
//                                category = rentalItem.category,
//                                modifier = Modifier
//                                    .clickable { navigateToRentalDetails(rentalItem.id) }
//                            )
//                        }
//                    }
                }
            }
// --------------------------- end of popular start of recommended  --------------------- //
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.recommended).capitalize(Locale.current),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.weight(1F))

                TextButton(onClick = {
                    /* TODO see all action */
                    ctx.toastUtil("Coming Soon")
                }) {
                    Text(
                        text = everyFirstLetterCapitalize(stringResource(id = R.string.see_all))
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.see_all)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier,
                ) {
                    if (uiState.isEmpty()) {
                        items(count = 4) { LoadingPlaceholderCardItem() }
                    } else {
                        items(
                            uiState.takeLast(4).reversed()
//                            count = uiState.rentalResults.take(4).reversed().size,
//                            key = { index -> uiState.rentalResults[index].id }
                        ) { rental ->
//                                val rentalItem = uiState.rentalResults[index]
                            ExploreCard(
                                imageUrl = rental.imageUrl,
                                title = rental.title,
                                price = rental.price ?: 0f,
                                category = rental.category,
                                modifier = Modifier
                                    .clickable { navigateToRentalDetails(rental.id) }
                            )
                        }
                    }
//                    when (uiState) {
//                        HomeUiState.Loading -> items(count = 4) { LoadingPlaceholderCardItem() }
//                        HomeUiState.Error -> items(count = 4) { ErrorPlaceholderCardItem() }
//                        is HomeUiState.Success -> {
//                            items(
//                                uiState.rentalResults.takeLast(4).reversed()
////                            count = uiState.rentalResults.take(4).reversed().size,
////                            key = { index -> uiState.rentalResults[index].id }
//                            ) { rental ->
////                                val rentalItem = uiState.rentalResults[index]
//                                ExploreCard(
//                                    imageUrl = rental.imageDetail.imageUrl,
//                                    title = rental.title,
//                                    price = rental.price ?: 0f,
//                                    category = rental.category,
//                                    modifier = Modifier
//                                        .clickable { navigateToRentalDetails(rental.id) }
//                                )
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    image: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(width = 80.dp, height = 60.dp),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )
        }
        Text(
            text = text.capitalize(Locale.current),
            style = MaterialTheme.typography.bodySmall,
//            fontWeight = FontWeight.Bold
        )
    }
}

//@Composable
//fun PopularCard(
//    rental: RentalResults,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .padding(8.dp)
//            .size(180.dp),
//        shape = MaterialTheme.shapes.extraSmall
//    ) {
//        Box(modifier = Modifier) {
//            AsyncImage(
//                model = rental.imageDetail.imageUrl,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                error = painterResource(id = R.drawable.ic_broken_image),
//                placeholder = painterResource(id = R.drawable.loading_img),
//                modifier = Modifier
//                    .height(100.dp)
//            )
//            Surface(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .offset(x = (-5).dp, y = 13.dp)
//                    .size(30.dp)
//                    .clickable {/*TODOs*/ },
//                shape = CircleShape
//            ) {
//                FavButton(modifier = Modifier)
//            }
//        }
//        Column(
//            modifier = Modifier
//                .padding(start = 3.dp),
////            verticalArrangement = Arrangement.spacedBy((-10).dp),
////            horizontalAlignment = Alignment.Start
//        ) {
//            Text(
//                text = everyFirstLetterCapitalize(rental.title),
//                fontSize = 15.sp,
////                style = MaterialTheme.typography.displayMedium,
////                softWrap = false,
////                maxLines = 1,
////                textAlign = TextAlign.Center,
//            )
//            Text(
//                text = "${rental.price}Ksh/ Month",
//                fontSize = 15.sp,
////                style = MaterialTheme.typography.bodyLarge,
////                softWrap = false,
////                maxLines = 1,
////                textAlign = TextAlign.Center,
//            )
//            Text(
//                text = rental.category,
//                fontSize = 15.sp,
////                style = MaterialTheme.typography.bodyLarge,
////                softWrap = false,
////                maxLines = 1,
////                textAlign = TextAlign.Center,
//            )
//        }
//    }
//}