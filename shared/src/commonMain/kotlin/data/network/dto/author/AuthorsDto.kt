package data.network.dto.author

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorsDto(
    @SerialName("authors")
    val authors: List<String>,
)
