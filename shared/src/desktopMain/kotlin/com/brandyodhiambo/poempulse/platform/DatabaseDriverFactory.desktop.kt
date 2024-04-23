package com.brandyodhiambo.poempulse.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.brandyodhiambo.poempulse.database.PoemDatabase

actual class DatabaseDriverFactory{
    actual fun createDriver(): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { PoemDatabase.Schema.create(it) }
    }

}