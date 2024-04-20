import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.author.Author
import domain.model.author.AuthorPoem
import domain.usecase.GetAuthorPoemUseCase
import domain.usecase.GetAuthorUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.UiEvents

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
    private fun getAuthors() {
        _authorUiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            getAuthorUseCase().collectLatest { result ->
                when (result) {
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
                                author = result.data ?: emptyList()
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun getAuthorPoem(authorName: String) {
        _authorUiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            getAuthorPoemUseCase.invoke(authorName).collectLatest { result ->
                when (result) {
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

                    else -> {}
                }
            }
        }
    }
}


data class AuthorState(
    val isLoading:Boolean= false,
    val error:String? = null,
    val author:List<String> = emptyList(),
    val authorPoem:List<AuthorPoem> = emptyList()
)
