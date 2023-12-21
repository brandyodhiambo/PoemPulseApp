package di

import ApiService
import AuthorViewModel
import Constants.BASE_URL
import data.repository.PoemPulseRepositoryImpl
import domain.repository.PoemPulseRepository
import domain.usecase.AuthorUseCase
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import platform.httpClient

fun commonModule() = module {
    single {
        httpClient {
            defaultRequest {
                url {
                    host = BASE_URL
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(HttpTimeout) {
               /* requestTimeoutMillis = 3000L
                connectTimeoutMillis = 3000L
                socketTimeoutMillis = 3000L*/
            }
        }
    }

    /*
    * Api Service
    * */
    single { ApiService(httpClient = get()) }

    /*
    * Repository
    * */

    single<PoemPulseRepository> { PoemPulseRepositoryImpl(apiService = get()) }

    /*
    * usecase
    * */
    single<AuthorUseCase> { AuthorUseCase(poemPulseRepository = get()) }

    /*
    * Viewmodel
    * */
    single<AuthorViewModel> { AuthorViewModel(authorUseCase = get()) }
}
