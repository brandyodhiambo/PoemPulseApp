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
package com.brandyodhiambo.poempulse.presentation.title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brandyodhiambo.poempulse.presentation.component.DataNotFound
import com.brandyodhiambo.poempulse.presentation.component.LoadingAnimation
import com.brandyodhiambo.poempulse.presentation.component.PoemCard
import com.brandyodhiambo.poempulse.utils.ObserveAsEvents
import com.brandyodhiambo.poempulse.utils.UiEvents
import com.brandyodhiambo.poempulse.utils.divideIntoSmallerParagraph
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

data class TitlePoemScreen(
    val title: String,
) : Screen {

    @Composable
    override fun Content() {
        val titleViewModel: TitleViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        val snackBarHostState = remember { SnackbarHostState() }
        val titleState = titleViewModel.titleState.collectAsState().value
        val scope = rememberCoroutineScope()

        ObserveAsEvents(titleViewModel.eventsFlow) { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }

                else -> {}
            }
        }

        LaunchedEffect(titleViewModel) {
            titleViewModel.getTitleLine(title = title)
        }

        TitlePoemContent(
            title = title,
            titleState = titleState,
            onBackPressed = {
                navigator.pop()
            },
            onRefreshPoems = {
                titleViewModel.getTitleLine(title)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TitlePoemContent(
    title: String,
    titleState: TitleState,
    onBackPressed: () -> Unit,
    onRefreshPoems: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = title,
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
                            contentDescription = "title poem Back Arrow"
                        )
                    }
                }
            )
        },
    ) { paddingValue ->
        val pullRefreshState =
            rememberPullRefreshState(
                refreshing = false,
                onRefresh = onRefreshPoems,
            )
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize().padding(paddingValue)
        ) {

            if (titleState.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center),
                    circleSize = 16.dp,
                )
            }

            if (titleState.titleLines.isEmpty() && titleState.isLoading.not()) {
                DataNotFound("No poem lines")
            }

            if (titleState.titleLines.isNotEmpty() && titleState.isLoading.not()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(titleState.titleLines) { poems ->
                        val paragraphs = divideIntoSmallerParagraph(poems.lines.joinToString(","), 5)
                        PoemCard(poems.author, paragraphs)
                    }
                }
            }

            PullRefreshIndicator(
                titleState.isLoading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
        }
    }
}
