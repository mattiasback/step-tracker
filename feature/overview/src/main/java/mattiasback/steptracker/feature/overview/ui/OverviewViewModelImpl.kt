package mattiasback.steptracker.feature.overview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mattiasback.steptracker.data.user.repository.UserRepository
import javax.inject.Inject

const val DAILY_GOAL_DEFAULT = 500L

@HiltViewModel
class OverviewViewModelImpl @Inject constructor(
    _userRepository: UserRepository,
) : ViewModel(), OverviewViewModel {

    private val _uiState = MutableStateFlow(OverviewViewState())
    private val _user = _userRepository.users.map { it.firstOrNull() }

    init {
        viewModelScope.launch {
            if (_userRepository.firstUser() == null) {
                _userRepository.createEmptyUser()
            }
        }

        //TODO add step detector library instead
        //Simulate steps taken
        viewModelScope.launch {
            while (true) {
                _uiState.update {
                    it.copy(steps = it.steps.inc())
                }
                delay(200)
            }
        }
    }

    override val viewState =
        combine(
            _uiState,
            _user,
        ) { state, user ->
            state.copy(
                steps = state.steps,
                dailyGoal = DAILY_GOAL_DEFAULT
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, OverviewViewState())

    override fun resetEvent() = _uiState.update { it.copy(event = null) }

    override fun onUiEvent(event: OverviewUiEvent) {
        when (event) {
            is OverviewUiEvent.DetailsClicked -> {
                _uiState.update { it.copy(event = OverviewEvent.NavigateToDetails) }
            }
        }
    }
}