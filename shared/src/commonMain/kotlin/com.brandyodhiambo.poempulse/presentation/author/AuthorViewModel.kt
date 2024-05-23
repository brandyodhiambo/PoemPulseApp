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
import org.jetbrains.compose.resources.DrawableResource
import poempulseapp.shared.generated.resources.Res
import poempulseapp.shared.generated.resources.poemauthor1
import poempulseapp.shared.generated.resources.poemauthor11
import poempulseapp.shared.generated.resources.poemauthor12
import poempulseapp.shared.generated.resources.poemauthor13
import poempulseapp.shared.generated.resources.poemauthor14
import poempulseapp.shared.generated.resources.poemauthor15
import poempulseapp.shared.generated.resources.poemauthor16
import poempulseapp.shared.generated.resources.poemauthor17
import poempulseapp.shared.generated.resources.poemauthor18
import poempulseapp.shared.generated.resources.poemauthor19
import poempulseapp.shared.generated.resources.poemauthor2
import poempulseapp.shared.generated.resources.poemauthor20
import poempulseapp.shared.generated.resources.poemauthor3
import poempulseapp.shared.generated.resources.poemauthor4
import poempulseapp.shared.generated.resources.poemauthor5
import poempulseapp.shared.generated.resources.poemauthor6
import poempulseapp.shared.generated.resources.poemauthor7
import poempulseapp.shared.generated.resources.poemauthor8
import poempulseapp.shared.generated.resources.poemauthor9

class AuthorViewModel(
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getAuthorPoemUseCase: GetAuthorPoemUseCase,
) : ScreenModel {

    private val _eventsFlow = Channel<UiEvents>()
    val eventsFlow get() = _eventsFlow.receiveAsFlow()

    private val _authorUiState = MutableStateFlow(AuthorState())
    val authorUiState get() = _authorUiState.asStateFlow()

    private val viewModelScope = screenModelScope
    val authorImage = listOf<DrawableResource>(
        Res.drawable.poemauthor1,
        Res.drawable.poemauthor2,
        Res.drawable.poemauthor3,
        Res.drawable.poemauthor4,
        Res.drawable.poemauthor5,
        Res.drawable.poemauthor6,
        Res.drawable.poemauthor7,
        Res.drawable.poemauthor8,
        Res.drawable.poemauthor9,
        Res.drawable.poemauthor11,
        Res.drawable.poemauthor12,
        Res.drawable.poemauthor13,
        Res.drawable.poemauthor14,
        Res.drawable.poemauthor15,
        Res.drawable.poemauthor16,
        Res.drawable.poemauthor17,
        Res.drawable.poemauthor18,
        Res.drawable.poemauthor19,
        Res.drawable.poemauthor20,
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
