package com.brandyodhiambo.poempulse.di

import ApiService
import AuthorViewModel
import com.brandyodhiambo.poempulse.utils.Constants.BASE_URL
import com.brandyodhiambo.poempulse.database.PoemDatabase
import com.brandyodhiambo.poempulse.data.local.adapter.idAdapter
import com.brandyodhiambo.poempulse.data.local.dao.AuthorDao
import com.brandyodhiambo.poempulse.data.local.dao.TitleDao
import com.brandyodhiambo.poempulse.data.local.dao.TodayPoemDao
import com.brandyodhiambo.poempulse.data.local.setting.PreferenceManager
import com.brandyodhiambo.poempulse.data.repository.PoemPulseRepositoryImpl
import com.brandyodhiambo.poempulse.data.repository.SettingRepositoryImpl
import database.AuthorEntity
import database.PoemTitleEntity
import database.TodayPoemEntity
import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository
import com.brandyodhiambo.poempulse.domain.repository.SettingRepository
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorPoemUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetPoemTitleUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTitleLineUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTodayPoemUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
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
import com.brandyodhiambo.poempulse.platform.DatabaseDriverFactory
import com.brandyodhiambo.poempulse.presentation.main.MainViewModel
import com.brandyodhiambo.poempulse.presentation.title.TitleViewModel
import com.brandyodhiambo.poempulse.presentation.todaypoem.TodayPoemViewModel

fun commonModule() = module {
    /*
    * http client
    * */
    single {
        HttpClient(CIO) {
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
    single{ AuthorDao(poemDatabase = get()) }
    single{ TitleDao(poemDatabase = get()) }
    single{ TodayPoemDao(poemDatabase = get()) }

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
    single<GetAuthorPoemUseCase>{ GetAuthorPoemUseCase(poemPulseRepository = get()) }
    single<GetPoemTitleUseCase>{ GetPoemTitleUseCase(poemPulseRepository = get()) }
    single<GetTitleLineUseCase>{ GetTitleLineUseCase(poemPulseRepository = get()) }

    /*
    * View model
    * */
    single<AuthorViewModel> { AuthorViewModel(getAuthorUseCase = get(), getAuthorPoemUseCase = get()) }
    single<TodayPoemViewModel> { TodayPoemViewModel(getTodayPoemUseCase = get()) }
    single<TitleViewModel>{ TitleViewModel(getPoemTitleUseCase = get(), getTitleLineUseCase = get()) }
    single<MainViewModel> {
        MainViewModel(
            settingRepository = get(),
        )
    }


}
