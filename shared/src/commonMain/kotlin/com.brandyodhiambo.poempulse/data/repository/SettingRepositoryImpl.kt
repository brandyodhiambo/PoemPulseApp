package com.brandyodhiambo.poempulse.data.repository

import com.brandyodhiambo.poempulse.utils.Constants.APP_THEME
import com.brandyodhiambo.poempulse.data.local.setting.PreferenceManager
import com.brandyodhiambo.poempulse.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(
    private val preferenceManager: PreferenceManager
): SettingRepository {
    override suspend fun saveAppTheme(theme: Int) {
        preferenceManager.setInt(key = APP_THEME,value = theme)
    }

    override fun getAppTheme(): Flow<Int?> {
        return preferenceManager.getInt(APP_THEME)
    }

    override fun clearAll() {
        return preferenceManager.clearPreferences()
    }
}