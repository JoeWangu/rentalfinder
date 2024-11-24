package com.saddict.rentalfinder.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.saddict.rentalfinder.R
import java.text.SimpleDateFormat
import java.util.Date

//how it should be sent -> val dob: String? = "1996-04-24",
//        <---------- DATE PICKER ------------>
@Composable
fun DatePickerFieldToModal(
    selectedDate: Long? = null,
    onDateSelected: (date: String) -> Unit,
    modifier: Modifier = Modifier
) {
//    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = {},
            shape = MaterialTheme.shapes.extraLarge,
//        label = { Text("DOB") },
            placeholder = { Text("YYYY-MM-DD") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            },
            modifier = modifier
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = { showModal = true }),
        )
    }

    if (showModal) {
        DatePickerModal(
            onDateSelected = { millis ->
                showModal = false
                millis?.let {
                    val formattedDate = convertMillisToDate(it)
                    onDateSelected(formattedDate)
                }
            },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
//    val formatter = SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        shape = MaterialTheme.shapes.extraSmall,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

//        <---------- GENDER PILL VIEWS ------------>
@Composable
fun GenderTabView(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val genderOptions = listOf("Male", "Female", "Other")
    val genderIcons = listOf(
        Icons.Default.Male,
        Icons.Default.Female,
        Icons.Default.Opacity
    )

    Row(
        modifier = modifier
//            .widthIn(max = 600.dp)
            .fillMaxWidth()
//            .padding(horizontal = 8.dp, vertical = 20.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        genderOptions.forEachIndexed { index, gender ->
            GenderPill(
                modifier = Modifier.weight(1f),
                isSelected = selectedGender == gender,
                onClick = { onGenderSelected(gender) },
                icon = genderIcons[index],
                title = gender
            )
        }
    }
}

@Composable
fun GenderPill(
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.onBackground else Color.DarkGray
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Gray
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
//            .padding(horizontal = 4.dp)
            .height(48.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
            modifier = Modifier
                .size(20.dp)
//                .padding(end = 8.dp)
        )
        Text(
            text = title,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

//        <---------- LOADING PLACEHOLDER CARD VIEW ------------>
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

//        <---------- ERROR PLACEHOLDER CARD VIEW ------------>
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

//        <---------- FAVORITE BUTTON VIEW ------------>
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