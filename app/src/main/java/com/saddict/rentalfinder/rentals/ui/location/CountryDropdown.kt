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
import com.saddict.rentalfinder.rentals.model.remote.CountryResults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(
    selectCountry: (country: CountryResults) -> Unit,
    viewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    val countries by viewModel.countries.collectAsState()
    val selectedCountry = viewModel.selectedCountry.value

    var isDropDownExpanded = remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isDropDownExpanded.value = true
            }
        ) {
            Text(text = selectedCountry?.name ?: "Select Country")
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
            countries.forEach { country ->
                DropdownMenuItem(text = {
                    Text(text = country.name!!)
                },
                    onClick = {
                        isDropDownExpanded.value = false
                        viewModel.onCountrySelected(country)
                        selectCountry(country)
                    })
            }
        }
    }
}

/*@Composable
fun DropDownDemo() {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableIntStateOf(0)
    }

    val usernames = listOf("Alexander", "Isabella", "Benjamin", "Sophia", "Christopher")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = usernames[itemPosition.intValue])
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
                usernames.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.intValue = index
                        })
                }
            }
        }

    }
}*/

//@Preview(showBackground = true)
//@Composable
//fun DropDownDemoPreview() {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            DropDownDemo()
//        }
//}
