package com.saddict.rentalfinder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.saddict.rentalfinder.rentals.ui.navigation.RentalsNavGraph
import com.saddict.rentalfinder.ui.theme.RentalfinderTheme
import com.saddict.rentalfinder.utils.isOnline
import com.saddict.rentalfinder.utils.toastUtilLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//       This line tells the system whether to fit the content view to the system windows.
//       When set to false, it allows the app to draw behind the system bars (status bar, navigation bar).
//        This is often used for immersive full-screen experiences where you want your app content to extend into the system bar areas
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        This line installs a splash screen for the application.
//        It typically shows a splash screen immediately when the app is launched, which can be used to display the app's logo or a loading screen.
//        This method sets up the splash screen to appear before the main content of the app is ready to be displayed. -->
        installSplashScreen()
        setContent {
            RentalfinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    if (!isOnline(context)) {
                        context.toastUtilLong("application is offline")
                    }
                    RentalsNavGraph()
                }
            }
        }
    }
}