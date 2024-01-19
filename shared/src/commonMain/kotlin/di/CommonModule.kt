package di

import ApiService
import AuthorViewModel
import Constants.BASE_URL
import com.brandyodhiambo.poempulse.database.PoemDatabase
import data.local.adapter.idAdapter
import data.local.dao.AuthorDao
import data.local.dao.TitleDao
import data.local.dao.TodayPoemDao
import data.local.setting.PreferenceManager
import data.repository.PoemPulseRepositoryImpl
import data.repository.SettingRepositoryImpl
import database.AuthorEntity
import database.PoemTitleEntity
import database.TodayPoemEntity
import domain.repository.PoemPulseRepository
import domain.repository.SettingRepository
import domain.usecase.GetAuthorUseCase
import domain.usecase.GetAuthorPoemUseCase
import domain.usecase.GetGivenWordPoemUseCase
import domain.usecase.GetGivenWordTitleUseCase
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
import platform.DatabaseDriverFactory
import platform.httpClient
import presentation.givenwordpoem.GivenWordPoemViewModel
import presentation.main.MainViewModel
import presentation.title.TitleViewModel
import presentation.todaypoem.TodayPoemViewModel

fun commonModule() = module {
    /*
    * http client
    * */
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
    * database
    * */
    single<PoemDatabase>{
        PoemDatabase(
            driver = get<DatabaseDriverFactory>().createDriver(),
            authorEntityAdapter = AuthorEntity.Adapter(
                idAdapter = idAdapter,
            ),
            poemTitleEntityAdapter = PoemTitleEntity.Adapter(
                idAdapter = idAdapter
            ),
            todayPoemEntityAdapter = TodayPoemEntity.Adapter(
                idAdapter = idAdapter
            )

        )
    }

    /*
    * settings
    * */
    single<PreferenceManager>{
        PreferenceManager(settings = get())
    }

    single<SettingRepository>{
        SettingRepositoryImpl(
            preferenceManager = get()
        )
    }

    /*
    * Api Service
    * */
    single { ApiService(httpClient = get()) }

    /*
    * Dao
    * */
    single{ AuthorDao(poemDatabase = get())}
    single{TitleDao(poemDatabase = get())}
    single{TodayPoemDao(poemDatabase = get())}

    /*
    * Repository
    * */
    single<PoemPulseRepository> { PoemPulseRepositoryImpl(
        apiService = get(),
        authorDao = get(),
        titleDao = get(),
        todayPoemDao = get()
    ) }

    /*
    * usecase
    * */
    single<GetAuthorUseCase> { GetAuthorUseCase(poemPulseRepository = get()) }
    single<GetTodayPoemUseCase> { GetTodayPoemUseCase(poemPulseRepository = get()) }
    single<GetAuthorPoemUseCase>{GetAuthorPoemUseCase(poemPulseRepository = get()) }
    single<GetGivenWordPoemUseCase>{ GetGivenWordPoemUseCase(poemPulseRepository = get()) }
    single<GetPoemTitleUseCase>{ GetPoemTitleUseCase(poemPulseRepository = get()) }
    single<GetTitleLineUseCase>{ GetTitleLineUseCase(poemPulseRepository = get()) }
    single<GetGivenWordTitleUseCase>{ GetGivenWordTitleUseCase(poemPulseRepository = get()) }

    /*
    * View model
    * */
    single<AuthorViewModel> { AuthorViewModel(getAuthorUseCase = get(), getAuthorPoemUseCase = get()) }
    single<TodayPoemViewModel> { TodayPoemViewModel(getTodayPoemUseCase = get()) }
    single<GivenWordPoemViewModel>{ GivenWordPoemViewModel(getGivenWordPoemUseCase = get()) }
    single<TitleViewModel>{ TitleViewModel(getPoemTitleUseCase = get(), getTitleLineUseCase = get(), getGivenWordTitleUseCase = get()) }
    single<MainViewModel> {
        MainViewModel(
            settingRepository = get(),
        )
    }


}
