package com.brandyodhiambo.poempulse.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.brandyodhiambo.poempulse.database.PoemDatabase

actual class DatabaseDriverFactory{
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = PoemDatabase.Schema,name = "poempulse.db")
    }

}