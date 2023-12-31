import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.author.AuthorPoem
import domain.usecase.GetAuthorPoemUseCase
import domain.usecase.GetAuthorUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthorViewModel(
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getAuthorPoemUseCase: GetAuthorPoemUseCase,
) : StateScreenModel<AuthorState>(AuthorState.Init) {

    init {
        getAuthors()
    }
    private fun getAuthors() {
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

    fun getAuthorPoem(authorName: String) {
        coroutineScope.launch {
            mutableState.value = AuthorState.Loading

            getAuthorPoemUseCase.invoke(authorName).collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        mutableState.value = AuthorState.Error(error = result.errorMessage ?: "Unknown Error occurred")
                    }

                    is NetworkResult.Success -> {
                        mutableState.value = AuthorState.AuthorPoemResult(poem = result.data ?: emptyList())
                    }

                    else -> {}
                }
            }
        }
    }
}

sealed class AuthorState {
    data object Init : AuthorState()
    data object Loading : AuthorState()

    data class Result(val authors: List<String>) : AuthorState()
    data class AuthorPoemResult(val poem: List<AuthorPoem>) : AuthorState()

    data class Error(val error: String) : AuthorState()
}
