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

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val _userRepository: UserRepository,
) : ViewModel(), DetailsViewModel {

    private val _uiState = MutableStateFlow(DetailsViewState())
    private val _user = _userRepository.users.map { it.first() }

    private var userId: Long = 0

    override val viewState =
        combine(
            _uiState,
            _user,
        ) { state, user ->
            userId = user.userId
            state.copy(
                steps = user.steps,
                dailyGoal = user.dailyGoal
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, DetailsViewState())

    override fun resetEvent() = _uiState.update { it.copy(event = null) }

    override fun onUiEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.SelectDailyGoal -> {
                viewModelScope.launch {
                    _userRepository.updateDailyGoal(
                        userId = userId,
                        dailyGoal = event.dailyGoal
                    )
                }
            }
        }
    }
}