package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.ios.Ios

actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit,
): HttpClient {
    return HttpClient(Ios) {
        config(this)
        engine {
            configureRequest {
                // setAllowsCellularAccess(true)
            }
        }
    }
}
