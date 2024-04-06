package com.saddict.rentalfinder.rentals.ui.rentals

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.toastUtil
import com.saddict.rentalfinder.utils.toastUtilLong
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object RentalEditNavigationDestination : NavigationDestination {
    override val route: String = "edit"
    override val titleRes: Int = R.string.edit
    const val RENTALIDARG = "itemId"
    val routeWithArgs = "${route}/{$RENTALIDARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalEditScreen(
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToImagePicker: () -> Unit,
    navigateToImageUploader: () -> Unit,
    imageId: Int,
    imageName: String,
    modifier: Modifier = Modifier,
    editViewModel: RentalEditViewModel = hiltViewModel(),
    uiState: RenEntryUiState = editViewModel.editUiState,
    editDetails: EntryDetails = uiState.renEntry,
) {
    val state = rememberScrollState()
    val ctx = LocalContext.current
    val coroutine = rememberCoroutineScope()

    Scaffold(
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.edit),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        RentalEntryBody(
            navigateToImagePicker = navigateToImagePicker,
            navigateToImageUploader = navigateToImageUploader,
            imageId = imageId,
            imageName = imageName,
            entryDetails = editDetails,
            onValueChange = editViewModel::updateUiState,
            saveButtonEnabled = editViewModel.editUiState.isEntryValid,
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(state = state),
            saveButtonOnClick = {
                coroutine.launch {
                    editViewModel.updateRental()
                    editViewModel.uiState.collect { state ->
                        when (state) {
                            EditRentalUiState.Loading -> {
                                ctx.toastUtil("please wait! updating rental")
                            }

                            EditRentalUiState.Error -> {
                                ctx.toastUtil("sorry could not update rental")
                            }

                            EditRentalUiState.SuccessError -> {
                                ctx.toastUtil("an error occured when updating")
                            }

                            is EditRentalUiState.Success -> {
                                ctx.toastUtilLong("successfully updated")
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
