package presentation.givenwordpoem

import NetworkResult
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.givenwordpoem.GivenWordPoem
import domain.usecase.GetGivenWordPoemUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GivenWordPoemViewModel(
    private val getGivenWordPoemUseCase: GetGivenWordPoemUseCase
):StateScreenModel<GivenWordPoemState>(GivenWordPoemState.Init) {

    fun getGivenWordPoem(word:String){
        coroutineScope.launch {
            mutableState.value = GivenWordPoemState.Loading

            getGivenWordPoemUseCase.invoke(word).collectLatest {result->
                when(result){
                    is NetworkResult.Error ->{
                        mutableState.value = GivenWordPoemState.Error(error = result.errorMessage ?:"Unknown error occurred")
                    }
                    is NetworkResult.Success->{
                        mutableState.value = GivenWordPoemState.Result(poem = result.data ?: emptyList())
                    }

                    else -> {}
                }
            }
        }
    }
}

sealed class GivenWordPoemState{
    data object Init : GivenWordPoemState()
    data object Loading:GivenWordPoemState()

    data class Result(val poem:List<GivenWordPoem>):GivenWordPoemState()

    data class Error(val error:String):GivenWordPoemState()

}