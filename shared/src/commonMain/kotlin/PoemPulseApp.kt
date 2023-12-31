
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.author.AuthorScreen
import presentation.todaypoem.TodayPoemScreen

@Composable
fun PoemPulseApp() {
    MaterialTheme {
        Navigator(AuthorScreen())
    }
}
