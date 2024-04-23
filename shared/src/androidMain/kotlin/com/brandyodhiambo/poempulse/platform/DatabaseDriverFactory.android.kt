package com.brandyodhiambo.poempulse.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.brandyodhiambo.poempulse.database.PoemDatabase

actual class DatabaseDriverFactory(private val context: Context){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PoemDatabase.Schema,context,"poempulse.db")
    }

}