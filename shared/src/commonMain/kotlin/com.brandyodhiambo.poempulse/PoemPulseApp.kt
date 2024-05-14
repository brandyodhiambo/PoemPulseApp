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
package com.brandyodhiambo.poempulse

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.brandyodhiambo.poempulse.core.theme.PoemPulseTheme
import com.brandyodhiambo.poempulse.platform.StatusBarColors
import com.brandyodhiambo.poempulse.presentation.LandingScreen
import com.brandyodhiambo.poempulse.presentation.main.MainViewModel
import com.brandyodhiambo.poempulse.utils.ProvideAppNavigator
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun PoemPulseApp(
    mainViewModel: MainViewModel = koinInject()
) {
    val darkTheme = when (mainViewModel.appTheme.collectAsState().value) {
        1 -> true
        else -> false
    }

    KoinContext {
        PoemPulseTheme(
            useDarkTheme = darkTheme
        ) {
            StatusBarColors(
                statusBarColor = MaterialTheme.colorScheme.background,
                navBarColor = MaterialTheme.colorScheme.background,
            )
            Navigator(
                screen = LandingScreen(),
                content = { navigator ->
                    ProvideAppNavigator(
                        navigator = navigator,
                        content = { SlideTransition(navigator = navigator) },
                    )
                },
            )
        }
    }
}
