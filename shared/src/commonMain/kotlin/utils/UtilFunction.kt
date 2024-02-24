import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(data = apiCall.invoke())
    } catch (e: RedirectResponseException) { // 3xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
        )
    } catch (e: ClientRequestException) { // 4xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
        )
    } catch (e: ServerResponseException) { // 5xx errors

        NetworkResult.Error(
            errorCode = e.response.status.value,
            errorMessage = e.message,
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            errorCode = 0,
            errorMessage = e.message ?: "An unknown error occurred",
        )
    }
}

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> = staticCompositionLocalOf { null }

@Composable
fun ProvideAppNavigator(navigator: Navigator, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        content()
    }
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun FilledIcon(item: Tab) = when (item.options.index) {
    (0u).toUShort() -> painterResource("home_filled.xml")
    (1u).toUShort() -> painterResource("author_filled.xml")
    (2u).toUShort() -> painterResource("title_filled.xml")
    else -> painterResource("home_filled.xml")
}


fun divideIntoSmallerParagraphs(paragraph: String, linesPerParagraph: Int): String {
    val lines = paragraph.split("\n")
    val smallerParagraphs = mutableListOf<String>()
    var currentParagraph = StringBuilder()

    for (line in lines) {
        currentParagraph.append(line.trim())
        currentParagraph.append("\n")

        if (currentParagraph.lines().size >= linesPerParagraph) {
            smallerParagraphs.add(currentParagraph.toString().trim())
            currentParagraph = StringBuilder()
        }
    }

    if (currentParagraph.isNotEmpty()) {
        smallerParagraphs.add(currentParagraph.toString().trim())
    }

    return smallerParagraphs.joinToString("\n\n------------------\n\n")
}


