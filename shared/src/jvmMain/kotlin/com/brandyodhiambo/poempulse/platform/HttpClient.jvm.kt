package com.brandyodhiambo.poempulse.platform

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(CIO) {
        config()
    }
}