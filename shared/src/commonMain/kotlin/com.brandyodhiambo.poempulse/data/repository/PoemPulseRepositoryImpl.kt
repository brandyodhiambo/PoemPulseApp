package com.brandyodhiambo.poempulse.data.repository

import ApiService
import com.brandyodhiambo.poempulse.utils.NetworkResult
import com.brandyodhiambo.poempulse.data.local.dao.AuthorDao
import com.brandyodhiambo.poempulse.data.local.dao.TitleDao
import com.brandyodhiambo.poempulse.data.local.dao.TodayPoemDao
import com.brandyodhiambo.poempulse.domain.model.author.AuthorPoem
import com.brandyodhiambo.poempulse.domain.model.title.TitleLine
import com.brandyodhiambo.poempulse.domain.model.todaypoem.TodayPoem
import com.brandyodhiambo.poempulse.domain.repository.PoemPulseRepository
import com.brandyodhiambo.poempulse.utils.safeApiCall
import toDomain

class PoemPulseRepositoryImpl(
    private val authorDao: AuthorDao,
    private val titleDao: TitleDao,
    private val todayPoemDao: TodayPoemDao,
    private val apiService: ApiService,
) : PoemPulseRepository {

    /**
     * Load data from the cache
     * Make API call,
     * If.. successful
     *  - Delete data from cache
     *  - Write the new data from API
     *  - Read from cache and return to user
     * If.. unsuccessful
     *  - Return data from cache
     */

    override suspend fun getAuthor(): NetworkResult<List<String>> {
        val cachedAuthor = authorDao.getAuthor().map { it.name }
        val response = safeApiCall(
            cachedData = cachedAuthor
        ) {
            val apiAuthor = apiService.getAuthors()
            authorDao.deleteAuthor()
            apiAuthor.authors.forEach { authorName->
                authorDao.insertAuthor(authorName)
            }
            authorDao.getAuthor().map { it.name }
        }
        return response
    }

    override suspend fun getTitle(): NetworkResult<List<String>> {
        val cachedTitle = titleDao.getPoemTitle().map { it.title }
        return safeApiCall(
            cachedData = cachedTitle
        ) {
            val apiTitle =  apiService.getTitle()
            apiTitle.titles.forEach {title->
                titleDao.insertPoemTitle(title)
            }
            titleDao.getPoemTitle().map { it.title }
        }
    }

    override suspend fun getTitleLine(title: String): NetworkResult<List<TitleLine>>{
        val response = safeApiCall {
            apiService.getTitleLines(title).map { it.toDomain() }
        }
        return response
    }


    override suspend fun getTodayPoem(randomPoemCount: Int): NetworkResult<List<TodayPoem>> {
        val cachedTodayPoem = todayPoemDao.getTodayPoem().map { it.toDomain() }

        return safeApiCall(
            cachedData = cachedTodayPoem
        ) {
            val apiTodayPoem = apiService.getTodayPoem(randomPoemCount)
            todayPoemDao.deleteTodayPoem()
            apiTodayPoem.forEach {
                todayPoemDao.insertTodayPoem(
                    linecount = it.linecount,
                    title = it.title,
                    lines = it.lines.joinToString(","),
                    author = it.author
                )
            }
            val newCachedData = todayPoemDao.getTodayPoem()

            newCachedData.map { it.toDomain() }
        }
    }

    override suspend fun getAuthorPoem(authorName: String): NetworkResult<List<AuthorPoem>>{
        val response = safeApiCall {
            apiService.getAuthorPoem(authorName = authorName).map { it.toDomain() }
        }
       return response
    }

}
