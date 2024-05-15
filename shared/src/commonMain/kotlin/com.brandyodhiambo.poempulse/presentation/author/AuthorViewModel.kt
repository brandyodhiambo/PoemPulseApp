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
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.brandyodhiambo.poempulse.domain.model.author.AuthorPoem
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorPoemUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorUseCase
import com.brandyodhiambo.poempulse.utils.NetworkResult
import com.brandyodhiambo.poempulse.utils.UiEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthorViewModel(
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getAuthorPoemUseCase: GetAuthorPoemUseCase,
) : ScreenModel {

    private val _eventsFlow = Channel<UiEvents>()
    val eventsFlow get() = _eventsFlow.receiveAsFlow()

    private val _authorUiState = MutableStateFlow(AuthorState())
    val authorUiState get() = _authorUiState.asStateFlow()

    private val viewModelScope = screenModelScope
    val authorImage = listOf(
        "poemauthor1.jpeg",
        "poemauthor2.jpeg",
        "poemauthor3.jpeg",
        "poemauthor4.jpeg",
        "poemauthor5.jpeg",
        "poemauthor6.jpeg",
        "poemauthor7.jpeg",
        "poemauthor8.jpeg",
        "poemauthor9.jpeg",
        "poemauthor11.jpeg",
        "poemauthor12.jpeg",
        "poemauthor13.jpeg",
        "poemauthor14.jpeg",
        "poemauthor15.jpeg",
        "poemauthor16.jpeg",
        "poemauthor17.jpeg",
        "poemauthor18.jpeg",
        "poemauthor19.jpeg",
        "poemauthor20.jpeg",
    )

    init {
        getAuthors()
    }

    fun getAuthors() {
        viewModelScope.launch {
            _authorUiState.update { it.copy(isLoading = true) }
            when (val result = getAuthorUseCase()) {
                is NetworkResult.Error -> {
                    _authorUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.errorMessage,
                            author = result.data ?: emptyList()
                        )
                    }

                    _eventsFlow.trySend(
                        UiEvents.SnackbarEvent(
                            result.errorMessage ?: "Unknown Error Occurred",
                        ),
                    )
                }

                is NetworkResult.Success -> {
                    _authorUiState.update {
                        it.copy(
                            isLoading = false,
                            author = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getAuthorPoem(authorName: String) {
        viewModelScope.launch {
            _authorUiState.update { it.copy(isLoading = true) }
            when (val result = getAuthorPoemUseCase(authorName)) {
                is NetworkResult.Error -> {
                    _authorUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.errorMessage
                        )
                    }

                    _eventsFlow.trySend(
                        UiEvents.SnackbarEvent(
                            result.errorMessage ?: "Unknown Error Occurred",
                        ),
                    )
                }

                is NetworkResult.Success -> {
                    _authorUiState.update {
                        it.copy(
                            isLoading = false,
                            authorPoem = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}

data class AuthorState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val author: List<String> = emptyList(),
    val authorPoem: List<AuthorPoem> = emptyList()
)
