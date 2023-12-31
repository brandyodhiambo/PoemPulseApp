package di

import ApiService
import AuthorViewModel
import Constants.BASE_URL
import data.repository.PoemPulseRepositoryImpl
import domain.repository.PoemPulseRepository
import domain.usecase.GetAuthorUseCase
import domain.usecase.GetAuthorPoemUseCase
import domain.usecase.GetGivenWordPoemUseCase
import domain.usecase.GetPoemTitleUseCase
import domain.usecase.GetTitleLineUseCase
import domain.usecase.GetTodayPoemUseCase
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
import presentation.givenwordpoem.GivenWordPoemViewModel
import presentation.title.TitleViewModel
import presentation.todaypoem.TodayPoemViewModel

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
    single<GetAuthorUseCase> { GetAuthorUseCase(poemPulseRepository = get()) }
    single<GetTodayPoemUseCase> { GetTodayPoemUseCase(poemPulseRepository = get()) }
    single<GetAuthorPoemUseCase>{GetAuthorPoemUseCase(poemPulseRepository = get()) }
    single<GetGivenWordPoemUseCase>{ GetGivenWordPoemUseCase(poemPulseRepository = get()) }
    single<GetPoemTitleUseCase>{ GetPoemTitleUseCase(poemPulseRepository = get()) }
    single<GetTitleLineUseCase>{ GetTitleLineUseCase(poemPulseRepository = get()) }

    /*
    * View model
    * */
    single<AuthorViewModel> { AuthorViewModel(getAuthorUseCase = get(), getAuthorPoemUseCase = get()) }
    single<TodayPoemViewModel> { TodayPoemViewModel(getTodayPoemUseCase = get()) }
    single<GivenWordPoemViewModel>{ GivenWordPoemViewModel(getGivenWordPoemUseCase = get()) }
    single<TitleViewModel>{ TitleViewModel(getPoemTitleUseCase = get(), getTitleLineUseCase = get()) }


}
