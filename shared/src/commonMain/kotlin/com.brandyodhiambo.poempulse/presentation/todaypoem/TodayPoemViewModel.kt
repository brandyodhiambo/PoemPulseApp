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

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem
import com.brandyodhiambo.poempulse.domain.usecase.GetTodayPoemUseCase
import com.brandyodhiambo.poempulse.utils.NetworkResult
import com.brandyodhiambo.poempulse.utils.UiEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodayPoemViewModel(
    private val getTodayPoemUseCase: GetTodayPoemUseCase,
) : ScreenModel {
    private val viewModelScope = screenModelScope

    private val _eventsFlow = Channel<UiEvents>()
    val eventsFlow get() = _eventsFlow.receiveAsFlow()

    private val _todayPoemState = MutableStateFlow(TodayPoemState())
    val todayPoemState get() = _todayPoemState.asStateFlow()
    fun getTodayPoem() {
        _todayPoemState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val randomPoemCount = 50
            when (val result = getTodayPoemUseCase(randomPoemCount)) {

                is NetworkResult.Error -> {
                    _todayPoemState.update {
                        it.copy(
                            isLoading = false,
                            error = result.errorMessage,
                            poems = result.data ?: emptyList()
                        )
                    }
                    _eventsFlow.trySend(
                        UiEvents.SnackbarEvent(
                            result.errorMessage ?: "Unknown Error Occurred"
                        )
                    )
                }

                is NetworkResult.Success -> {
                    _todayPoemState.update {
                        it.copy(
                            isLoading = false,
                            poems = result.data ?: emptyList()
                        )
                    }
                }

                else -> {}
            }
        }
    }

    init {
        getTodayPoem()
    }
}

data class TodayPoemState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val poems: List<TodayPoem> = emptyList()
)
