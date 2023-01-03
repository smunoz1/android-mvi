package cl.mobdev.features.mvi.presentation.listshopping

internal sealed class ListShoppingUIntent {
    object PressingBtnGetListListShoppingUIntent : ListShoppingUIntent()
    object RetryUIntent : ListShoppingUIntent()
}
