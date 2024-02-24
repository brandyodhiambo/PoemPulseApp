package presentation.todaypoem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import divideIntoSmallerParagraphs

data class TodayPoemDetail(
    val title: String,
    val line: String,
    val author: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        PoemDetailContent(
            title = title,
            line = line,
            author = author,
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
                            text = title.trim(),
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
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it),
                contentPadding = PaddingValues(16.dp)
            ) {
                val paragraphs = divideIntoSmallerParagraphs(line, 5)
                items(paragraphs) { paragraph ->
                    poemBody(paragraph)
                }
                item {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "By:${author}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            textAlign = TextAlign.End
                        ),
                    )
                }
            }
        }
    }

    @Composable
    fun poemBody(paragraph: String) {
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = paragraph,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            ),
        )
        Spacer(Modifier.height(8.dp))
    }

}