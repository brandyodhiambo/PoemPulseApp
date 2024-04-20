package data.local.dao

import com.brandyodhiambo.poempulse.database.PoemDatabase
import database.TodayPoemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TodayPoemDao(
    private val poemDatabase: PoemDatabase
) {

    val dbQuery = poemDatabase.todaypoemQueries

    suspend fun getTodayPoem() = withContext(Dispatchers.IO){
        dbQuery.getAllTodayPoem().executeAsList()
    }

    suspend fun insertTodayPoem(
        author: String,
        linecount: String,
        lines: String,
        title: String
    ) = withContext(Dispatchers.IO){
        dbQuery.insertTodayPoem(
            author = author,
            linecount = linecount,
            lines = lines,
            title = title
        )
    }

    suspend fun deleteTodayPoem() = withContext(Dispatchers.IO){
        dbQuery.deleteAllTodayPoem()
    }
}