package mattiasback.steptracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mattiasback.steptracker.core.ui.theme.StepTrackerTheme
import mattiasback.steptracker.feature.overview.ui.DetailsScreen
import mattiasback.steptracker.feature.overview.ui.OverviewEvent
import mattiasback.steptracker.feature.overview.ui.OverviewScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepTrackerTheme {
                StepTrackerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepTrackerApp() {

    val navController = rememberNavController()

    //TODO split nav and add top bar
    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Overview.name,
            modifier = Modifier.padding(padding)
        ) {
            composable(
                route = Screens.Overview.name
            ) {
                OverviewScreen(
                    onEvent = { event ->
                        when (event) {
                            is OverviewEvent.NavigateToDetails -> {
                                navController.navigate(Screens.Details.name)
                            }
                        }
                    }
                )
            }
            composable(
                route = Screens.Details.name
            ) {
                DetailsScreen(
                    onEvent = { event ->
                        when (event) {
                            else -> { /*NOOP*/
                            }
                        }
                    }
                )
            }

        }
    }
}

enum class Screens {
    Overview,
    Details
}