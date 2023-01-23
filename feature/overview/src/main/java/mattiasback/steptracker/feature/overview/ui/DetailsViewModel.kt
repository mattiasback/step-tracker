package mattiasback.steptracker.feature.overview.ui

import kotlinx.coroutines.flow.StateFlow

interface DetailsViewModel {
    val viewState: StateFlow<DetailsViewState>
    fun resetEvent()
    fun onUiEvent(event: DetailsUiEvent)
}

data class DetailsViewState(
    val event: DetailsEvent? = null,
)

sealed class DetailsEvent {
    //TODO
}

sealed class DetailsUiEvent {
    //TODO
}
