package di

import org.koin.dsl.module
import org.koin.core.module.Module
import platform.DatabaseDriverFactory

actual fun platformModule():Module = module {
    single { DatabaseDriverFactory() }
}