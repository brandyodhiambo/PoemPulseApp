package domain.repository

import NetworkResult
import domain.model.author.AuthorPoem
import domain.model.givenwordpoem.GivenWordPoem
import domain.model.title.TitleLine
import domain.model.todaypoem.TodayPoem
import kotlinx.coroutines.flow.Flow

interface PoemPulseRepository {

    suspend fun getAuthor(): Flow<NetworkResult<List<String>>>

    suspend fun getTitle():Flow<NetworkResult<List<String>>>

    suspend fun getTitleLine(title:String):Flow<NetworkResult<List<TitleLine>>>

    suspend fun getTodayPoem(dayNumber: Int): Flow<NetworkResult<List<TodayPoem>>>
    suspend fun getAuthorPoem(authorName: String): Flow<NetworkResult<List<AuthorPoem>>>

    suspend fun getGivenWordPoem(word:String):Flow<NetworkResult<List<GivenWordPoem>>>
}
