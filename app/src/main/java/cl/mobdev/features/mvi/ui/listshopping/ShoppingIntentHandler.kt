package cl.mobdev.features.mvi.ui.listshopping

import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.PressingBtnAddItemListShoppingUIntent
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.PressingBtnGetListListShoppingUIntent
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUIntent.RetryUIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ShoppingIntentHandler {
    private val shoppingIntents = MutableSharedFlow<ListShoppingUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<ListShoppingUIntent> = shoppingIntents.asSharedFlow()

    fun getListShoppingUIntent() {
        coroutineScope?.launch {
            shoppingIntents.emit(PressingBtnGetListListShoppingUIntent)
        }
    }

    fun retryIntent() {
        coroutineScope?.launch {
            shoppingIntents.emit(RetryUIntent)
        }
    }

    fun addItemShoppingUIntent(itemShopping: String) {
        coroutineScope?.launch {
            shoppingIntents.emit(PressingBtnAddItemListShoppingUIntent(
                itemShopping = itemShopping
            ))
        }
    }
}
