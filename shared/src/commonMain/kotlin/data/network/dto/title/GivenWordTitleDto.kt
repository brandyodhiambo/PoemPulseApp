package data.network.dto.title


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

    @Serializable
    data class GivenWordTitleDto(
        @SerialName("title")
        val title: String
    )
