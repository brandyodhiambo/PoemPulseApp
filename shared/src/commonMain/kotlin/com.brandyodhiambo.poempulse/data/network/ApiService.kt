import com.brandyodhiambo.poempulse.data.network.dto.author.AuthorPoemDto
import com.brandyodhiambo.poempulse.data.network.dto.author.AuthorsDto
import com.brandyodhiambo.poempulse.data.network.dto.title.TitleDto
import com.brandyodhiambo.poempulse.data.network.dto.title.TitleLineDto
import com.brandyodhiambo.poempulse.data.network.dto.todaypoem.TodayPoemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val httpClient: HttpClient,
) {

    suspend fun getAuthors(): AuthorsDto {
        return httpClient.get(urlString = "/author").body()
    }

    suspend fun getTitle(): TitleDto {
        return httpClient.get(urlString = "/title").body()
    }

    suspend fun getTitleLines(title:String):List<TitleLineDto>{
        return httpClient.get(urlString = "/title/$title").body()
    }

    suspend fun getTodayPoem(dayNumber: Int): List<TodayPoemDto> {
        return httpClient.get(urlString = "/random/$dayNumber").body()
    }

    suspend fun getAuthorPoem(authorName: String): List<AuthorPoemDto> {
        return httpClient.get(urlString = "/author/$authorName").body()
    }

}
