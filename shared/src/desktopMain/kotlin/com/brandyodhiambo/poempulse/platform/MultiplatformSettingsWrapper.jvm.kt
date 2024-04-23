
package com.brandyodhiambo.poempulse.platform

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class MultiplatformSettingsWrapper {
    actual fun createSettings(): Settings {
        val delegate: Preferences = Preferences.userRoot()
        return PreferencesSettings(delegate)
    }
}
