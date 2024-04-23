package com.brandyodhiambo.poempulse.di

import org.koin.core.module.Module
import org.koin.dsl.module
import com.brandyodhiambo.poempulse.platform.DatabaseDriverFactory
import com.brandyodhiambo.poempulse.platform.MultiplatformSettingsWrapper

actual fun platformModule() : Module = module{
    single { MultiplatformSettingsWrapper(context = get()).createSettings() }
    single{ DatabaseDriverFactory(context = get()) }
}