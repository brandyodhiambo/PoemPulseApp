package com.brandyodhiambo.poempulse.presentation.title

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.brandyodhiambo.poempulse.domain.model.title.TitleLine
import com.brandyodhiambo.poempulse.domain.usecase.GetPoemTitleUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTitleLineUseCase
import com.brandyodhiambo.poempulse.utils.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.brandyodhiambo.poempulse.utils.UiEvents


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

    private fun getTitle() {
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