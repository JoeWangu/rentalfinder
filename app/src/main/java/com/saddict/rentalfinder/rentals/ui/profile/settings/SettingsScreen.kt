package com.saddict.rentalfinder.rentals.ui.profile.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R
import com.saddict.rentalfinder.rentals.ui.navigation.NavigationDestination
import com.saddict.rentalfinder.rentals.ui.profile.ProfileRow
import com.saddict.rentalfinder.utils.utilscreens.RFATopBar

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RFATopBar(
                title = stringResource(id = R.string.settings),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateUp
            )
        },
    ) { contentPadding ->
        SettingsBody(
            modifier = Modifier
                .padding(contentPadding)
        )
    }
}

@Composable
fun SettingsBody(modifier: Modifier = Modifier) {
    val settingsItems1 = listOf("language", "contact us")
    val settingsItems2 = listOf("change pin", "privacy policy")
    var radioSelected by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.general).capitalize(Locale.current),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(16.dp)
                .alpha(0.5F),
        )
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Column {
                settingsItems1.forEachIndexed { index, items ->
                    ProfileRow(
                        profileIcon = null,
                        profileText = items,
                        trailingProfileIcon = Icons.Default.KeyboardArrowRight,
                        onClickProfileRow = {
                            when (index) {
                                0 -> {}
                                1 -> {}
                            }
                        })
                }
            }
        }

        Text(
            text = stringResource(id = R.string.security).capitalize(Locale.current),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(16.dp)
                .alpha(0.5F),
        )
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Column {
                settingsItems2.forEachIndexed { index, items ->
                    ProfileRow(
                        profileIcon = null,
                        profileText = items,
                        trailingProfileIcon = Icons.Default.KeyboardArrowRight,
                        onClickProfileRow = {
                            when (index) {
                                0 -> {}
                                1 -> {}
                            }
                        })
                }
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.biometric).capitalize(Locale.current),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Switch(
                        checked = radioSelected,
                        onCheckedChange = { radioSelected = it }
                    )
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.logout).capitalize(Locale.current),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 15.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .clickable { /*ToDo*/ }
            )
        }
    }
}