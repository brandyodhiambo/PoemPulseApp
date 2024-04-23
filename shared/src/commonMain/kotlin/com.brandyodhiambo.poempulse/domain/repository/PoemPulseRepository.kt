package com.brandyodhiambo.poempulse.domain.repository

import com.brandyodhiambo.poempulse.utils.NetworkResult
import com.brandyodhiambo.poempulse.domain.model.author.AuthorPoem
import com.brandyodhiambo.poempulse.domain.model.title.TitleLine
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem

interface PoemPulseRepository {

    suspend fun getAuthor(): NetworkResult<List<String>>

    suspend fun getTitle(): NetworkResult<List<String>>

    suspend fun getTitleLine(title:String):NetworkResult<List<TitleLine>>


    suspend fun getTodayPoem(randomPoemCount: Int): NetworkResult<List<TodayPoem>>
    suspend fun getAuthorPoem(authorName: String): NetworkResult<List<AuthorPoem>>

}
