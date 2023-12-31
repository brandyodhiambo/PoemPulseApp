package data.repository

import ApiService
import NetworkResult
import domain.model.author.AuthorPoem
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
}
