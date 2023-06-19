package io.maqsad.androidcomponents.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.maqsad.androidcomponents.R


// Set of Material typography styles to start with
val fonts = FontFamily(
    Font(R.font.poppins_ttf),
    Font(R.font.poppins_medium_ttf, weight = FontWeight.Medium),
    Font(R.font.poppins_light_ttf, weight = FontWeight.Light),
    Font(R.font.poppins_bold_ttf, weight = FontWeight.Bold),
    Font(R.font.poppins_semibold_ttf, weight = FontWeight.SemiBold),
)

val Typography = androidx.compose.material.Typography(
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)