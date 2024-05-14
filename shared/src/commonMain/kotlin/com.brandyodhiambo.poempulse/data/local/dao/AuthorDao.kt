/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brandyodhiambo.poempulse.data.local.dao

import com.brandyodhiambo.poempulse.database.PoemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AuthorDao(
    private val poemDatabase: PoemDatabase
) {

    private val dbQuery = poemDatabase.authorQueries

    suspend fun getAuthor() = withContext(Dispatchers.IO) {
        dbQuery.getAllAuthor().executeAsList()
    }

    suspend fun insertAuthor(authorName: String) = withContext(Dispatchers.IO) {
        dbQuery.insertAuthor(authorName)
    }

    suspend fun deleteAuthor() = withContext(Dispatchers.IO) {
        dbQuery.deleteAllAuthor()
    }
}
