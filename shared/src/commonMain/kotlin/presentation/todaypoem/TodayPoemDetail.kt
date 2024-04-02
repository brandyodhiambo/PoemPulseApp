package presentation.todaypoem

import AuthorState
import AuthorViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import divideIntoSmallerParagraph
import getEncodedName
import org.koin.compose.koinInject
import presentation.component.PoemCard
import presentation.component.poemBody

data class TodayPoemDetail(
    val title: String,
    val line: String,
    val author: String
) : Screen {
    @Composable
    override fun Content() {
        val authorViewModel: AuthorViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        val authorState = authorViewModel.authorUiState.collectAsState().value



        authorViewModel.getAuthorPoem(authorName = author.getEncodedName())

        PoemDetailContent(
            title = title,
            line = line,
            author = author,
            authorState = authorState,
            onBackPressed = {
                navigator.pop()
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PoemDetailContent(
        title: String,
        line: String,
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
                            text = author.trim(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Start,
                                fontStyle = FontStyle.Italic
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackPressed
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = "PoemDetails Back Arrow"
                            )
                        }
                    }
                )
            },
        ) {paddingValue->
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

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val paragraphs = divideIntoSmallerParagraph(line, 5)
                    item {
                        poemBody(
                            paragraph = paragraphs,
                            author = author,
                            title = title
                        )
                    }
                    item {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "More poems by $author",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                    items(authorState.authorPoem) { poems ->
                        val paragraph = divideIntoSmallerParagraph(poems.lines.joinToString(","), 5)
                        PoemCard(poems.title, paragraph)
                    }
                }
            }

        }
    }

}