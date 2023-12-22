
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.todaypoem.TodayPoemScreen

@Composable
fun PoemPulseApp() {
    MaterialTheme {
        Navigator(TodayPoemScreen())
    }
}