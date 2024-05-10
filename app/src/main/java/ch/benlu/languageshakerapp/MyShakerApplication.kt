package ch.benlu.languageshakerapp

import ch.benlu.languageshakerlib.LanguageShakerApplication
import java.util.Locale

class MyShakerApplication: LanguageShakerApplication() {
    override fun onCreate() {
        super.onCreate()

        // Set the values here instead of in the constructor (if you use Hilt)
        isActive = BuildConfig.DEBUG
        keyLocale = Locale.forLanguageTag("zu")
        timeDiff = 3000
        shakeAcceleration = 12
        showToast = false
    }
}