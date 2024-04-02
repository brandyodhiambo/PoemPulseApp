package presentation.author

import AuthorState
import AuthorViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import divideIntoSmallerParagraph
import getEncodedName
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import platform.StatusBarColors
import presentation.component.PoemCard
import utils.UiEvents

data class AuthorPoemScreen(
    val author: String,
) : Screen {

    @Composable
    override fun Content() {
        val authorViewModel: AuthorViewModel = koinInject()
        StatusBarColors(
            statusBarColor = MaterialTheme.colorScheme.background,
            navBarColor = MaterialTheme.colorScheme.background,
        )

        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = remember { SnackbarHostState() }
        val authorState = authorViewModel.authorUiState.collectAsState().value

        LaunchedEffect(key1 = true, block = {
            authorViewModel.eventsFlow.collectLatest { event ->
                when (event) {
                    is UiEvents.SnackbarEvent -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                        )
                    }

                    else -> {}
                }

            }
        })

        authorViewModel.getAuthorPoem(authorName = author.getEncodedName())

        AuthorPoemScreenContent(
            author = author,
            authorState = authorState,
            onBackPressed = {
                navigator.pop()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorPoemScreenContent(
    author: String,
    authorState: AuthorState,
    onBackPressed: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = author,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "author poem Back Arrow"
                        )
                    }
                }
            )
        },
    ) { paddingValue ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValue)) {

            if (authorState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (authorState.error != null) {
                Text(
                    text = authorState.error,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(authorState.authorPoem) { poems ->
                    val paragraphs = divideIntoSmallerParagraph(poems.lines.joinToString(","), 5)
                    PoemCard(poems.title, paragraphs)
                }
            }
        }
    }

}


