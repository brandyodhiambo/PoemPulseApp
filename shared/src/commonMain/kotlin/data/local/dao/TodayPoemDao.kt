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

    suspend fun insertTodayPoem(todayPoemEntity: TodayPoemEntity) = withContext(Dispatchers.IO){
        dbQuery.insertTodayPoem(
            todayPoemEntity.author,
            todayPoemEntity.linecount,
            todayPoemEntity.lines,
            todayPoemEntity.title
        )
    }

    suspend fun deleteTodayPoem() = withContext(Dispatchers.IO){
        dbQuery.deleteAllTodayPoem()
    }
}