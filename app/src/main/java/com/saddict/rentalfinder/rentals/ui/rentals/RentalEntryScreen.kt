package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.location.LocationSelector
import com.saddict.rentalfinder.rentals.ui.location.LocationViewModel
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
    navigateToImagePicker: () -> Unit,
    navigateToImageUploader: () -> Unit,
    imageId: Int,
    imageName: String,
    modifier: Modifier = Modifier,
    entryViewModel: RentalEntryViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    uiState: RenEntryUiState = entryViewModel.entryUiState,
    entryDetails: EntryDetails = uiState.renEntry,
    onValueChange: (EntryDetails) -> Unit = entryViewModel::updateUiState,
) {
    val state = rememberScrollState()
    val ctx = LocalContext.current
    val coroutine = rememberCoroutineScope()

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
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(state = state),
            navigateToImagePicker = navigateToImagePicker,
            imageId = imageId,
            imageName = imageName,
            navigateToImageUploader = navigateToImageUploader,
            onValueChange = onValueChange,
            entryDetails = entryDetails,
            saveButtonEnabled = entryViewModel.entryUiState.isEntryValid,
            locationViewModel = locationViewModel,
            saveButtonOnClick = {
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
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalEntryBody(
    imageId: Int,
    imageName: String,
    navigateToImagePicker: () -> Unit,
    navigateToImageUploader: () -> Unit,
    onValueChange: (EntryDetails) -> Unit,
    saveButtonEnabled: Boolean,
    saveButtonOnClick: () -> Unit,
    entryDetails: EntryDetails,
    locationViewModel: LocationViewModel,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    LaunchedEffect(key1 = imageId) {
        onValueChange(entryDetails.copy(image = imageId))
    }
    Column(
        modifier = modifier
            .padding(top = 16.dp, bottom = 50.dp)
    ) {
        Column(
//        modifier = modifier.padding(8.dp)
            verticalArrangement = Arrangement.spacedBy((10).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxSize()
        ) {
            var renCategory by remember { mutableStateOf(entryDetails.category) }
            var isCategoryExpanded by remember { mutableStateOf(false) }
//            var location by remember { mutableStateOf(entryDetails.location) }
//            var isLocationExpanded by remember { mutableStateOf(false) }
            var radioSelected by remember { mutableStateOf(entryDetails.available) }
//            var countrySelected by remember { mutableIntStateOf(entryDetails.country) }
            OutlinedTextField(
                value = entryDetails.title,
                onValueChange = { onValueChange(entryDetails.copy(title = it)) },
                label = { Text(text = stringResource(id = R.string.rental_name)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_name_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.price,
                onValueChange = { onValueChange(entryDetails.copy(price = it)) },
                label = { Text(text = stringResource(id = R.string.rental_price)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_price_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
                enabled = enabled
            )
            OutlinedTextField(
                value = entryDetails.total_units,
                onValueChange = { onValueChange(entryDetails.copy(total_units = it)) },
                label = { Text(text = stringResource(id = R.string.rental_total_units)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_total_units_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
                enabled = enabled
            )
            OutlinedTextField(
                value = imageName,
                onValueChange = { onValueChange(entryDetails.copy(image = imageId)) },
                label = { Text(text = stringResource(id = R.string.choose_image)) },
                placeholder = { Text(text = stringResource(id = R.string.choose_image)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
                enabled = enabled,
                readOnly = true
            )
//            Text(text = imageName)
            Row {
                ElevatedButton(
                    onClick = {
                        navigateToImagePicker()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "pick image")
                }
                ElevatedButton(
                    onClick = {
                        navigateToImageUploader()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "upload image")
                }
            }
            OutlinedTextField(
                value = entryDetails.description,
                onValueChange = { onValueChange(entryDetails.copy(description = it)) },
                label = { Text(text = stringResource(id = R.string.rental_description)) },
                placeholder = { Text(text = stringResource(id = R.string.rental_description_enter)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier,
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
                    checked = entryDetails.available,
                    onCheckedChange = {
                        radioSelected = it
//                        entryDetails.available = it
                        onValueChange(entryDetails.copy(available = it))
                    }
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
//            Row(
//                modifier = Modifier,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
            ExposedDropdownMenuBox(
                expanded = isCategoryExpanded,
                onExpandedChange = { isCategoryExpanded = it },
//                    modifier = Modifier
//                        .weight(1f)
            ) {
                TextField(
                    value = renCategory,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled)
                )
                ExposedDropdownMenu(
                    expanded = isCategoryExpanded,
                    onDismissRequest = { isCategoryExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "bedSitter")
                        },
                        onClick = {
                            renCategory = "bedsitter"
                            isCategoryExpanded = false
//                                entryDetails.category = "bedsitter"
                            onValueChange(entryDetails.copy(category = renCategory))
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "single")
                        },
                        onClick = {
                            renCategory = "single"
                            isCategoryExpanded = false
//                                entryDetails.category = "single"
                            onValueChange(entryDetails.copy(category = renCategory))
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "double")
                        },
                        onClick = {
                            renCategory = "double"
                            isCategoryExpanded = false
//                                entryDetails.category = "double"
                            onValueChange(entryDetails.copy(category = renCategory))
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "1 bedroom")
                        },
                        onClick = {
                            renCategory = "1 bedroom"
                            isCategoryExpanded = false
//                                entryDetails.category = "1 bedroom"
                            onValueChange(entryDetails.copy(category = renCategory))
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "2 bedroom")
                        },
                        onClick = {
                            renCategory = "2 bedroom"
                            isCategoryExpanded = false
//                                entryDetails.category = "2 bedroom"
                            onValueChange(entryDetails.copy(category = renCategory))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            LocationSelector(
                selectCountry = {
                    onValueChange(
                        entryDetails.copy(
                            country = it.id,
                            state = null,
                            city = null,
                            neighborhood = null
                        )
                    )
                },
                selectState = {
                    onValueChange(
                        entryDetails.copy(
                            state = it.id,
                            city = null,
                            neighborhood = null
                        )
                    )
                },
                selectCity = {
                    onValueChange(entryDetails.copy(city = it.id, neighborhood = null))
                },
                selectNeighborhood = {
                    onValueChange(entryDetails.copy(neighborhood = it.id))
                },
                locationViewModel,
                modifier = Modifier
            )
//                ExposedDropdownMenuBox(
//                    expanded = isLocationExpanded,
//                    onExpandedChange = { isLocationExpanded = it },
////                    modifier = Modifier
////                        .weight(1f)
//                ) {
//                    TextField(
//                        value = location,
//                        onValueChange = {},
//                        readOnly = true,
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationExpanded)
//                        },
//                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
//                        modifier = Modifier
//                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled)
//                    )
//                    ExposedDropdownMenu(
//                        expanded = isLocationExpanded,
//                        onDismissRequest = { isLocationExpanded = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = {
//                                Text(text = "Mjini")
//                            },
//                            onClick = {
//                                location = "Mjini"
//                                isLocationExpanded = false
////                                entryDetails.location = "Mjini"
//                                onValueChange(entryDetails.copy(location = location))
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = {
//                                Text(text = "Ngomongo")
//                            },
//                            onClick = {
//                                location = "Ngomongo"
//                                isLocationExpanded = false
////                                entryDetails.location = "Ngomongo"
//                                onValueChange(entryDetails.copy(location = location))
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = {
//                                Text(text = "Diaspora")
//                            },
//                            onClick = {
//                                location = "Diaspora"
//                                isLocationExpanded = false
////                                entryDetails.location = "Diaspora"
//                                onValueChange(entryDetails.copy(location = location))
//                            }
//                        )
//                    }
//                }
//            }
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedButton(
                onClick = saveButtonOnClick,
                contentPadding = PaddingValues(start = 64.dp, end = 64.dp),
                enabled = saveButtonEnabled
            ) {
                Text(text = stringResource(id = R.string.post_rental))
            }
        }
    }
}