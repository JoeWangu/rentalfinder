package com.saddict.rentalfinder.rentals.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.remote.RentalResults
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.ErrorPlaceholderCardItem
import com.saddict.rentalfinder.utils.FavButton
import com.saddict.rentalfinder.utils.LoadingPlaceholderCardItem
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.toastUtilLong
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
    navigateToRentalEntry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.app_name_2),
                canNavigateBack = false,
//                navigateUp = navigateUp,
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToRentalEntry() },
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
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
    rentalItems: LazyPagingItems<RentalResults> = homeViewModel.rentalItemsPagedFlow.collectAsLazyPagingItems()
) {
    val ctx = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = rentalItems.loadState) {
        if (rentalItems.loadState.refresh is LoadState.Error) {
            ctx.toastUtilLong("Error: " + (rentalItems.loadState.refresh as LoadState.Error).error.message)
        }
    }
    val state = rememberScrollState()
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
            Text(
                text = stringResource(id = R.string.categories).capitalize(Locale.current),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )
            Row {
                imageList.forEachIndexed { index, image ->
                    CategoryCard(
                        image = image,
                        text = stringResource(id = categoryList[index]),
                        modifier = Modifier.padding(8.dp)
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

                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = everyFirstLetterCapitalize(stringResource(id = R.string.see_all))
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
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
//                    reverseLayout = true,
                    modifier = Modifier,
                ) {
//                    items(RentalDataSource.rentals.take(4)) { rental ->
//                        PopularCard(rental = rental)
//                    }
                    when (rentalItems.loadState.refresh) {
                        is LoadState.Loading -> items(count = 4) { LoadingPlaceholderCardItem() }
                        is LoadState.Error -> items(count = 4) { ErrorPlaceholderCardItem() }
                        else -> items(
                            count = 4,
//                            count = rentalItems.itemCount,
//                            key = { index ->
//                                rentalItems[index]?.id ?: index.toString()
//                            }
                            key = rentalItems.itemKey { it.id }
                        ) { index ->
                            val rentalItem = rentalItems[index]
                            if (rentalItem != null) {
                                PopularCard(
                                    rental = rentalItem,
                                    modifier = Modifier
                                        .clickable { navigateToRentalDetails(rentalItem.id) }
                                )
                            } else {
                                ErrorPlaceholderCardItem()
                            }
                        }
                    }
                }
            }
// --------------------------- end of popular start of recommended  --------------------- //
            LaunchedEffect(key1 = rentalItems.loadState) {
                if (rentalItems.loadState.refresh is LoadState.NotLoading) {
//                    delay(1_000L)
                    homeViewModel.recommendedItems()
                }
            }
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

                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = everyFirstLetterCapitalize(stringResource(id = R.string.see_all))
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.see_all)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val uiState by homeViewModel.uiState.collectAsState(Unit)
                LazyRow(
                    modifier = Modifier,
                ) {
                    when (uiState) {
                        HomeUiState.Loading -> {
                            items(count = 4) { LoadingPlaceholderCardItem() }
                        }

                        HomeUiState.Error -> {
                            items(count = 4) { ErrorPlaceholderCardItem() }
                        }

                        is HomeUiState.Success -> {
                            items(
                                (uiState as HomeUiState.Success).rentalResults.take(4).reversed()
                            ) { rental ->
                                PopularCard(
                                    rental = rental,
                                    modifier = Modifier
                                        .clickable { navigateToRentalDetails(rental.id) }
                                )
                            }
                        }
                    }
//                    items(RentalDataSource.rentals.take(4).reversed()) { rental ->
//                        PopularCard(rental = rental)
//                    }
//                    items(
//                        count = rentalItems.itemCount,
//                        key = rentalItems.itemKey { it.id.plus(4) }) { index ->
//                        if (index in 4..8) {
//                            val rentalItem = rentalItems[index]
//                            if (rentalItem != null) {
//                                PopularCard(rental = rentalItem)
//                            }
//                        }
//                    }
//                    coroutineScope.launch {
//                        homeViewModel.recommendedItems()
//                        homeViewModel.uiState.collect { state ->
//                            when (state) {
//                                HomeUiState.Loading -> {
//                                    items(count = 4) { LoadingPlaceholderCardItem() }
//                                }
//
//                                HomeUiState.Error -> {
//                                    items(count = 4) { ErrorPlaceholderCardItem() }
//                                }
//
//                                is HomeUiState.Success -> {
//                                    items(state.rentalResults) { rental ->
//                                        PopularCard(rental = rental)
//                                    }
//                                }
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
//                    .height(100.dp)
            )
        }
        Text(
            text = text.capitalize(Locale.current),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PopularCard(
    rental: RentalResults,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .size(180.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Box(modifier = Modifier) {
//            Image(
//                painter = rental.image,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .height(100.dp)
//            )
            AsyncImage(
                model = rental.imageDetail.image,
//                model = ImageRequest.Builder(context = LocalContext.current)
//                    .data(rental.imageDetail.image)
//                    .crossfade(true)
//                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier
                    .height(100.dp)
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-5).dp, y = 13.dp)
                    .size(30.dp)
                    .clickable {/*TODO*/ },
                shape = CircleShape
            ) {
                FavButton(modifier = Modifier)
            }
        }
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = rental.name,
                fontSize = 15.sp,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "${rental.price}Ksh/ Month",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = rental.type,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RentalfinderTheme {
//        Greeting("Android")
//    }
//}