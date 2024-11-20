package com.saddict.rentalfinder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.saddict.rentalfinder.R

// Fonts
val Montserrat = FontFamily(Font(R.font.montserrat_medium))
val Trocchi = FontFamily(Font(R.font.trocchi_regular))
val Macondo = FontFamily(Font(R.font.macondo_swash_caps_regular))

// Set of Material typography styles to start with
// If a field is commented out it means the default value is not
// overridden and that value in it is the default
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Macondo,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
//        lineHeight = 64.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 45.sp,
//        lineHeight = 52.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 36.sp,
//        lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 28.sp,
//        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Trocchi,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Trocchi,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Trocchi,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
    )
)

//Explanation of Typography Use:
/*//
//    Display: Large, attention-grabbing text (titles, headers).
//    Headline: Secondary headers or subheadings.
//    Title: Section titles or prominent text.
//    Body: Regular text (paragraphs, articles).
//    Label: Small descriptive text (form field labels, hints).
//    Button: For buttons and interactive elements.
//    Caption: Small, less prominent text (legal disclaimers).
//    Overline: Small text, often used for metadata or labels.*/

// -------- DEFAULT VALUES --------
/*
// Jetpack Compose Typography Default Values
//
//    Display Large (h1)
//        Font Size: 57sp
//        Font Weight: FontWeight.Normal
//        Line Height: 64sp
//
//    Display Medium (h2)
//        Font Size: 45sp
//        Font Weight: FontWeight.Normal
//        Line Height: 52sp
//
//    Display Small (h3)
//        Font Size: 36sp
//        Font Weight: FontWeight.Normal
//        Line Height: 44sp
//
//    Headline Large (h4)
//        Font Size: 32sp
//        Font Weight: FontWeight.Normal
//        Line Height: 40sp
//
//    Headline Medium (h5)
//        Font Size: 28sp
//        Font Weight: FontWeight.Normal
//        Line Height: 36sp
//
//    Headline Small (h6)
//        Font Size: 24sp
//        Font Weight: FontWeight.Normal
//        Line Height: 32sp
//
//    Body Large
//        Font Size: 16sp
//        Font Weight: FontWeight.Normal
//        Line Height: 24sp
//
//    Body Medium
//        Font Size: 14sp
//        Font Weight: FontWeight.Normal
//        Line Height: 20sp
//
//    Body Small
//        Font Size: 12sp
//        Font Weight: FontWeight.Normal
//        Line Height: 16sp
// Label Large
//
//    Font Size: 16sp
//    Font Weight: FontWeight.Normal
//    Line Height: 24sp
//
// Label Medium
//
//    Font Size: 14sp
//    Font Weight: FontWeight.Normal
//    Line Height: 20sp
//
// Label Small
//
//    Font Size: 12sp
//    Font Weight: FontWeight.Normal
//    Line Height: 16sp
*/
