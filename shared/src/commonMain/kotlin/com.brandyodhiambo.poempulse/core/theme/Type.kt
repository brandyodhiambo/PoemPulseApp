/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brandyodhiambo.poempulse.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.brandyodhiambo.poempulse.platform.font
import org.jetbrains.compose.resources.Font
import poempulseapp.shared.generated.resources.Res
import poempulseapp.shared.generated.resources.quattrocento_bold
import poempulseapp.shared.generated.resources.quattrocento_regular

@Composable
internal fun getTypography(): Typography {
    val quattrocentoRegular =
        Font(
            resource = Res.font.quattrocento_regular,
            FontWeight.Normal,
            FontStyle.Normal,
        )

    val quattrocentoBold =
        Font(
            resource =  Res.font.quattrocento_bold,
            FontWeight.Bold,
            FontStyle.Normal,
        )

    val quattrocentoSemiBold =
        Font(
            resource =  Res.font.quattrocento_bold,
            FontWeight.SemiBold,
            FontStyle.Normal,
        )

    val quattrocentoExtraBold =
        Font(
            resource =  Res.font.quattrocento_bold,
            FontWeight.ExtraBold,
            FontStyle.Normal,
        )

    val quattrocentoMedium =
        Font(
            resource =  Res.font.quattrocento_regular,
            FontWeight.Medium,
            FontStyle.Normal,
        )

    val quattrocentoBlack =
        Font(
            resource =  Res.font.quattrocento_bold,
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
            // fontWeight = FontWeight.W400,
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
