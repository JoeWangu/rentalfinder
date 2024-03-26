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