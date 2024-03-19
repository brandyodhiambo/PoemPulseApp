package presentation.todaypoem

import NetworkResult
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.todaypoem.TodayPoem
import domain.usecase.GetTodayPoemUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.UiEvents

class TodayPoemViewModel(
    private val getTodayPoemUseCase: GetTodayPoemUseCase,
) : ScreenModel {
    private val viewModelScope = screenModelScope

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow get() = _eventFlow.asSharedFlow()

    private val _todayPoemState = MutableStateFlow(TodayPoemState())
    val todayPoemState get() = _todayPoemState.asStateFlow()
    private fun getTodayPoem() {
        viewModelScope.launch {
           _todayPoemState.update { it.copy(isLoading = true) }
            val randomId = (0..10).random()
            getTodayPoemUseCase.invoke(randomId).collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _todayPoemState.update {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent(
                                result.errorMessage ?: "Unknown Error Occurred"
                            )
                        )
                    }

                    is NetworkResult.Success -> {
                        _todayPoemState.update {
                            it.copy(
                                isLoading = false,
                                poem = result.data ?: emptyList()
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    init {
        getTodayPoem()
    }
}

data class TodayPoemState(
    val isLoading:Boolean = false,
    val error:String? = null,
    val poem:List<TodayPoem> = emptyList()

)
