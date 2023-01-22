package mattiasback.steptracker.feature.overview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mattiasback.steptracker.data.user.repository.UserRepository
import javax.inject.Inject

const val DAILY_GOAL_DEFAULT = 6000L

@HiltViewModel
class OverviewViewModelImpl @Inject constructor(
    _userRepository: UserRepository,
) : ViewModel(), OverviewViewModel {

    private val _uiState = MutableStateFlow(OverviewViewState())
    private val _user = _userRepository.users.map { it.firstOrNull() }

    init {
        viewModelScope.launch {
            _userRepository.createEmptyUser()
        }
    }

    override val viewState =
        combine(
            _uiState,
            _user,
        ) { state, user ->
            state.copy(
                steps = user?.steps ?: 0,
                dailyGoal = user?.dailyGoal ?: DAILY_GOAL_DEFAULT
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