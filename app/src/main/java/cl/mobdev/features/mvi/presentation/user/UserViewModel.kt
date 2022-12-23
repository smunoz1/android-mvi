package cl.mobdev.features.mvi.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

@FlowPreview
@ExperimentalCoroutinesApi
internal class UserViewModel : ViewModel() {
    private val reducer = UserReducer()
    private val processor = UserProcessor()

    val defaultUiState: UserUiState = UserUiState.DefaultUiState
    private val uiState: MutableStateFlow<UserUiState> = MutableStateFlow(defaultUiState)

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<UserUIntent>,
        coroutineScope: CoroutineScope = viewModelScope,
    ) {
        userIntents.buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .scan(defaultUiState) { previousUiState, result ->
                with(reducer) { previousUiState reduceWith result }
            }
            .onEach {
                uiState.value = it
            }
            .launchIn(coroutineScope)
    }

    private fun UserUIntent.toAction(): UserAction {
        return when (this) {
            UserUIntent.PressingBtnGetListUserUIntent -> UserAction.GetListUserAction
            UserUIntent.RetryUIntent -> UserAction.GetListUserAction
        }
    }

    fun uiState(): StateFlow<UserUiState> = uiState
}
