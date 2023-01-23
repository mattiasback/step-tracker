package mattiasback.steptracker.feature.overview.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mattiasback.steptracker.data.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val _userRepository: UserRepository,
) : ViewModel(), DetailsViewModel {

    private val _uiState = MutableStateFlow(DetailsViewState())

    override val viewState
        get() = _uiState.asStateFlow()

    override fun resetEvent() = _uiState.update { it.copy(event = null) }

    override fun onUiEvent(event: DetailsUiEvent) {
        //TODO
        when (event) {
            else -> {}
        }
    }
}