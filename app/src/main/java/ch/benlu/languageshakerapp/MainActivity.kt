package ch.benlu.languageshakerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.benlu.languageshakerapp.ui.theme.LanguageShakerAppTheme
import ch.benlu.languageshakerlib.LanguageShakerApplication
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageShakerAppTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    WelcomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        content = {
            Column(
                modifier =
                    modifier
                        .padding(16.dp)
                        .padding(it),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.headline),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.subline),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Icon(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                        painter = painterResource(id = R.drawable.baseline_vibration_24),
                        contentDescription = null,
                    )

                    Badge(
                        modifier =
                            Modifier
                                .padding(8.dp)
                                .align(alignment = Alignment.CenterHorizontally),
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            Text(text = stringResource(id = R.string.current_language_label))
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = Locale.getDefault().displayLanguage,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                    Card {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.how_to_headline),
                                style = MaterialTheme.typography.bodyLarge,
                            )

                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.how_to_text),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Button(
                                    onClick = { /*TODO*/ },
                                ) {
                                    Text(text = stringResource(id = R.string.btn_text_more))
                                }
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text(text = stringResource(id = R.string.btn_text_read))
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        // trigger shake event manually
                        LanguageShakerApplication.switchLanguage(
                            context,
                            Locale.forLanguageTag("zu"),
                        )
                    },
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                ) {
                    Icon(
                        painter =
                            painterResource(
                                id = R.drawable.baseline_vibration_24,
                            ),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = stringResource(id = R.string.shake_button_text))
                }
            }
        },
    )
}

@Preview(
    showSystemUi = true,
)
@Composable
fun GreetingPreview() {
    LanguageShakerAppTheme(dynamicColor = false) {
        Surface {
            WelcomeScreen()
        }
    }
}
