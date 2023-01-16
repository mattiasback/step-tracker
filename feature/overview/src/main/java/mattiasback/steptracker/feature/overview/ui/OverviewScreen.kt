package mattiasback.steptracker.feature.overview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import mattiasback.steptracker.feature.overview.R

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
        onUiEvent = viewModel::onUiEvent,
        steps = viewState.steps
    )
}

@Composable
internal fun OverviewContent(
    modifier: Modifier = Modifier,
    onUiEvent: (OverviewUiEvent) -> Unit = {},
    steps: Long = 0
) {
    Column(modifier = modifier) {
        Text(
            modifier = modifier.clickable {
                onUiEvent(OverviewUiEvent.DetailsClicked)
            },
            text = stringResource(R.string.overview_screen_steps, steps)
        )
    }
}