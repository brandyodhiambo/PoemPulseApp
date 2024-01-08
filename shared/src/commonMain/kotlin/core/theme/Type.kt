package core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import platform.font

@Composable
internal fun getTypography(): Typography {
    val playfairRegular =
        font(
            "PlayFairDisplay",
            "playfair_display_regular",
            FontWeight.Normal,
            FontStyle.Normal,
        )

    val playfairBold =
        font(
            "PlayFairDisplay",
            "playfair_display_bold",
            FontWeight.Bold,
            FontStyle.Normal,
        )

    val playfairBoldItalic =
        font(
            "PlayFairDisplay",
            "playfair_display_bold_italic",
            FontWeight.Bold,
            FontStyle.Italic,
        )

    val playfairSemiBold =
        font(
            "PlayFairDisplay",
            "playfair_display_semi_bold",
            FontWeight.SemiBold,
            FontStyle.Normal,
        )

    val playfairSemiBoldItalic =
        font(
            "PlayFairDisplay",
            "playfair_display_semi_bold_italic",
            FontWeight.SemiBold,
            FontStyle.Italic,
        )

    val playfairExtraBold =
        font(
            "PlayFairDisplay",
            "playfair_display_extrabold",
            FontWeight.ExtraBold,
            FontStyle.Normal,
        )

    val playfairExtraBoldItalic =
        font(
            "PlayFairDisplay",
            "playfair_display_extra_bold_italic",
            FontWeight.ExtraBold,
            FontStyle.Italic,
        )

    val playfairMedium =
        font(
            "PlayFairDisplay",
            "playfair_display_medium",
            FontWeight.Medium,
            FontStyle.Normal,
        )

    val playfairMediumItalic =
        font(
            "PlayFairDisplay",
            "playfair_display_medium_italic",
            FontWeight.Medium,
            FontStyle.Italic,
        )

    val playfairBlack =
        font(
            "PlayFairDisplay",
            "playfair_display_black",
            FontWeight.Black,
            FontStyle.Normal,
        )

    val playfairBlackItalic =
        font(
            "PlayFairDisplay",
            "playfair_display_black_italic",
            FontWeight.Black,
            FontStyle.Italic,
        )

    @Composable
    fun playfairDisplay() = FontFamily(
        playfairRegular,
        playfairBlack,
        playfairBlackItalic,
        playfairBold,
        playfairBoldItalic,
        playfairMedium,
        playfairMediumItalic,
        playfairSemiBold,
        playfairSemiBoldItalic,
        playfairExtraBold,
        playfairExtraBoldItalic
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 50.sp,
            // lineHeight = 64.sp,
            // letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 40.sp,
            // lineHeight = 52.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
            // lineHeight = 44.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 28.sp,
            // lineHeight = 40.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 24.sp,
            // lineHeight = 36.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            // lineHeight = 32.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            // lineHeight = 28.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.1.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = playfairDisplay(),
            fontWeight = FontWeight.W500,
            fontSize = 9.sp,
            // lineHeight = 16.sp,
        ),
    )
}