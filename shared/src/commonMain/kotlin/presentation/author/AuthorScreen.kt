package presentation.author

import AuthorViewModel
import LocalAppNavigator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import navigation.Screens
import org.koin.compose.koinInject
import platform.StatusBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorScreen(
    authorViewModel: AuthorViewModel = koinInject()
){
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    val navigator = LocalAppNavigator.currentOrThrow
    val authorState = authorViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ) ,
                title = {
                    Text(
                        text = "Authors",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val result = authorState.value) {
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

                is AuthorState.Result -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(result.authors) { author ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .clickable {
                                       navigator.push(AuthorPoemScreen(author))
                                    },
                                text = author,
                                style = MaterialTheme.typography.bodyMedium,
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
