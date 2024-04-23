package com.brandyodhiambo.poempulse.data.local.dao

import com.brandyodhiambo.poempulse.database.PoemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TitleDao(
    private val poemDatabase: PoemDatabase
) {
    private val dbQuery = poemDatabase.poemtitleQueries

    suspend fun getPoemTitle() = withContext(Dispatchers.IO){
        dbQuery.getAllPoemTitle().executeAsList()
    }

    suspend fun insertPoemTitle(title:String) = withContext(Dispatchers.IO){
        dbQuery.insertPoemTitle(title)
    }

    suspend fun deletePoemTitle() = withContext(Dispatchers.IO){
        dbQuery.deletePoemTitle()
    }
}