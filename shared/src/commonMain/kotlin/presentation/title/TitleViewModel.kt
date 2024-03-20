package presentation.title

import NetworkResult
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.title.GivenWordTitle
import domain.model.title.TitleLine
import domain.usecase.GetGivenWordTitleUseCase
import domain.usecase.GetPoemTitleUseCase
import domain.usecase.GetTitleLineUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.UiEvents

class TitleViewModel(
    private val getPoemTitleUseCase: GetPoemTitleUseCase,
    private val getTitleLineUseCase: GetTitleLineUseCase,
    private val getGivenWordTitleUseCase: GetGivenWordTitleUseCase
):ScreenModel {

    private val viewModelScope = screenModelScope
    private val _titleState = MutableStateFlow(TitleState())
    val titleState get() = _titleState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow get() = _eventFlow.asSharedFlow()

    init {
        getTitle()
    }

    private fun getTitle(){
        viewModelScope.launch {
           _titleState.update { it.copy(isLoading = true) }

            getPoemTitleUseCase.invoke().collectLatest { result->
                when(result){
                    is NetworkResult.Error->{
                        _titleState.update {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }
                        _eventFlow.emit(
                            UiEvents.SnackbarEvent(
                                message = result.errorMessage ?: "Unknown error occurred"
                            )
                        )
                    }

                    is NetworkResult.Success ->{
                        _titleState.update {
                            it.copy(
                                isLoading = false,
                                title = result.data ?: emptyList()
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }

    fun getTitleLine(title:String){
        viewModelScope.launch {
            _titleState.update { it.copy(isLoading = true) }

            getTitleLineUseCase.invoke(title).collectLatest { result->
                when(result){
                    is NetworkResult.Error ->{
                        _titleState.update {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }

                        _eventFlow.emit(
                            UiEvents.SnackbarEvent(
                                message = result.errorMessage ?:"Unknown error occurred"
                            )
                        )
                    }

                    is NetworkResult.Success->{
                        _titleState.update {
                            it.copy(
                                isLoading = false,
                                titleLines = result.data ?: emptyList()
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun getGivenWordTitle(word:String){
        viewModelScope.launch {
            _titleState.update { it.copy(isLoading = true) }

            getGivenWordTitleUseCase.invoke(word).collectLatest { result->
                when(result){
                    is NetworkResult.Error->{
                        _titleState.update { it.copy(isLoading = false,error = result.errorMessage) }
                    }

                    is NetworkResult.Success->{
                        _titleState.update { it.copy(isLoading = false, givenWordTitle = result.data ?: emptyList()) }
                    }

                    else -> {}
                }
            }
        }
    }
}


data class TitleState(
    val isLoading:Boolean = true,
    val error:String? = null,
    val title:List<String> = emptyList(),
    val titleLines:List<TitleLine> = emptyList(),
    val givenWordTitle:List<GivenWordTitle> = emptyList()

)