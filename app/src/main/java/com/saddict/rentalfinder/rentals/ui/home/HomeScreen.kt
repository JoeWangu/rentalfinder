package com.saddict.rentalfinder.rentals.ui.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.remote.Rental
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.FavButton
import com.saddict.rentalfinder.utils.utilscreens.RFABottomBar
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name_3
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToExplore: () -> Unit,
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
                title = stringResource(id = R.string.app_name_2),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            RFABottomBar(
                navigateToHome = {},
                navigateToExplore = { navigateToExplore() },
                navigateToFavourites = {},
                navigateToAccount = {},
                selectedItem = selectedBottomItem,
                onItemSelected = onItemSelected
            )
        }
    ) { contentPadding ->
        HomeBody(
            modifier = Modifier
                .padding(contentPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    var searchtext by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchItems = remember { mutableStateListOf("first text", "second text") }
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
// --------------------------- end of search start of rest of column --------------------- //
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
                .size(80.dp),
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
//            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeCard(rental: Rental, modifier: Modifier = Modifier) {
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

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RentalfinderTheme {
//        Greeting("Android")
//    }
//}