package presentation.title

import NetworkResult
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.title.TitleLine
import domain.usecase.GetPoemTitleUseCase
import domain.usecase.GetTitleLineUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TitleViewModel(
    private val getPoemTitleUseCase: GetPoemTitleUseCase,
    private val getTitleLineUseCase: GetTitleLineUseCase
):StateScreenModel<TitleState>(TitleState.Init) {

    init {
        getTitle()
    }

    private fun getTitle(){
        coroutineScope.launch {
            mutableState.value = TitleState.Loading

            getPoemTitleUseCase.invoke().collectLatest { result->
                when(result){
                    is NetworkResult.Error->{
                        mutableState.value = TitleState.Error(error = result.errorMessage ?:"Unknown Error Occurred")
                    }

                    is NetworkResult.Success ->{
                        mutableState.value = TitleState.Result(title = result.data?: emptyList())
                    }

                    else -> {}
                }

            }
        }
    }

    fun getTitleLine(title:String){
        coroutineScope.launch {
            mutableState.value = TitleState.Loading

            getTitleLineUseCase.invoke(title).collectLatest { result->
                when(result){
                    is NetworkResult.Error ->{
                        mutableState.value = TitleState.Error(error = result.errorMessage?:"Unknown Error Occurred")
                    }

                    is NetworkResult.Success->{
                        mutableState.value = TitleState.TitleLineResult(line = result.data ?: emptyList())
                    }

                    else -> {}
                }
            }
        }
    }
}

sealed class TitleState{
    data object Init:TitleState()
    data object Loading:TitleState()

    data class Result(val title:List<String>):TitleState()

    data class TitleLineResult(val line:List<TitleLine>):TitleState()

    data class Error(val error:String):TitleState()
}