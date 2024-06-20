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
package com.brandyodhiambo.poempulse.presentation.author

import AuthorState
import AuthorViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brandyodhiambo.poempulse.presentation.component.DataNotFound
import com.brandyodhiambo.poempulse.presentation.component.LoadingAnimation
import com.brandyodhiambo.poempulse.utils.LocalAppNavigator
import com.brandyodhiambo.poempulse.utils.ObserveAsEvents
import com.brandyodhiambo.poempulse.utils.UiEvents
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun AuthorScreen(
    authorViewModel: AuthorViewModel = koinInject()
) {
    val navigator = LocalAppNavigator.currentOrThrow
    val snackbarHostState = remember { SnackbarHostState() }
    val authorState = authorViewModel.authorUiState.collectAsState().value
    val scope = rememberCoroutineScope()

    ObserveAsEvents(authorViewModel.eventsFlow) { event ->
        when (event) {
            is UiEvents.SnackbarEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }

            else -> {}
        }
    }

    AuthorScreenContent(
        authorImage = authorViewModel.authorImage,
        authorState = authorState,
        snackbarHostState = { SnackbarHost(snackbarHostState) },
        onAuthorClicked = {
            navigator.push(AuthorPoemScreen(it))
        },
        onRefreshAuthor = {
            authorViewModel.getAuthors()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AuthorScreenContent(
    authorImage: List<DrawableResource>,
    authorState: AuthorState,
    snackbarHostState: @Composable () -> Unit,
    onAuthorClicked: (String) -> Unit,
    onRefreshAuthor: () -> Unit,
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

        val pullRefreshState =
            rememberPullRefreshState(
                refreshing = false,
                onRefresh = onRefreshAuthor,
            )

        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize().padding(paddingValues)
        ) {

            if (authorState.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center),
                    circleSize = 16.dp,
                )
            }

            if (authorState.author.isEmpty() && authorState.isLoading.not()) {
                DataNotFound("No author found")
            }

            if (authorState.author.isNotEmpty() && authorState.isLoading.not()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(authorState.author) { author ->
                        AuthorCard(
                            image = authorImage.random(),
                            authorName = author,
                            onAuthorClicked = onAuthorClicked
                        )
                    }
                }
            }

            PullRefreshIndicator(
                authorState.isLoading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AuthorCard(
    modifier: Modifier = Modifier,
    image: DrawableResource,
    authorName: String,
    onAuthorClicked: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        onClick = {
            onAuthorClicked(authorName)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val borderWidth = 4.dp
            Image(
                painter = painterResource(image),
                contentDescription = "author_image",
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
