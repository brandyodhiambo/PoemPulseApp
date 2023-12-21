package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android

actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit,
): HttpClient {
    return HttpClient(Android) {
        config(this)
    }
}
