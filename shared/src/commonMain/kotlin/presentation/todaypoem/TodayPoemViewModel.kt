package presentation.todaypoem

import NetworkResult
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.todaypoem.TodayPoem
import domain.usecase.GetTodayPoemUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.UiEvents

class TodayPoemViewModel(
    private val getTodayPoemUseCase: GetTodayPoemUseCase,
) : StateScreenModel<TodayPoemState>(TodayPoemState.Init) {
    private val viewModelScope = screenModelScope

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow get() = _eventFlow.asSharedFlow()
    private fun getTodayPoem() {
        viewModelScope.launch {
            mutableState.value = TodayPoemState.Loading
            val randomId = (0..10).random()
            getTodayPoemUseCase.invoke(randomId).collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent(
                                result.errorMessage ?: "Unknown Error Occurred"
                            )
                        )
                    }

                    is NetworkResult.Success -> {
                        mutableState.value = TodayPoemState.Result(poems = result.data ?: emptyList())
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

sealed class TodayPoemState {
    data object Init : TodayPoemState()
    data object Loading : TodayPoemState()

    data class Result(val poems: List<TodayPoem>) : TodayPoemState()

    data class Error(val error: String) : TodayPoemState()
}
