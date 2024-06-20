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

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.brandyodhiambo.poempulse.domain.model.title.TitleLine
import com.brandyodhiambo.poempulse.domain.usecase.GetPoemTitleUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTitleLineUseCase
import com.brandyodhiambo.poempulse.utils.NetworkResult
import com.brandyodhiambo.poempulse.utils.UiEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TitleViewModel(
    private val getPoemTitleUseCase: GetPoemTitleUseCase,
    private val getTitleLineUseCase: GetTitleLineUseCase,
) : ScreenModel {

    private val viewModelScope = screenModelScope
    private val _titleState = MutableStateFlow(TitleState())
    val titleState get() = _titleState.asStateFlow()

    private val _eventsFlow = Channel<UiEvents>()
    val eventsFlow get() = _eventsFlow.receiveAsFlow()

    init {
        getTitle()
    }

    fun getTitle() {
        viewModelScope.launch {
            _titleState.update { it.copy(isLoading = true) }
            when (val result = getPoemTitleUseCase()) {
                is NetworkResult.Error -> {
                    _titleState.update {
                        it.copy(
                            isLoading = false,
                            error = result.errorMessage
                        )
                    }
                    _eventsFlow.trySend(
                        UiEvents.SnackbarEvent(
                            message = result.errorMessage ?: "Unknown error occurred"
                        )
                    )
                }

                is NetworkResult.Success -> {

                    _titleState.update {
                        it.copy(
                            isLoading = false,
                            title = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getTitleLine(title: String) {
        viewModelScope.launch {
            _titleState.update { it.copy(isLoading = true) }

            when (val result = getTitleLineUseCase(title)) {
                is NetworkResult.Error -> {
                    _titleState.update {
                        it.copy(
                            isLoading = false,
                            error = result.errorMessage
                        )
                    }

                    _eventsFlow.trySend(
                        UiEvents.SnackbarEvent(
                            message = result.errorMessage ?: "Unknown error occurred"
                        )
                    )
                }

                is NetworkResult.Success -> {
                    _titleState.update {
                        it.copy(
                            isLoading = false,
                            titleLines = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}

data class TitleState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val title: List<String> = emptyList(),
    val titleLines: List<TitleLine> = emptyList(),

)
