package com.saddict.rentalfinder.rentals.ui.navigation

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStoreImpl
import com.saddict.rentalfinder.rentals.ui.explore.ExploreDestination
import com.saddict.rentalfinder.rentals.ui.explore.ExploreScreen
import com.saddict.rentalfinder.rentals.ui.favourites.FavouritesDestination
import com.saddict.rentalfinder.rentals.ui.favourites.FavouritesScreen
import com.saddict.rentalfinder.rentals.ui.home.HomeDestination
import com.saddict.rentalfinder.rentals.ui.home.HomeScreen
import com.saddict.rentalfinder.rentals.ui.images.ImageUploaderNavigationDestination
import com.saddict.rentalfinder.rentals.ui.images.ImageUploaderScreen
import com.saddict.rentalfinder.rentals.ui.images.RentalImageNavigationDestination
import com.saddict.rentalfinder.rentals.ui.images.RentalImageScreen
import com.saddict.rentalfinder.rentals.ui.profile.ProfileDestination
import com.saddict.rentalfinder.rentals.ui.profile.ProfileScreen
import com.saddict.rentalfinder.rentals.ui.profile.account.AccountDestination
import com.saddict.rentalfinder.rentals.ui.profile.account.AccountScreen
import com.saddict.rentalfinder.rentals.ui.profile.settings.SettingsDestination
import com.saddict.rentalfinder.rentals.ui.profile.settings.SettingsScreen
import com.saddict.rentalfinder.rentals.ui.registration.login.LoginDestination
import com.saddict.rentalfinder.rentals.ui.registration.login.LoginScreen
import com.saddict.rentalfinder.rentals.ui.registration.register.RegisterDestination
import com.saddict.rentalfinder.rentals.ui.registration.register.RegisterScreen
import com.saddict.rentalfinder.rentals.ui.rentals.RentalDetailsNavigationDestination
import com.saddict.rentalfinder.rentals.ui.rentals.RentalDetailsScreen
import com.saddict.rentalfinder.rentals.ui.rentals.RentalEntryNavigationDestination
import com.saddict.rentalfinder.rentals.ui.rentals.RentalEntryScreen
import com.saddict.rentalfinder.utils.toastUtil

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RentalsNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    var pressedTime: Long = 0
    val ctx = LocalContext.current
    val activity = LocalContext.current as? Activity
    val token = PreferenceDataStoreImpl(ctx).getToken()
    NavHost(
        navController = navController,
        startDestination = if (token.isEmpty()) LoginDestination.route else HomeDestination.route,
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
                navigateToFavourites = { navController.navigate(FavouritesDestination.route) },
                navigateToProfile = { navController.navigate(ProfileDestination.route) },
                selectedBottomItem = selectedItem,
                onItemSelected = { selectedItem = it },
                navigateToRentalDetails = {
                    navController.navigate(
                        "${RentalDetailsNavigationDestination.route}/${it}"
                    )
                },
                navigateToRentalEntry = { navController.navigate(RentalEntryNavigationDestination.route) }
            )
        }
        composable(route = ExploreDestination.route) {
//             Handle back button press
            BackHandler(onBack = {
                if (selectedItem > 0) {
                    selectedItem -= 1
                    navController.navigate(HomeDestination.route)
                } else {
                    // Exit the app if already on the first item
                    activity?.finish()
                }
            })
            ExploreScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToFavourites = { navController.navigate(FavouritesDestination.route) },
                navigateToProfile = { navController.navigate(ProfileDestination.route) },
                selectedBottomItem = selectedItem,
                onItemSelected = { selectedItem = it },
                navigateToRentalDetails = {
                    navController.navigate(
                        "${RentalDetailsNavigationDestination.route}/${it}"
                    )
                },
            )
        }
        composable(route = FavouritesDestination.route) {
            BackHandler(onBack = {
                if (selectedItem > 0) {
                    selectedItem = 0
                    navController.navigate(HomeDestination.route)
                } else {
                    // Exit the app if already on the first item
                    activity?.finish()
                }
            })
            FavouritesScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToExplore = { navController.navigate(ExploreDestination.route) },
                navigateToProfile = { navController.navigate(ProfileDestination.route) },
                selectedBottomItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
        composable(route = ProfileDestination.route) {
            BackHandler(onBack = {
                if (selectedItem > 0) {
                    selectedItem = 0
                    navController.navigate(HomeDestination.route)
                } else {
                    // Exit the app if already on the first item
                    activity?.finish()
                }
            })
            ProfileScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToExplore = { navController.navigate(ExploreDestination.route) },
                navigateToFavourites = { navController.navigate(FavouritesDestination.route) },
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
                navigateToMyAccount = { navController.navigate(AccountDestination.route) },
                navigateToAddress = {},
                navigateToSettings = { navController.navigate(SettingsDestination.route) },
                navigateToHelpCenter = {},
                navigateToContact = {}
            )
        }
        composable(route = AccountDestination.route) {
            AccountScreen(
                navigateUp = { navController.popBackStack() }
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navigateUp = { navController.popBackStack() },
                navigateToLogin = { navController.navigate(LoginDestination.route) },
            )
        }
        composable(route = LoginDestination.route) {
            BackHandler {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    ctx.toastUtil("Press back again to exit")
                }
                pressedTime = System.currentTimeMillis()
            }
            LoginScreen(
                navigateToRegister = { navController.navigate(RegisterDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
            )
        }
        composable(route = RegisterDestination.route) {
            RegisterScreen(
                navigateUp = { navController.popBackStack() },
                navigateToLogin = { navController.navigate(LoginDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
            )
        }
        composable(
            route = RentalDetailsNavigationDestination.routeWithArgs,
            arguments = listOf(navArgument(RentalDetailsNavigationDestination.RENTALIDARG) {
                type = NavType.IntType
            })
        ) {
            RentalDetailsScreen(
                navigateUp = { navController.popBackStack() }
            )
        }
        composable(
            route = RentalEntryNavigationDestination.route,
            arguments = listOf(
                navArgument(name = "imageId") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument(name = "imageName") {
                    type = NavType.StringType
                    defaultValue = "default.jpg"
                }
            )
        ) { backStackEntry ->
            val imageId = backStackEntry.savedStateHandle.getStateFlow(
                "imageId", initialValue = 1
            ).collectAsState().value
            val imageName = backStackEntry.savedStateHandle.getStateFlow(
                "imageName", initialValue = "default.jpg"
            ).collectAsState().value
            RentalEntryScreen(
                navigateUp = { navController.popBackStack() },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToImagePicker = { navController.navigate(RentalImageNavigationDestination.route) },
                navigateToImageUploader = {
                    navController.navigate(
                        ImageUploaderNavigationDestination.route
                    )
                },
                imageId = imageId,
                imageName = imageName
            )
        }
        composable(route = RentalImageNavigationDestination.route) {
            RentalImageScreen(
                navigateUp = { navController.popBackStack() },
                onImageClick = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("imageId", it.id)
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "imageName",
                        it.imageName
                    )
                    navController.popBackStack()
                }
            )
        }
        composable(route = ImageUploaderNavigationDestination.route) {
            ImageUploaderScreen(
                navigateUp = { navController.popBackStack() }
            )
        }
    }
}