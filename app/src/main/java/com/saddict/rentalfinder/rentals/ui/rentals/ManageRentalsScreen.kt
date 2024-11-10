package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.explore.ExploreCard
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object ManageRentalsDestination : NavigationDestination {
    override val route: String = "my_rentals"
    override val titleRes: Int = R.string.my_rentals
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageRentalsScreen(
    navigateUp: () -> Unit,
    navigateToRentalDetails: (Int) -> Unit,
    navigateToRentalEntry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.my_rentals),
                canNavigateBack = true,
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior
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
        floatingActionButtonPosition = FabPosition.End
    ) { contentPadding ->
        ManageRentalsBody(
            modifier = Modifier
                .padding(contentPadding),
            navigateToRentalDetails = navigateToRentalDetails
        )
    }
}

@Composable
fun ManageRentalsBody(
    navigateToRentalDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    manageRentalsViewModel: ManageRentalsViewModel = hiltViewModel(),
) {
    val rentals = manageRentalsViewModel.getAllRentals.collectAsLazyPagingItems()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Trigger refresh (e.g., on swipe refresh or button press)
        Button(onClick = { rentals.refresh() }) {
            Text("Refresh")
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(
                items = rentals.itemSnapshotList.items,
                key = { it.id }
            ) { items ->
                ExploreCard(
                    imageUrl = items.imageUrl,
                    title = items.title,
                    price = items.price ?: 0f,
                    category = items.category,
                    modifier = Modifier
                        .clickable { navigateToRentalDetails(items.id) }
                )
            }
        }
    }
}