package di

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.DatabaseDriverFactory
import platform.MultiplatformSettingsWrapper

actual fun platformModule():Module = module {
    single { MultiplatformSettingsWrapper().createSettings() }
    single { DatabaseDriverFactory() }
}