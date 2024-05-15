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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem
import com.brandyodhiambo.poempulse.presentation.component.DataNotFound
import com.brandyodhiambo.poempulse.presentation.component.LoadingAnimation
import com.brandyodhiambo.poempulse.presentation.component.PoemCard
import com.brandyodhiambo.poempulse.presentation.component.poemBody
import com.brandyodhiambo.poempulse.utils.divideIntoSmallerParagraph
import org.koin.compose.koinInject

data class TodayPoemDetail(
    val poem: TodayPoem
) : Screen {
    @Composable
    override fun Content() {
        val authorViewModel: AuthorViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        val authorState = authorViewModel.authorUiState.collectAsState().value

        LaunchedEffect(authorViewModel) {
            authorViewModel.getAuthorPoem(authorName = poem.author)
        }

        PoemDetailContent(
            title = poem.title,
            line = poem.lines.joinToString(","),
            author = poem.author,
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
        ) { paddingValue ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValue)) {

                if (authorState.isLoading) {
                    LoadingAnimation(
                        modifier = Modifier.align(Alignment.Center),
                        circleSize = 16.dp,
                    )
                }

                if (authorState.error != null) {
                    DataNotFound(authorState.error)
                }

                if (authorState.authorPoem.isNotEmpty() && authorState.isLoading.not()) {
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
}
