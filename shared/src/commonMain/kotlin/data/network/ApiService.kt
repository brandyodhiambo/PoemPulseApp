import data.network.dto.author.AuthorsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val httpClient: HttpClient,
) {

    suspend fun getAuthors(): AuthorsDto {
        return httpClient.get(urlString = "/author").body()
    }
}
