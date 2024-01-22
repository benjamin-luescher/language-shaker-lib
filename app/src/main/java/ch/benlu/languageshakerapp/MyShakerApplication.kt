package ch.benlu.languageshakerapp

import ch.benlu.languageshakerlib.LanguageShakerApplication
import java.util.Locale

class MyShakerApplication: LanguageShakerApplication(
    isActive = BuildConfig.DEBUG, // only active in debug mode
    keyLocale = Locale.forLanguageTag("zu"), // define the "key locale" language
    timeDiff = 3000, // time difference between two shakes in milliseconds
    shakeAcceleration = 12, // shake acceleration
    showToast = false // show toast message when language is changed
)