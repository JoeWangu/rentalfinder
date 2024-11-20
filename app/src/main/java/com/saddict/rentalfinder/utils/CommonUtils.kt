package com.saddict.rentalfinder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import com.saddict.rentalfinder.R

@Composable
fun FavButton(modifier: Modifier = Modifier) {
    var isFavourite by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        IconToggleButton(
            checked = isFavourite,
            onCheckedChange = { isFavourite = !isFavourite }
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(id = R.string.add_favourite),
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center),
                tint = Color.Cyan
            )
        }
    }
}

fun Context.toastUtil(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastUtilLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun everyFirstLetterCapitalize(str: String): String {
    val words = str.split(" ").toMutableList()
    var output = ""
    for (word in words) {
        output += word.capitalize(Locale.current) + " "
    }
    output = output.trim()
    return output
}

val Context.tokenDataStore by preferencesDataStore("app_preferences")

@Composable
fun ErrorPlaceholderCardItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(180.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_image),
            contentDescription = "Error loading item",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LoadingPlaceholderCardItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(180.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        // Show shimmer animation while loading
        ShimmerAnimation(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ShimmerAnimation(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f)
        )
        val gradient = Brush.linearGradient(
            colors,
            start = Offset(0f, 0f),
            end = Offset(100f, 0f)
        )
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val translateAnim by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shimmer_animation"
        )
        Box(
            modifier = Modifier
                .background(gradient)
                .graphicsLayer(
                    translationX = translateAnim - 100f,
                    clip = true
                )
        )
    }
}

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val n = cm.activeNetwork
    if (n != null) {
        val nc = cm.getNetworkCapabilities(n)
        //It will check for both wifi and cellular network
        return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        )
    }
    return false
}

/*
@Composable
fun ShowCustomDialog(
    messages: String, // List of  messages
    onDismiss: () -> Unit, // Callback to dismiss the dialog
    title: String
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
//                    text = "Registration Errors",
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display each message in a separate line
//                errorMessages.forEach { message ->
                Text(
                    text = messages,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
//                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("OK")
                }
            }
        }
    }
}*/
