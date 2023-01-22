package mattiasback.steptracker.feature.overview.ui

import kotlinx.coroutines.flow.StateFlow

interface OverviewViewModel {
    val viewState: StateFlow<OverviewViewState>
    fun resetEvent()
    fun onUiEvent(event: OverviewUiEvent)
}

data class OverviewViewState(
    val event: OverviewEvent? = null,
    val steps: Long = 0,
    val dailyGoal: Long = 0
)

sealed class OverviewEvent {
    object NavigateToDetails : OverviewEvent()
}

sealed class OverviewUiEvent {
    object DetailsClicked : OverviewUiEvent()
}
