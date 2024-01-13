package com.saddict.rentalfinder.rentals.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saddict.rentalfinder.rentals.ui.explore.ExploreDestination
import com.saddict.rentalfinder.rentals.ui.explore.ExploreScreen
import com.saddict.rentalfinder.rentals.ui.home.HomeDestination
import com.saddict.rentalfinder.rentals.ui.home.HomeScreen
import com.saddict.rentalfinder.utils.toastUtil
import kotlin.system.exitProcess

@Composable
fun RentalsNavGraph(
//    selectedBottomItem: Int,
//    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    var pressedTime: Long = 0
    val ctx = LocalContext.current
    val activity = LocalContext.current as? Activity
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            BackHandler {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    ctx.toastUtil("Press back again to exit")
                }
                pressedTime = System.currentTimeMillis()
            }
            HomeScreen(
                navigateToExplore = { navController.navigate(ExploreDestination.route) },
//                selectedBottomItem = selectedBottomItem,
//                onItemSelected = onItemSelected
                selectedBottomItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
        composable(route = ExploreDestination.route) {
//             Handle back button press
            BackHandler(onBack = {
                if (selectedItem > 0) {
                    selectedItem = 0
                    navController.navigate(HomeDestination.route)
                } else {
                    // Exit the app if already on the first item
                    exitProcess(0)
                }
            })
            ExploreScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
//                selectedBottomItem = selectedBottomItem,
//                onItemSelected = onItemSelected
                selectedBottomItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    }
}