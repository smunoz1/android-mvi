package cl.mobdev.features.mvi.presentation.listshopping

import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingResult.GetListListShoppingResult
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState.DefaultUiState
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState.DisplayListShoppingUiState
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState.EmptyUiState
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState.ErrorUiState
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState.LoadingUiState

internal class ListShoppingReducer {

    infix fun ListShoppingUiState.reduceWith(result: ListShoppingResult): ListShoppingUiState {
        return when (val previousState = this) {
            is LoadingUiState -> previousState reduceWith result
            is DisplayListShoppingUiState -> previousState reduceWith result
            is EmptyUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is DefaultUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: ListShoppingResult) = when (result) {
        is GetListListShoppingResult.Success -> this
        is GetListListShoppingResult.Error -> ErrorUiState
        is GetListListShoppingResult.Empty -> EmptyUiState
        else -> throw unsupportedReduceCase()
    }

    private infix fun DisplayListShoppingUiState.reduceWith(result: ListShoppingResult): ListShoppingUiState {
        throw unsupportedReduceCase()
    }

    private infix fun ErrorUiState.reduceWith(result: ListShoppingResult) = when (result) {
        is GetListListShoppingResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun EmptyUiState.reduceWith(result: ListShoppingResult) = when (result) {
        is GetListListShoppingResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun DefaultUiState.reduceWith(result: ListShoppingResult) = when (result) {
        is GetListListShoppingResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private fun unsupportedReduceCase() = RuntimeException()

}
