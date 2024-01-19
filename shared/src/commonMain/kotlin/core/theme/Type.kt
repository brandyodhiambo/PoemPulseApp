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
    val quattrocentoRegular =
        font(
            "quattrocento",
            "quattrocento_regular",
            FontWeight.Normal,
            FontStyle.Normal,
        )

    val quattrocentoBold =
        font(
            "quattrocento",
            "quattrocento_bold",
            FontWeight.Bold,
            FontStyle.Normal,
        )

    val quattrocentoSemiBold =
        font(
            "quattrocento",
            "quattrocento_bold",
            FontWeight.SemiBold,
            FontStyle.Normal,
        )

    val quattrocentoExtraBold =
        font(
            "quattrocento",
            "quattrocento_bold",
            FontWeight.ExtraBold,
            FontStyle.Normal,
        )

    val quattrocentoMedium =
        font(
            "quattrocento",
            "quattrocento_regular",
            FontWeight.Medium,
            FontStyle.Normal,
        )

    val quattrocentoBlack =
        font(
            "quattrocento",
            "quattrocento_bold",
            FontWeight.Black,
            FontStyle.Normal,
        )


    @Composable
    fun quattrocentoDisplay() = FontFamily(
        quattrocentoRegular,
        quattrocentoBlack,
        quattrocentoBold,
        quattrocentoMedium,
        quattrocentoSemiBold,
        quattrocentoExtraBold,
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 50.sp,
            // lineHeight = 64.sp,
            // letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 40.sp,
            // lineHeight = 52.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
            // lineHeight = 44.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 28.sp,
            // lineHeight = 40.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 24.sp,
            // lineHeight = 36.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = quattrocentoDisplay(),
            //fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            // lineHeight = 32.sp,
        ),
        titleLarge = TextStyle(
           fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            // lineHeight = 28.sp,
        ),
        titleMedium = TextStyle(
          fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.1.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            // lineHeight = 24.sp,
            // letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
           fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            // lineHeight = 20.sp,
            // letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
            // lineHeight = 16.sp,
            // letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = quattrocentoDisplay(),
            fontWeight = FontWeight.W500,
            fontSize = 9.sp,
            // lineHeight = 16.sp,
        ),
    )
}