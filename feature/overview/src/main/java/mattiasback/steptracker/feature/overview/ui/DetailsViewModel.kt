package mattiasback.steptracker.feature.overview.ui

import kotlinx.coroutines.flow.StateFlow

interface DetailsViewModel {
    val viewState: StateFlow<DetailsViewState>
    fun resetEvent()
    fun onUiEvent(event: DetailsUiEvent)
}

data class DetailsViewState(
    val event: DetailsEvent? = null,
    val steps: Long = 0,
    val dailyGoal: Long? = null
)

sealed class DetailsEvent {
    object NavigateToX : DetailsEvent()
}

sealed class DetailsUiEvent {
    data class SelectDailyGoal(val dailyGoal: Long) : DetailsUiEvent()
}
