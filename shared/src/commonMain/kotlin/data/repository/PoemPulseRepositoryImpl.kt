package data.repository

import ApiService
import NetworkResult
import domain.repository.PoemPulseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import safeApiCall

class PoemPulseRepositoryImpl(
    private val apiService: ApiService,
) : PoemPulseRepository {
    override suspend fun getAuthor(): Flow<NetworkResult<List<String>>> = flow {
        val response = safeApiCall {
            apiService.getAuthors().authors
        }
        emit(response)
    }
}
