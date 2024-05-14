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
package com.brandyodhiambo.poempulse.presentation.component

import KottieAnimation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import animateKottieCompositionAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import rememberKottieComposition

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AnimationLoader(
    spec: String,
    modifier: Modifier = Modifier
) {
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(resource(spec))
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        speed = 1f,
        iterations = 10
    )

    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier,
    )
}
