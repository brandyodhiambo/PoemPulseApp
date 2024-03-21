package presentation.author

import AuthorState
import AuthorViewModel
import LocalAppNavigator
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import platform.StatusBarColors
import utils.UiEvents

@Composable
fun AuthorScreen(
    authorViewModel: AuthorViewModel = koinInject()
) {
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    val navigator = LocalAppNavigator.currentOrThrow
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

    AuthorScreenContent(
        authorImage = authorViewModel.authorImage,
        authorState = authorState,
        snackbarHostState = { SnackbarHost(snackbarHostState) },
        onAuthorClicked = {
            navigator.push(AuthorPoemScreen(it))
        }

    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorScreenContent(
    authorImage: List<String>,
    authorState: AuthorState,
    snackbarHostState: @Composable () -> Unit,
    onAuthorClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = "Authors",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            )
        },
        snackbarHost = snackbarHostState
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

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
                items(authorState.author) { author ->
                    authorCard(
                        image = authorImage.random(),
                        authorName = author,
                        onAuthorClicked = onAuthorClicked
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun authorCard(
    image: String,
    authorName: String,
    onAuthorClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onAuthorClicked(authorName)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val borderWidth = 4.dp
            Image(
                painter = painterResource(image),
                contentDescription = image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .border(
                        BorderStroke(borderWidth, MaterialTheme.colorScheme.primary),
                        CircleShape
                    )
                    .padding(borderWidth)
                    .clip(CircleShape),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = authorName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
