package domain.repository

import NetworkResult
import kotlinx.coroutines.flow.Flow

interface PoemPulseRepository {

    suspend fun getAuthor(): Flow<NetworkResult<List<String>>>
}