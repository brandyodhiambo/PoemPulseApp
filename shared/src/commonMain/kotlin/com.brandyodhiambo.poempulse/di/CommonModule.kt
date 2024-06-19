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
package com.brandyodhiambo.poempulse.di

import ApiService
import AuthorViewModel
import com.brandyodhiambo.poempulse.data.local.adapter.idAdapter
import com.brandyodhiambo.poempulse.data.local.dao.AuthorDao
import com.brandyodhiambo.poempulse.data.local.dao.TitleDao
import com.brandyodhiambo.poempulse.data.local.dao.TodayPoemDao
import com.brandyodhiambo.poempulse.data.local.setting.PreferenceManager
import com.brandyodhiambo.poempulse.data.repository.PoemPulseRepositoryImpl
import com.brandyodhiambo.poempulse.data.repository.SettingRepositoryImpl
import com.brandyodhiambo.poempulse.database.PoemDatabase
import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository
import com.brandyodhiambo.poempulse.domain.repository.SettingRepository
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorPoemUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetAuthorUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetPoemTitleUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTitleLineUseCase
import com.brandyodhiambo.poempulse.domain.usecase.GetTodayPoemUseCase
import com.brandyodhiambo.poempulse.platform.DatabaseDriverFactory
import com.brandyodhiambo.poempulse.platform.httpClient
import com.brandyodhiambo.poempulse.presentation.main.MainViewModel
import com.brandyodhiambo.poempulse.presentation.title.TitleViewModel
import com.brandyodhiambo.poempulse.presentation.todaypoem.TodayPoemViewModel
import com.brandyodhiambo.poempulse.utils.Constants.BASE_URL
import database.AuthorEntity
import database.PoemTitleEntity
import database.TodayPoemEntity
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun commonModule() = module {
    /*
    * http client
    * */
    single {
        httpClient {
            expectSuccess = true

            addDefaultResponseValidation()

            defaultRequest {
                url {
                    host = BASE_URL
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }

            val jsonResponse = Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
                allowStructuredMapKeys = true
            }

            install(ContentNegotiation) {
                json(
                    json = jsonResponse,
                )
            }

            install(ContentNegotiation) {
                json(
                    json = jsonResponse,
                )
            }


           /* install(HttpRequestRetry) {
                retryIf { _, response -> response.status.value.let { it == 401 } }
                exponentialDelay()
            }*/
        }
    }

    /*
    * database
    * */
    single<PoemDatabase> {
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
    single<PreferenceManager> {
        PreferenceManager(settings = get())
    }

    single<SettingRepository> {
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
    single { AuthorDao(poemDatabase = get()) }
    single { TitleDao(poemDatabase = get()) }
    single { TodayPoemDao(poemDatabase = get()) }

    /*
    * Repository
    * */
    single<PoemPulseRepository> {
        PoemPulseRepositoryImpl(
            apiService = get(),
            authorDao = get(),
            titleDao = get(),
            todayPoemDao = get()
        )
    }

    /*
    * usecase
    * */
    single<GetAuthorUseCase> { GetAuthorUseCase(poemPulseRepository = get()) }
    single<GetTodayPoemUseCase> { GetTodayPoemUseCase(poemPulseRepository = get()) }
    single<GetAuthorPoemUseCase> { GetAuthorPoemUseCase(poemPulseRepository = get()) }
    single<GetPoemTitleUseCase> { GetPoemTitleUseCase(poemPulseRepository = get()) }
    single<GetTitleLineUseCase> { GetTitleLineUseCase(poemPulseRepository = get()) }

    /*
    * View model
    * */
    single<AuthorViewModel> { AuthorViewModel(getAuthorUseCase = get(), getAuthorPoemUseCase = get()) }
    single<TodayPoemViewModel> { TodayPoemViewModel(getTodayPoemUseCase = get()) }
    single<TitleViewModel> { TitleViewModel(getPoemTitleUseCase = get(), getTitleLineUseCase = get()) }
    single<MainViewModel> {
        MainViewModel(
            settingRepository = get(),
        )
    }
}
