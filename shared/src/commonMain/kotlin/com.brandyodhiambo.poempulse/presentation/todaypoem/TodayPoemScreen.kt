/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brandyodhiambo.poempulse.presentation.todaypoem

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.brandyodhiambo.poempulse.presentation.component.DataNotFound
import com.brandyodhiambo.poempulse.presentation.component.LoadingAnimation
import com.brandyodhiambo.poempulse.utils.LocalAppNavigator
import com.brandyodhiambo.poempulse.utils.ObserveAsEvents
import com.brandyodhiambo.poempulse.utils.UiEvents
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

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
        },
        onRefreshPoems = {
            todayPoemViewModel.getTodayPoem()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TodayPoemContent(
    todayPoemState: TodayPoemState,
    snackbarHostState: @Composable () -> Unit,
    onPoemClicked: (poem: TodayPoem) -> Unit,
    onRefreshPoems: () -> Unit,
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

        val pullRefreshState =
            rememberPullRefreshState(
                refreshing = false,
                onRefresh = onRefreshPoems,
            )
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
                .padding(paddingValue)
        ) {

            if (todayPoemState.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center),
                    circleSize = 16.dp,
                )
            }

            if (todayPoemState.poems.isEmpty() && todayPoemState.isLoading.not()) {
                DataNotFound(title = "No poems found")
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

            PullRefreshIndicator(
                todayPoemState.isLoading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
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
