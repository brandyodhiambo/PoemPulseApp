package presentation.author

import AuthorState
import AuthorViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject
import org.koin.core.component.KoinComponent
import platform.StatusBarColors

data class AuthorPoemScreen(val author:String) : Screen,KoinComponent {

    @Composable
    override fun Content() {
        val authorViewModel = rememberKoinInject<AuthorViewModel>()
        StatusBarColors(
            statusBarColor = MaterialTheme.colorScheme.background,
            navBarColor = MaterialTheme.colorScheme.background,
        )

        val navigator = LocalNavigator.currentOrThrow
        val authorState = authorViewModel.state.collectAsState()

        authorViewModel.getAuthorPoem(authorName = author)

        AuthorPoemScreenContent(
            authorState = authorState.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorPoemScreenContent(
    authorState:AuthorState
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ) ,
                title = {
                    Text(
                        text = "Author's Poem",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val result = authorState) {
                is AuthorState.Init -> {}

                is AuthorState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is AuthorState.Error -> {
                    Text(
                        text = result.error,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is AuthorState.AuthorPoemResult -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(result.poem) { poems ->
                            Text(
                                text = poems.lines.joinToString(","),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }

                else -> {}
            }
        }
    }

}
