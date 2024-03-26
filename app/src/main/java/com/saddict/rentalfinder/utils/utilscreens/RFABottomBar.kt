package com.saddict.rentalfinder.utils.utilscreens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.saddict.rentalfinder.R

@Composable
fun RFABottomBar(
    navigateToHome: () -> Unit,
    navigateToExplore: () -> Unit,
    navigateToFavourites: () -> Unit,
    navigateToProfile: () -> Unit,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
//    var selectedItem = selectedBottomItem
    LaunchedEffect(key1 = selectedItem) {
        Log.d("MyComposable", "Selected item changed to: $selectedItem")
    }
    val menuTitle = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.explore),
        stringResource(id = R.string.favourites),
        stringResource(id = R.string.profile),
    )
    val menuIcons = listOf(
        Icons.Default.Home,
        Icons.Default.Place,
        Icons.Default.FavoriteBorder,
        Icons.Default.Person,
    )
    //        ToDo
    //         change place to "explore icon"
    NavigationBar(modifier = modifier) {
        menuIcons.forEachIndexed { index, imageVector ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index)
                    when (index) {
                        0 -> {
                            navigateToHome()
                        }

                        1 -> {
                            navigateToExplore()
                        }

                        2 -> {
                            navigateToFavourites()
                        }

                        3 -> {
                            navigateToProfile()
                        }

                        else -> {
                            navigateToHome()
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = menuTitle[index].capitalize(Locale.current)
                    )
                }
            )
        }
    }
}