package com.brandyodhiambo.poempulse.domain.model.author


data class AuthorPoem(
    val author: String,
    val linecount: String,
    val lines: List<String>,
    val title: String,
)
