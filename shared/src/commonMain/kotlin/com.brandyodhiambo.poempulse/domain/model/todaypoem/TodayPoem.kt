package com.brandyodhiambo.poempulse.domain.model.todaypoem


data class TodayPoem(
    val author: String,
    val linecount: String,
    val lines: List<String>,
    val title: String,
)