package di

import org.koin.dsl.module
import org.koin.core.module.Module
import platform.DatabaseDriverFactory
import platform.MultiplatformSettingsWrapper

actual fun platformModule():Module = module {
    single { MultiplatformSettingsWrapper().createSettings() }
    single { DatabaseDriverFactory() }
}