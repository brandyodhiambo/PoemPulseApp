package com.brandyodhiambo.poempulse.data.local.dao

import com.brandyodhiambo.poempulse.database.PoemDatabase
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

    suspend fun insertAuthor(authorName:String) = withContext(Dispatchers.IO){
        dbQuery.insertAuthor(authorName)
    }

    suspend fun deleteAuthor() = withContext(Dispatchers.IO){
        dbQuery.deleteAllAuthor()
    }
}