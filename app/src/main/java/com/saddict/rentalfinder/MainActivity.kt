package com.saddict.rentalfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.saddict.rentalfinder.rentals.ui.navigation.RentalsNavGraph
import com.saddict.rentalfinder.ui.theme.RentalfinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentalfinderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
                    RentalsNavGraph(
//                        selectedBottomItem = selectedItem,
//                        onItemSelected = { selectedItem = it }
                    )
                    // Handle back button press
//                    BackHandler(onBack = {
//                        if (selectedItem > 0) {
//                            selectedItem--
//                        } else {
//                            // Exit the app if already on the first item
//                            exitProcess(0)
//                        }
//                    })
                }
            }
        }
    }
}