package mattiasback.steptracker.feature.overview.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mattiasback.steptracker.core.ui.theme.StepTrackerTheme

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel<OverviewViewModelImpl>(),
    onEvent: (OverviewEvent) -> Unit = {}
) {
    val viewState by viewModel.viewState.collectAsState()

    viewState.event?.let { event ->
        LaunchedEffect(event) {
            viewModel.resetEvent()
            onEvent(event)
        }
    }

    OverviewContent(
        steps = viewState.steps,
        dailyGoal = viewState.dailyGoal,
        onIndicatorClicked = { viewModel.onUiEvent(OverviewUiEvent.DetailsClicked) },
    )
}

@Composable
internal fun OverviewContent(
    modifier: Modifier = Modifier,
    onIndicatorClicked: () -> Unit = {},
    steps: Long = 0,
    dailyGoal: Long = 1
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepIndicator(
            dailyGoal = dailyGoal,
            steps = steps,
            onIndicatorClicked = onIndicatorClicked
        )
    }

}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OverviewPreview() {
    StepTrackerTheme {
        OverviewContent(
            steps = 4000,
            dailyGoal = 6000
        )
    }
}
