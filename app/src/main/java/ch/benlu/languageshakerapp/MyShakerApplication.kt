package ch.benlu.languageshakerapp

import ch.benlu.languageshakerlib.LanguageShakerApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyShakerApplication : LanguageShakerApplication() {
    override fun onCreate() {
        init(
            isActive = BuildConfig.DEBUG,
            keyLocale = Locale.forLanguageTag("zu"),
            timeDiff = 3000,
            shakeAcceleration = 12,
            showToast = false,
        )

        super.onCreate()
    }
}
