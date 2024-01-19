package presentation.todaypoem

import NetworkResult
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.todaypoem.TodayPoem
import domain.usecase.GetTodayPoemUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodayPoemViewModel(
    private val getTodayPoemUseCase: GetTodayPoemUseCase,
) : StateScreenModel<TodayPoemState>(TodayPoemState.Init) {

    private fun getTodayPoem() {
        coroutineScope.launch {
            mutableState.value = TodayPoemState.Loading
            val randomId = (0..10).random()
            getTodayPoemUseCase.invoke(randomId).collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        mutableState.value = TodayPoemState.Error(error = result.errorMessage ?: "Unknown Error")
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
