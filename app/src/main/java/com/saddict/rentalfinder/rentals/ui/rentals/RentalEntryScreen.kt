package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.toastUtilLong
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object RentalEntryNavigationDestination : NavigationDestination {
    override val route: String = "rentalentry"
    override val titleRes: Int = R.string.rental_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalEntryScreen(
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.rental_entry),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        RentalEntryBody(
            modifier = modifier.padding(innerPadding),
            navigateToHome = navigateToHome
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalEntryBody(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    entryViewModel: RentalEntryViewModel = hiltViewModel(),
    uiState: RenEntryUiState = entryViewModel.entryUiState,
    entryDetails: EntryDetails = uiState.renEntry,
    onValueChange: (EntryDetails) -> Unit = entryViewModel::updateUiState,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        Column(
//        modifier = modifier.padding(8.dp)
            verticalArrangement = Arrangement.spacedBy((10).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxSize()
        ) {
            var renType by remember { mutableStateOf(entryDetails.type) }
            var location by remember { mutableStateOf(entryDetails.location) }
            var isTypeExpanded by remember { mutableStateOf(false) }
            var isLocationExpanded by remember { mutableStateOf(false) }
            var radioSelected by remember { mutableStateOf(entryDetails.available) }
            val coroutine = rememberCoroutineScope()
            val ctx = LocalContext.current
            OutlinedTextField(
                value = entryDetails.name,
                onValueChange = { onValueChange(entryDetails.copy(name = it)) },
                label = { Text(text = stringResource(id = R.string.rental_name)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_name_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
//                .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.price,
                onValueChange = { onValueChange(entryDetails.copy(price = it)) },
                label = { Text(text = stringResource(id = R.string.rental_price)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_price_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
//                .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.total_units,
                onValueChange = { onValueChange(entryDetails.copy(total_units = it)) },
                label = { Text(text = stringResource(id = R.string.rental_total_units)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_total_units_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
//                .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.image,
                onValueChange = { onValueChange(entryDetails.copy(image = it)) },
                label = { Text(text = stringResource(id = R.string.choose_image)) },
                placeholder = { Text(text = stringResource(id = R.string.choose_image)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
//                .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.description,
                onValueChange = { onValueChange(entryDetails.copy(description = it)) },
                label = { Text(text = stringResource(id = R.string.rental_description)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_description_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
//                .padding(start = 8.dp, end = 4.dp),
                enabled = enabled
            )
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.available).capitalize(Locale.current),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1F))
                Switch(
                    checked = radioSelected,
                    onCheckedChange = { radioSelected = it }
                )
            }
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = isTypeExpanded,
                    onExpandedChange = { isTypeExpanded = it },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    TextField(
                        value = renType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTypeExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isTypeExpanded,
                        onDismissRequest = { isTypeExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Single")
                            },
                            onClick = {
                                renType = "single"
                                isTypeExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "BedSitter")
                            },
                            onClick = {
                                renType = "bedsitter"
                                isTypeExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "1 Bedroom")
                            },
                            onClick = {
                                renType = "1 bedroom"
                                isTypeExpanded = false
                            }
                        )
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = isLocationExpanded,
                    onExpandedChange = { isLocationExpanded = it },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    TextField(
                        value = location,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isLocationExpanded,
                        onDismissRequest = { isLocationExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Mjini")
                            },
                            onClick = {
                                location = "M"
                                isLocationExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Ngomongo")
                            },
                            onClick = {
                                location = "N"
                                isLocationExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Diaspora")
                            },
                            onClick = {
                                location = "D"
                                isLocationExpanded = false
                            }
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    coroutine.launch {
                        entryViewModel.postRental()
                        entryViewModel.uiState.collect { state ->
                            when (state) {
                                CreateRentalUiState.Loading -> {
                                    ctx.toastUtil("please wait! posting rental")
                                }

                                CreateRentalUiState.Error -> {
                                    ctx.toastUtil("sorry could not post rental")
                                }

                                CreateRentalUiState.SuccessError -> {
                                    ctx.toastUtil("an error occured when posting")
                                }

                                is CreateRentalUiState.Success -> {
                                    ctx.toastUtilLong("successfully posted")
                                    delay(1_000L)
                                    navigateToHome()
                                }
                            }
                        }
                    }
                },
                contentPadding = PaddingValues(start = 64.dp, end = 64.dp),
                enabled = entryViewModel.entryUiState.isEntryValid
            ) {
                Text(text = stringResource(id = R.string.post_rental))
            }
        }
    }
}

@Preview
@Composable
fun EntryPreview() {
    RentalEntryScreen(navigateUp = {}, navigateToHome = {})
}