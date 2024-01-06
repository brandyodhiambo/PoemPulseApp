package data.local.dao

import com.brandyodhiambo.poempulse.database.PoemDatabase
import database.AuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AuthorDao(
    private val poemDatabase: PoemDatabase
) {

    private val dbQuery = poemDatabase.authorQueries

    suspend fun getAuthor() = withContext(Dispatchers.IO){
        dbQuery.getAllAuthor().executeAsList()
    }

    suspend fun insertAuthor(authorEntity: AuthorEntity) = withContext(Dispatchers.IO){
        dbQuery.insertAuthor(authorEntity.name)
    }

    suspend fun deleteAuthor() = withContext(Dispatchers.IO){
        dbQuery.deleteAllAuthor()
    }
}