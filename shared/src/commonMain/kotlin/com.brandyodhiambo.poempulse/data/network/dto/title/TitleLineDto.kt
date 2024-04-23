package com.brandyodhiambo.poempulse.data.network.dto.title


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

    @Serializable
    data class TitleLineDto(
        @SerialName("author")
        val author: String,
        @SerialName("linecount")
        val linecount: String,
        @SerialName("lines")
        val lines: List<String>,
        @SerialName("title")
        val title: String
    )