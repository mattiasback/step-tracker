package mattiasback.steptracker.feature.overview.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mattiasback.steptracker.core.ui.theme.StepTrackerTheme

@Composable
internal fun StepIndicator(
    modifier: Modifier = Modifier,
    steps: Long = 0,
    dailyGoal: Long = 1,
    onIndicatorClicked: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onIndicatorClicked() },
        contentAlignment = Alignment.Center
    ) {
        val strokeWidth = 32.dp
        Text(
            modifier = modifier.padding(horizontal = strokeWidth + 8.dp),
            text = steps.toString(),
            style = MaterialTheme.typography.displayLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        ProgressBar(
            modifier = Modifier.size(240.dp),
            current = steps.toFloat(),
            maximum = dailyGoal.toFloat(),
            strokeWidth = strokeWidth
        )
    }

}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier,
    maximum: Float = 1f,
    current: Float = 0f,
    strokeWidth: Dp = 1.dp
) {
    val progress by remember(current, maximum) {
        mutableStateOf(
            if (maximum == 0f) 1f else java.lang.Float.min((current / maximum), 1f)
        )
    }

    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    CircularProgressIndicator(
        modifier = modifier,
        progress = animatedProgress,
        strokeWidth = strokeWidth,
        strokeCap = StrokeCap.Round,
        trackColor = MaterialTheme.colorScheme.inversePrimary
    )
}

@Preview(showBackground = true)
@Composable
private fun StepIndicatorPreview() {
    StepTrackerTheme(darkTheme = false) {
        StepIndicator(
            steps = 4000,
            dailyGoal = 6000
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StepIndicatorDarkPreview() {
    StepTrackerTheme(darkTheme = true) {
        StepIndicator(
            steps = 99999,
            dailyGoal = 200000
        )
    }
}
