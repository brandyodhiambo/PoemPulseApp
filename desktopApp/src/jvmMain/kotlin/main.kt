import androidx.compose.foundation.layout.Box // ktlint-disable filename
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import org.koin.core.Koin

lateinit var koin: Koin
fun main() {
    koin = initKoin().koin
    koin.loadModules(
        listOf(),
    )

    return application {
        Window(
            onCloseRequest = { exitApplication() },
            title = "Poem Pulse",
            state = rememberWindowState(
                position = WindowPosition.Aligned(Alignment.Center),
                width = 1200.dp,
                height = 700.dp,
            ),
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MainView()
            }
        }
    }
}
