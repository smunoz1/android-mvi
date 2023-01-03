package cl.mobdev.features.mvi.presentation.listshopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingAction.AddItemListShoppingAction
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingAction.GetListListShoppingAction
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.PressingBtnAddItemListShoppingUIntent
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.PressingBtnGetListListShoppingUIntent
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.RetryUIntent
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
internal class ListShoppingViewModel : ViewModel() {
    private val reducer = ListShoppingReducer()
    private val processor = ListShoppingProcessor()

    val defaultUiState: ListShoppingUiState = ListShoppingUiState.DefaultUiState
    private val uiState: MutableStateFlow<ListShoppingUiState> = MutableStateFlow(defaultUiState)

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<ListShoppingUIntent>,
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

    private fun ListShoppingUIntent.toAction(): ListShoppingAction {
        return when (this) {
            is PressingBtnGetListListShoppingUIntent -> GetListListShoppingAction
            is RetryUIntent -> GetListListShoppingAction
            is PressingBtnAddItemListShoppingUIntent -> AddItemListShoppingAction(this.itemShopping)
        }
    }

    fun uiState(): StateFlow<ListShoppingUiState> = uiState
}
