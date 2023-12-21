package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit,
): HttpClient {
    return HttpClient() {
        config(this)
    }
}
