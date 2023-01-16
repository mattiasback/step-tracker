package mattiasback.steptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mattiasback.steptracker.ui.theme.StepTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    StepTrackerApp("Android")
                }
            }
        }
    }
}

@Composable
fun StepTrackerApp(name: String) {
    Text(text = "Steps: ")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StepTrackerTheme {
        StepTrackerApp("Android")
    }
}