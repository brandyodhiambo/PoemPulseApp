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
package com.brandyodhiambo.poempulse.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

suspend fun <T : Any> safeApiCall(
    cachedData: T? = null,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        NetworkResult.Success(data = apiCall.invoke())
    } catch (e: RedirectResponseException) { // 3xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
            data = cachedData,
        )
    } catch (e: ClientRequestException) { // 4xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
            data = cachedData,
        )
    } catch (e: ServerResponseException) { // 5xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
            data = cachedData,
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            errorCode = 0,
            errorMessage = e.message ?: "An unknown error occurred",
            data = cachedData,
        )
    }
}

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> = staticCompositionLocalOf { null }

@Composable
fun ProvideAppNavigator(navigator: Navigator, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        content()
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun FilledIcon(item: Tab) = when (item.options.index) {
    (0u).toUShort() -> painterResource("home_filled.xml")
    (1u).toUShort() -> painterResource("author_filled.xml")
    (2u).toUShort() -> painterResource("title_filled.xml")
    else -> painterResource("home_filled.xml")
}

fun divideIntoSmallerParagraphs(paragraph: String, linesPerParagraph: Int): List<String> {
    val lines = paragraph.split(",")
    val smallerParagraphs = mutableListOf<String>()
    var currentParagraph = StringBuilder()

    for (line in lines) {
        currentParagraph.append(line.trim())
        currentParagraph.append("\n")

        if (currentParagraph.lines().size >= linesPerParagraph) {
            smallerParagraphs.add(currentParagraph.toString().trim())
            currentParagraph = StringBuilder()
        }
    }

    if (currentParagraph.isNotEmpty()) {
        smallerParagraphs.add(currentParagraph.toString().trim())
    }

    return smallerParagraphs
}

fun divideIntoSmallerParagraph(paragraph: String, linesPerParagraph: Int): String {
    val lines = paragraph.split(",")
    val smallerParagraphs = mutableListOf<String>()
    var currentParagraph = StringBuilder()

    for (line in lines) {
        currentParagraph.append(line.trim())
        currentParagraph.append("\n")

        if (currentParagraph.lines().size >= linesPerParagraph) {
            smallerParagraphs.add(currentParagraph.toString().trim())
            currentParagraph = StringBuilder()
        }
    }

    if (currentParagraph.isNotEmpty()) {
        smallerParagraphs.add(currentParagraph.toString().trim())
    }

    return smallerParagraphs.joinToString("\n\n\n")
}

/*fun String.getEncodedName(): String {
    return this.replace(" ", "%20")
}

fun String.getEncodedWord(): String {
    return this.replace(" ", "+")
}*/

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    LaunchedEffect(flow) {
        withContext(Dispatchers.Main.immediate) {
            flow.collect(onEvent)
        }
    }
}
