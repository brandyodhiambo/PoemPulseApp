package presentation.author

import AuthorState
import AuthorViewModel
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import divideIntoSmallerParagraph
import divideIntoSmallerParagraphs
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject
import org.koin.core.component.KoinComponent
import platform.StatusBarColors
import presentation.component.poemBody

data class AuthorPoemScreen(
    val author: String,
) : Screen{

    @Composable
    override fun Content() {
        val authorViewModel:AuthorViewModel = koinInject()
        StatusBarColors(
            statusBarColor = MaterialTheme.colorScheme.background,
            navBarColor = MaterialTheme.colorScheme.background,
        )

        val navigator = LocalNavigator.currentOrThrow
        val authorState  by authorViewModel.state.collectAsState()

        authorViewModel.getAuthorPoem(authorName = author)

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
    author:String,
    authorState:AuthorState,
    onBackPressed: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ) ,
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
    ) {paddingValue->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValue)) {
            when (authorState) {
                is AuthorState.Init -> {}

                is AuthorState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is AuthorState.Error -> {
                    Text(
                        text = authorState.error,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is AuthorState.AuthorPoemResult -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(authorState.poem) { poems ->
                            val paragraphs = divideIntoSmallerParagraph(poems.lines.joinToString(","), 5)
                            authorPoemBody(poems.title,paragraphs)
                        }
                    }
                }

                else -> {}
            }
        }
    }

}

@Composable
fun authorPoemBody(title:String,paragraph: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(8.dp))
            var expanded by remember { mutableStateOf(false) }
            Text(
                modifier = Modifier.fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ).clickable {
                        expanded = !expanded
                    },
                text = paragraph,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                maxLines = if (expanded) paragraph.length else 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
        }
    }

}
