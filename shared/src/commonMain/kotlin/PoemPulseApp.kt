
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.theme.PoemPulseTheme
import presentation.main.MainViewModel
import org.koin.compose.koinInject
import platform.StatusBarColors
import presentation.main.MainScreen


@Composable
fun PoemPulseApp(
    mainViewModel: MainViewModel = koinInject()
) {
    val darkTheme = when(mainViewModel.appTheme.collectAsState().value){
        1->true
        else->false
    }

    PoemPulseTheme(
        useDarkTheme = darkTheme
    ){
        StatusBarColors(
            statusBarColor = MaterialTheme.colorScheme.background,
            navBarColor = MaterialTheme.colorScheme.background,
        )
        Navigator(
            screen = MainScreen(),
            content = { navigator ->
                ProvideAppNavigator(
                    navigator = navigator,
                    content = { SlideTransition(navigator = navigator) },
                )
            },
        )
    }
}
