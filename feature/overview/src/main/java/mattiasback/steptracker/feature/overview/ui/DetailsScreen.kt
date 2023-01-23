package mattiasback.steptracker.feature.overview.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mattiasback.steptracker.core.ui.theme.StepTrackerTheme

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel<DetailsViewModelImpl>(),
    onEvent: (DetailsEvent) -> Unit = {}
) {
    val viewState by viewModel.viewState.collectAsState()

    viewState.event?.let { event ->
        LaunchedEffect(event) {
            viewModel.resetEvent()
            onEvent(event)
        }
    }

    //TODO Define some content
    DetailsContent()
}

@Composable
internal fun DetailsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        DetailsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = "Recent Activity"
        )
        DetailsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = "Goals"
        )
        DetailsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = "History"
        )
    }

}

@Composable
private fun DetailsCard(
    modifier: Modifier = Modifier,
    title: String = ""
) {
    ElevatedCard(modifier = modifier) {
        Text(text = title)
    }
}


@Preview(showBackground = false)
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OverviewPreview() {
    StepTrackerTheme {
        DetailsContent()
    }
}
