package com.brandyodhiambo.poempulse.data.network.dto.title


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleDto(
    @SerialName("titles")
    val titles: List<String>
)