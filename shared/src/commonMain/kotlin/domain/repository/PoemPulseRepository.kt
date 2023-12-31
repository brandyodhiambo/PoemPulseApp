package domain.repository

import NetworkResult
import domain.model.author.AuthorPoem
import domain.model.todaypoem.TodayPoem
import kotlinx.coroutines.flow.Flow

interface PoemPulseRepository {

    suspend fun getAuthor(): Flow<NetworkResult<List<String>>>

    suspend fun getTodayPoem(dayNumber: Int): Flow<NetworkResult<List<TodayPoem>>>
    suspend fun getAuthorPoem(authorName: String): Flow<NetworkResult<List<AuthorPoem>>>
}
