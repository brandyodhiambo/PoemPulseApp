import data.network.dto.author.AuthorsDto
import data.network.dto.todaypoem.TodayPoemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val httpClient: HttpClient,
) {

    suspend fun getAuthors(): AuthorsDto {
        return httpClient.get(urlString = "/author").body()
    }

    suspend fun getTodayPoem(dayNumber: Int): List<TodayPoemDto> {
        return httpClient.get(urlString = "/random/$dayNumber").body()
    }
}
