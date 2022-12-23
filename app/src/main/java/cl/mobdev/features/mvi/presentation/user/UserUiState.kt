package cl.mobdev.features.mvi.presentation.user

internal sealed class UserUiState {
    object DefaultUiState : UserUiState()
    object LoadingUiState : UserUiState()
    data class DisplayUserUiState(
        val listUser: List<String>,
    ) : UserUiState()
    object ErrorUiState : UserUiState()
    object EmptyUiState : UserUiState()
}
