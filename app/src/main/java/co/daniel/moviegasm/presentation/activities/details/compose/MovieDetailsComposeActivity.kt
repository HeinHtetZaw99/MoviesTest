package co.daniel.moviegasm.presentation.activities.details.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Created by HeinHtetZaw on 26/06/2021.
 */
class MovieDetailsComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DefaultText("Compose Testing")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsComposePreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background) {
            DefaultText("Compose Testing")
        }
    }
}