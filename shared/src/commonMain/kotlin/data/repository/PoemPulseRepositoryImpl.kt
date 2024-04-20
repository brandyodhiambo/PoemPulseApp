package data.repository

import ApiService
import NetworkResult
import data.local.dao.AuthorDao
import data.local.dao.TitleDao
import data.local.dao.TodayPoemDao
import database.TodayPoemEntity
import domain.model.author.Author
import domain.model.author.AuthorPoem
import domain.model.givenwordpoem.GivenWordPoem
import domain.model.title.GivenWordTitle
import domain.model.title.Title
import domain.model.title.TitleLine
import domain.model.todaypoem.TodayPoem
import domain.repository.PoemPulseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import safeApiCall
import toAuthorEntity
import toDomain
import toTitleEntity
import toTodayPoemEntity

class PoemPulseRepositoryImpl(
    private val authorDao: AuthorDao,
    private val titleDao: TitleDao,
    private val todayPoemDao: TodayPoemDao,
    private val apiService: ApiService,
) : PoemPulseRepository {

    override suspend fun getAuthor(): Flow<NetworkResult<List<String>>> = flow{
        val response = safeApiCall {
            val cachedAuthor = authorDao.getAuthor()

            if(cachedAuthor.isEmpty()){
                val apiAuthor = apiService.getAuthors()
                apiAuthor.authors.forEach { name->
                    val author = Author(
                        name = name
                    )
                    authorDao.insertAuthor(author.toAuthorEntity())
                }
            } else{
                authorDao.deleteAuthor()
                val apiAuthor = apiService.getAuthors()
                apiAuthor.authors.forEach { name->
                    val author = Author(
                        name = name
                    )
                    authorDao.insertAuthor(author.toAuthorEntity())
                }
            }
            authorDao.getAuthor().map { it.name }
        }
        emit(response)
    }

    override suspend fun getTitle(): Flow<NetworkResult<List<String>>> = flow {
        val response = safeApiCall {
            val cachedTitle = titleDao.getPoemTitle()

            if(cachedTitle.isEmpty()){
                val apiTitle =  apiService.getTitles()
                apiTitle.titles.forEach {
                    val title = Title(
                        title = it
                    )
                    titleDao.insertPoemTitle(title.toTitleEntity())
                }
            } /*else{
                titleDao.deletePoemTitle()
                val apiTitle =  apiService.getTitles()
                apiTitle.titles.forEach {
                    val title = Title(
                        title = it
                    )
                    titleDao.insertPoemTitle(title.toTitleEntity())
                }
            }*/
            titleDao.getPoemTitle().map { it.title }
        }
        emit(response)
    }

    override suspend fun getTitleLine(title: String): Flow<NetworkResult<List<TitleLine>>> = flow{
        val response = safeApiCall {
            apiService.getTitleLines(title).map { it.toDomain() }
        }
        emit(response)
    }

    override suspend fun getGivenWordTitle(word: String): Flow<NetworkResult<List<GivenWordTitle>>> = flow{
        val response = safeApiCall {
            apiService.getGivenWordTitle(word).map { it.toDomain() }
        }

        emit(response)
    }

    override suspend fun getTodayPoem(randomPoemCount: Int): NetworkResult<List<TodayPoem>> {
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

    override suspend fun getAuthorPoem(authorName: String): Flow<NetworkResult<List<AuthorPoem>>> = flow {
        val response = safeApiCall {
            apiService.getAuthorPoem(authorName = authorName).map { it.toDomain() }
        }
        emit(response)
    }

    override suspend fun getGivenWordPoem(word: String): Flow<NetworkResult<List<GivenWordPoem>>>  = flow{
        val response  = safeApiCall {
            apiService.getGivenWordPoem(word).map { it.toDomain() }
        }
        emit(response)
    }
}
