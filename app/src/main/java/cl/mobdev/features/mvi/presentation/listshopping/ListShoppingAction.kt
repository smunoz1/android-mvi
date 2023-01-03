package cl.mobdev.features.mvi.presentation.listshopping

internal sealed class ListShoppingAction {
    object GetListListShoppingAction : ListShoppingAction()
    data class AddItemListShoppingAction(val itemShopping: String) : ListShoppingAction()
}
