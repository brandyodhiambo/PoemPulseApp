package data.repository

import Constants.APP_THEME
import data.local.setting.PreferenceManager
import domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(
    private val preferenceManager: PreferenceManager
):SettingRepository {
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