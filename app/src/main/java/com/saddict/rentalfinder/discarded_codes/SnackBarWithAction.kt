package com.saddict.rentalfinder.discarded_codes

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Image
//import androidx.compose.material3.ExtendedFloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarDuration
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.SnackbarResult
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import kotlinx.coroutines.launch

//@Composable
//fun SnackBarMine() {
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//    Scaffold(
//        snackbarHost = {
//            SnackbarHost(hostState = snackbarHostState)
//        },
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                text = { Text("Show snackbar") },
//                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
//                onClick = {
//                    scope.launch {
//                        val result = snackbarHostState
//                            .showSnackbar(
//                                message = "Snackbar",
//                                actionLabel = "Action",
//                                // Defaults to SnackbarDuration.Short
//                                duration = SnackbarDuration.Indefinite
//                            )
//                        when (result) {
//                            SnackbarResult.ActionPerformed -> {
//                                /* Handle snackbar action performed */
//                            }
//
//                            SnackbarResult.Dismissed -> {
//                                /* Handle snackbar dismissed */
//                            }
//                        }
//                    }
//                }
//            )
//        }
//    ) { contentPadding ->
//        // Screen content
//        Column(modifier = Modifier.padding(contentPadding)) { }
//    }
//}
