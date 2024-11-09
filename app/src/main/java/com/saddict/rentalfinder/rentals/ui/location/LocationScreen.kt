package com.saddict.rentalfinder.rentals.ui.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saddict.rentalfinder.rentals.model.remote.CityResults
import com.saddict.rentalfinder.rentals.model.remote.CountryResults
import com.saddict.rentalfinder.rentals.model.remote.NeighborhoodResults
import com.saddict.rentalfinder.rentals.model.remote.StateResults

//@Composable
//fun LocationScreen(modifier: Modifier = Modifier) {
//    LocationSelector(modifier = modifier)
//}

@Composable
fun LocationSelector(
    selectCountry: (country: CountryResults) -> Unit,
    selectState: (state: StateResults) -> Unit,
    selectCity: (city: CityResults) -> Unit,
    selectNeighborhood: (neighborhood: NeighborhoodResults) -> Unit,
//    stateIsNotEmpty: Boolean,
    viewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Select the location of your rental",
            style = MaterialTheme.typography.titleLarge
        )
        CountryDropdown(
            selectCountry = selectCountry,
            viewModel = viewModel,
            modifier = Modifier
        )
        if (viewModel.states.collectAsState(initial = emptyList()).value.isNotEmpty()) {
            StateDropdown(
                selectState = selectState,
                viewModel = viewModel,
                modifier = Modifier
            )
        }
        CityDropdown(
            selectCity = selectCity,
            viewModel = viewModel,
            modifier = Modifier
        )
        NeighborhoodDropdown(
            selectNeighborhood = selectNeighborhood,
            viewModel = viewModel,
            modifier = Modifier
        )
    }
}