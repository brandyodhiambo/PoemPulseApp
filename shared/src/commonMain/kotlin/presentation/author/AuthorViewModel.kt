import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.GetAuthorUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthorViewModel(
    private val getAuthorUseCase: GetAuthorUseCase,
) : StateScreenModel<AuthorState>(AuthorState.Init) {
    fun getAuthors() {
        coroutineScope.launch {
            mutableState.value = AuthorState.Loading

            getAuthorUseCase.invoke().collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        mutableState.value = AuthorState.Error(error = result.errorMessage ?: "Unknown Error")
                    }

                    is NetworkResult.Success -> {
                        mutableState.value = AuthorState.Result(authors = result.data ?: emptyList())
                    }

                    else -> {}
                }
            }
        }
    }

    init {
        getAuthors()
    }
}

sealed class AuthorState {
    data object Init : AuthorState()
    data object Loading : AuthorState()

    data class Result(val authors: List<String>) : AuthorState()

    data class Error(val error: String) : AuthorState()
}
