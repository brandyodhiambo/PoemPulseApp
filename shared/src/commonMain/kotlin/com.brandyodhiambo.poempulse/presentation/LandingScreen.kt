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
package com.brandyodhiambo.poempulse.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brandyodhiambo.poempulse.presentation.component.AnimationLoader
import com.brandyodhiambo.poempulse.presentation.main.MainScreen
import org.koin.core.component.KoinComponent

internal class LandingScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LandingScreenContent(
            title = "Poem By People",
            description = "To read a poem is to hear it with our eyes. To hear it is to see it with our ears",
            spec = "poem_anim.json",
            onButtonClick = {
                navigator.replaceAll(MainScreen())
            }
        )
    }

    @Composable
    fun LandingScreenContent(
        title: String,
        spec: String,
        description: String,
        onButtonClick: () -> Unit,
    ) {
        Scaffold(
            bottomBar = {
                LandingPageButton(
                    modifier = Modifier.padding(16.dp),
                    text = "Explore Poems",
                    onClick = onButtonClick
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Poem Pulse",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic
                    ),
                )
                Spacer(modifier = Modifier.height(24.dp))
                AnimationLoader(
                    spec = spec,
                    modifier = Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        }
    }

    @Composable
    fun LandingPageButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}
