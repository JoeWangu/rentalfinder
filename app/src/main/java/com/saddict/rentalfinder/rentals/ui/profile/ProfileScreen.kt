package com.saddict.rentalfinder.rentals.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.utils.everyFirstLetterCapitalize
import com.saddict.rentalfinder.utils.utilscreens.RFABottomBar
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object ProfileDestination : NavigationDestination {
    override val route: String = "profile"
    override val titleRes: Int = R.string.profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToHome: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToFavourites: () -> Unit,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navigateToMyAccount: () -> Unit,
    navigateToAddress: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    navigateToContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.account),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            RFABottomBar(
                navigateToHome = { navigateToHome() },
                navigateToExplore = { navigateToExplore() },
                navigateToFavourites = { navigateToFavourites() },
                navigateToProfile = {},
                selectedItem = selectedItem,
                onItemSelected = onItemSelected
            )
        }
    ) { innerPadding ->
        ProfileBody(
            navigateToMyAccount = { navigateToMyAccount() },
            navigateToAddress = { navigateToAddress() },
            navigateToSettings = { navigateToSettings() },
            navigateToHelpCenter = { navigateToHelpCenter() },
            navigateToContact = { navigateToContact() },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun ProfileBody(
    navigateToMyAccount: () -> Unit,
    navigateToAddress: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToHelpCenter: () -> Unit,
    navigateToContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profileMenu = listOf(
        R.string.my_account,
        R.string.address,
        R.string.settings,
        R.string.help_center,
        R.string.contact
    )
    val profileIcons = listOf(
        Icons.Default.AccountCircle,
        Icons.Default.LocationOn,
        Icons.Default.Settings,
        Icons.Default.Info,
        Icons.Default.Phone
    )
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Divider(
            thickness = 2.dp,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Onam Sarker",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                )
                Text(
                    text = "0123-456-800"
                )
            }
        }
        Divider(
            thickness = 2.dp,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        profileMenu.forEachIndexed { index, profileItem ->
            ProfileRow(
                profileIcon = profileIcons[index],
                profileText = stringResource(id = profileItem),
                trailingProfileIcon = Icons.Default.KeyboardArrowRight,
                onClickProfileRow = {
                    when (index) {
                        0 -> {
                            navigateToMyAccount()
                        }

                        1 -> {
                            navigateToAddress()
                        }

                        2 -> {
                            navigateToSettings()
                        }

                        3 -> {
                            navigateToHelpCenter()
                        }

                        4 -> {
                            navigateToContact()
                        }
                    }
                },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ProfileRow(
    profileIcon: ImageVector?,
    profileText: String,
    trailingProfileIcon: ImageVector,
    onClickProfileRow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClickProfileRow() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (profileIcon != null) {
            Icon(
                imageVector = profileIcon,
                contentDescription = null,
                //            tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = everyFirstLetterCapitalize(profileText),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1F))
        Icon(
            imageVector = trailingProfileIcon,
            contentDescription = null
        )
    }
}