package cl.mobdev.features.mvi.presentation.listshopping

internal sealed class ListShoppingUiState {
    object DefaultUiState : ListShoppingUiState()
    object LoadingUiState : ListShoppingUiState()
    data class DisplayListShoppingUiState(
        val listShopping: List<String>,
    ) : ListShoppingUiState()
    object ErrorUiState : ListShoppingUiState()
    object EmptyUiState : ListShoppingUiState()
}
