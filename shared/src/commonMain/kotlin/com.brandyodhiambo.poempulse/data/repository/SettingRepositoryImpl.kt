/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.brandyodhiambo.poempulse.data.repository

import com.brandyodhiambo.poempulse.data.local.setting.PreferenceManager
import com.brandyodhiambo.poempulse.domain.repository.SettingRepository
import com.brandyodhiambo.poempulse.utils.Constants.APP_THEME
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(
    private val preferenceManager: PreferenceManager
) : SettingRepository {
    override suspend fun saveAppTheme(theme: Int) {
        preferenceManager.setInt(key = APP_THEME, value = theme)
    }

    override fun getAppTheme(): Flow<Int?> {
        return preferenceManager.getInt(APP_THEME)
    }

    override fun clearAll() {
        return preferenceManager.clearPreferences()
    }
}
