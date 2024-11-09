package com.saddict.rentalfinder.rentals.ui.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.model.remote.NeighborhoodResults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeighborhoodDropdown(
    selectNeighborhood: (neighborhood: NeighborhoodResults) -> Unit,
    viewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    val neighborhoods by viewModel.neighborhoods.collectAsState()
    val selectedNeighborhood = viewModel.selectedNeighborhood.value

    var isDropDownExpanded = remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(text = selectedNeighborhood?.name ?: "Select Neighborhood")
            Image(
                painter = painterResource(id = R.drawable.round_arrow_drop_down_24),
                contentDescription = "DropDown Icon"
            )
        }
        DropdownMenu(
            expanded = isDropDownExpanded.value,
            onDismissRequest = {
                isDropDownExpanded.value = false
            }) {
            neighborhoods.forEach { neighborhoods ->
                DropdownMenuItem(text = {
                    Text(text = neighborhoods.name)
                },
                    onClick = {
                        isDropDownExpanded.value = false
                        viewModel.onNeighborhoodSelected(neighborhoods)
                        selectNeighborhood(neighborhoods)
                    })
            }
        }
    }
}