package com.brandyodhiambo.poempulse

import com.brandyodhiambo.poempulse.utils.ProvideAppNavigator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.brandyodhiambo.poempulse.core.theme.PoemPulseTheme
import org.koin.compose.KoinContext
import com.brandyodhiambo.poempulse.presentation.main.MainViewModel
import org.koin.compose.koinInject
import com.brandyodhiambo.poempulse.platform.StatusBarColors
import com.brandyodhiambo.poempulse.presentation.LandingScreen


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
