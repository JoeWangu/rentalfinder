package com.saddict.rentalfinder.rentals.ui.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.data.remote.RentalDataSource
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.FavButton
import com.saddict.rentalfinder.utils.header
import com.saddict.rentalfinder.utils.utilscreens.CarouselSlider
import com.saddict.rentalfinder.utils.utilscreens.RFABottomBar
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object ExploreDestination: NavigationDestination{
    override val route: String = "explore"
    override val titleRes: Int = R.string.explore
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navigateToHome: () -> Unit,
//    navigateToFavourites: () -> Unit,
//    navigateToAccount: () -> Unit,
    selectedBottomItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.explore),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            RFABottomBar(
                navigateToHome = { navigateToHome() },
                navigateToExplore = {},
                navigateToFavourites = {},
                navigateToAccount = {},
                selectedItem = selectedBottomItem,
                onItemSelected = onItemSelected
            )
        }
    ) { contentPadding ->
        ExploreBody(
            modifier = Modifier
                .padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreBody(modifier: Modifier = Modifier) {
    var searchtext by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchItems = remember { mutableStateListOf("first text", "second text") }
    val imageList = listOf(
        R.drawable.proxy_image_1,
        R.drawable.proxy_image_2,
        R.drawable.proxy_image_3,
        R.drawable.proxy_image_4
    )
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp),
            query = searchtext,
            onQueryChange = { searchtext = it },
            onSearch = {
                searchItems.add(searchtext)
                active = false
                searchtext = ""
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        modifier = Modifier
                            .clickable {
                                if (searchtext.isNotEmpty()) {
                                    searchtext = ""
                                } else {
                                    active = false
                                }
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.menu_icon),
                        modifier = Modifier
                            .clickable { }
                    )
                }
            },
        ) {
            searchItems.forEach {
                Row(modifier = Modifier.padding(14.dp)) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = it)
                }
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            header {
                CarouselSlider(imageList = imageList)
            }
            items(RentalDataSource.rentals) { items ->
                ExploreCard(items, modifier = Modifier)
            }
        }
    }
}

@Composable
fun ExploreCard(rental: Rental, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(id = rental.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-5).dp, y = 13.dp)
                    .size(30.dp)
                    .clickable {},
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
                text = "${rental.type} in ${rental.location}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}