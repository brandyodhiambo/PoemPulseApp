package com.brandyodhiambo.poempulse.di

import org.koin.dsl.module
import org.koin.core.module.Module
import com.brandyodhiambo.poempulse.platform.DatabaseDriverFactory
import com.brandyodhiambo.poempulse.platform.MultiplatformSettingsWrapper

actual fun platformModule():Module = module {
    single { MultiplatformSettingsWrapper().createSettings() }
    single { DatabaseDriverFactory() }
}