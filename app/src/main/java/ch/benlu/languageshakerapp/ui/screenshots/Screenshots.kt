import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ch.benlu.languageshakerapp.WelcomeScreen
import ch.benlu.languageshakerapp.ui.theme.LanguageShakerAppTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ScreenshotOriginalLanguage() {
    LanguageShakerAppTheme(dynamicColor = false) {
        Surface {
            WelcomeScreen()
        }
    }
}

@Preview(locale = "zu", uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ScreenshotAfterShakeLanguage() {

    LanguageShakerAppTheme(dynamicColor = false) {
        Surface {
            WelcomeScreen()
        }
    }
}