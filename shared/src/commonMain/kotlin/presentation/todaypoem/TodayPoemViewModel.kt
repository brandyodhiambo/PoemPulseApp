package presentation.todaypoem

import NetworkResult
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.todaypoem.TodayPoem
import domain.usecase.GetTodayPoemUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.UiEvents

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
