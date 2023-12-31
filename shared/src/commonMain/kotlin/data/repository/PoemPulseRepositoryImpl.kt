package data.repository

import ApiService
import NetworkResult
import domain.model.author.AuthorPoem
import domain.model.givenwordpoem.GivenWordPoem
import domain.model.title.TitleLine
import domain.model.todaypoem.TodayPoem
import domain.repository.PoemPulseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import safeApiCall
import toDomain

class PoemPulseRepositoryImpl(
    private val apiService: ApiService,
) : PoemPulseRepository {
    override suspend fun getAuthor(): Flow<NetworkResult<List<String>>> = flow {
        val response = safeApiCall {
            apiService.getAuthors().authors
        }
        emit(response)
    }

    override suspend fun getTitle(): Flow<NetworkResult<List<String>>> = flow {
        val response = safeApiCall {
            apiService.getTitles().titles
        }
        emit(response)
    }

    override suspend fun getTitleLine(title: String): Flow<NetworkResult<List<TitleLine>>> = flow{
        val response = safeApiCall {
            apiService.getTitleLines(title).map { it.toDomain() }
        }
        emit(response)
    }

    override suspend fun getTodayPoem(dayNumber: Int): Flow<NetworkResult<List<TodayPoem>>>  = flow{
        val response = safeApiCall {
            apiService.getTodayPoem(dayNumber).map { it.toDomain() }
        }
        emit(response)
    }

    override suspend fun getAuthorPoem(authorName: String): Flow<NetworkResult<List<AuthorPoem>>> = flow {
        val response = safeApiCall {
            apiService.getAuthorPoem(authorName = authorName).map { it.toDomain() }
        }
        emit(response)
    }

    override suspend fun getGivenWordPoem(word: String): Flow<NetworkResult<List<GivenWordPoem>>>  = flow{
        val response  = safeApiCall {
            apiService.getGivenWordPoem(word).map { it.toDomain() }
        }
        emit(response)
    }
}
