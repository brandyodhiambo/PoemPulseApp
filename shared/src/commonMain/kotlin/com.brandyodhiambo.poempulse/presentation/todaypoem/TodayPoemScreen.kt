package com.brandyodhiambo.poempulse.presentation.todaypoem

import com.brandyodhiambo.poempulse.utils.LocalAppNavigator
import com.brandyodhiambo.poempulse.utils.ObserveAsEvents
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.brandyodhiambo.poempulse.utils.UiEvents

@Composable
fun TodayPoemScreen(
    todayPoemViewModel: TodayPoemViewModel = koinInject()
) {
    val navigator = LocalAppNavigator.currentOrThrow
    val snackbarHostState = remember { SnackbarHostState() }
    val todayPoemState = todayPoemViewModel.todayPoemState.collectAsState().value
    val scope = rememberCoroutineScope()

    ObserveAsEvents(todayPoemViewModel.eventsFlow) { event ->
        when (event) {
            is UiEvents.SnackbarEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }
        }
    }

    TodayPoemContent(
        todayPoemState = todayPoemState,
        snackbarHostState = { SnackbarHost(snackbarHostState) },
        onPoemClicked = {
            navigator.push(
                TodayPoemDetail(it)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayPoemContent(
    todayPoemState: TodayPoemState,
    snackbarHostState: @Composable () -> Unit,
    onPoemClicked: (poem: TodayPoem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Hello Poem Lover \uD83D\uDC4B, ",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "here are today's poem for you",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            )
        },
        snackbarHost = snackbarHostState
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {

            if (todayPoemState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (todayPoemState.poems.isEmpty() && todayPoemState.isLoading.not()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    text = "No poems found",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            if (todayPoemState.poems.isNotEmpty() && todayPoemState.isLoading.not()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(todayPoemState.poems) { poem ->
                        TodayPoemCard(
                            poem = poem,
                            onPoemClicked = onPoemClicked
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayPoemCard(
    modifier: Modifier = Modifier,
    poem: TodayPoem,
    onPoemClicked: (poem: TodayPoem) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(.4f),
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = {
            onPoemClicked(poem)
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = poem.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                )
            )
            Spacer(modifier.height(8.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = poem.lines.joinToString(","),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = poem.author,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                ),
            )
        }
    }
}
