package com.saddict.rentalfinder.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

fun Context.toastUtil(message: CharSequence){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
fun Context.toastUtilLong(message: CharSequence){ Toast.makeText(this, message, Toast.LENGTH_LONG).show() }