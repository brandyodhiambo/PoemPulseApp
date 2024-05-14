/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    suspend fun getTitleLines(title: String): List<TitleLineDto> {
        return httpClient.get(urlString = "/title/$title").body()
    }

    suspend fun getTodayPoem(dayNumber: Int): List<TodayPoemDto> {
        return httpClient.get(urlString = "/random/$dayNumber").body()
    }

    suspend fun getAuthorPoem(authorName: String): List<AuthorPoemDto> {
        return httpClient.get(urlString = "/author/$authorName").body()
    }
}
