package mattiasback.steptracker.feature.overview.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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

    DetailsContent(
        dailyGoal = viewState.dailyGoal,
        onDailyGoalSelected = { goal -> viewModel.onUiEvent(DetailsUiEvent.SelectDailyGoal(dailyGoal = goal)) },
    )
}

@Composable
internal fun DetailsContent(
    modifier: Modifier = Modifier,
    dailyGoal: Long? = null,
    onDailyGoalSelected: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SettingsCard(
            modifier = Modifier.fillMaxWidth(),
            value = dailyGoal.toString(),
            onCommitValue = { onDailyGoalSelected(it.toLong()) }
        )
    }

}

@Composable
private fun SettingsCard(
    modifier: Modifier = Modifier,
    value: String = "",
    onCommitValue: (String) -> Unit
) {
    ElevatedCard(modifier = modifier) {
        SettingsTextField(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = value,
            onCommitValue = onCommitValue,
            keyboard = KeyboardType.Decimal,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    value: String = "",
    onCommitValue: (String) -> Unit,
    keyboard: KeyboardType = KeyboardType.Text
) {
    var input by remember(value) { mutableStateOf(value) }
    var textFieldFocused by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                textFieldFocused = it.isFocused
                if (!textFieldFocused) {
                    onCommitValue(input)
                }
            },
        value = input,
        maxLines = 1,
        onValueChange = { input = it },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboard,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onCommitValue(input)
            }
        )
    )

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OverviewPreview() {
    StepTrackerTheme {
        DetailsContent(
            dailyGoal = 6000
        )
    }
}
