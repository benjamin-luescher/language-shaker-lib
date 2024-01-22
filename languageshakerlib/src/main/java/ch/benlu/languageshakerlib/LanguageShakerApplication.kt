package ch.benlu.languageshakerlib

import android.app.Application
import android.app.LocaleManager
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.LocaleList
import android.widget.Toast
import java.util.Locale
import kotlin.math.sqrt

open class LanguageShakerApplication(
    val isActive: Boolean = true,
    val keyLocale: Locale,
    val timeDiff: Int = 3000,
    val shakeAcceleration: Int = 12,
    val showToast: Boolean = true
): Application() {
    private lateinit var sensorManager: SensorManager
    private var lastShakeTime: Long = 0

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private var currentLocale: Locale = Locale.getDefault()

        // TODO: would be nice to change language without activity restart!
        fun switchLanguage(context: Context, locale: Locale): Locale {
            val targetLocale = if (currentLocale == locale) {
                getDeviceLocale()
            } else {
                locale
            }
            println("Switch Language... Change language from $currentLocale to $targetLocale")

            Locale.setDefault(targetLocale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(targetLocale)

            resources.updateConfiguration(configuration, resources.displayMetrics)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList(targetLocale)
            }

            currentLocale = targetLocale

            return targetLocale
        }

        private fun getDeviceLocale(): Locale {
            val language = loadLanguage()
            val country = loadCountry()

            if (language != null && country != null) {
                val locale = Locale(language, country)
                println("Settings restored $locale")
                return locale
            }

            val locale = Locale.getDefault()
            saveSettings(locale)
            println("Settings initialized $locale")
            return locale
        }

        private fun saveSettings(locale: Locale) {
            val editor = sharedPreferences.edit()
            editor.putString("Language", locale.language)
            editor.putString("Country", locale.country)
            editor.apply()

            println("Settings saved $locale")
        }

        private fun loadLanguage(): String? {
            return sharedPreferences.getString("Language", null)
        }

        private fun loadCountry(): String? {
            return sharedPreferences.getString("Country", null)
        }
    }

    override fun onCreate() {
        println("LanguageShakeApplication onCreate")

        super.onCreate()
        sharedPreferences = getSharedPreferences("LanguageShakerSettings", Context.MODE_PRIVATE)

        switchLanguage(this, getDeviceLocale())
        startShakeListener(this)

    }

    override fun onTerminate() {
        super.onTerminate()
        stopShakeListener()
        println("LanguageShakeApplication onTerminate")
    }

    private fun stopShakeListener() {
        println("Stop shake listener")
        sensorManager.unregisterListener(accelerometerListener)
    }

    private fun startShakeListener(context: Context) {
        println("Start shake listener")

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometerSensor != null) {
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            showToast("Accelerometer not available on this device")
        }
    }

    private fun shake(): Locale {
        val target = switchLanguage(this, keyLocale)
        showToast("Language changed to ${target.language}")
        return target
    }

    private val accelerometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val currentTime = System.currentTimeMillis()
                val timeDifference = currentTime - lastShakeTime

                if (timeDifference > timeDiff) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val acceleration = sqrt((x * x + y * y + z * z).toDouble())
                    if (acceleration > shakeAcceleration) {
                        shake()
                        lastShakeTime = currentTime
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // nothing to do here
        }
    }



    private fun showToast(message: String) {
        if (showToast) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}