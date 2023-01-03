package cl.mobdev.features.mvi.presentation.listshopping

internal sealed class ListShoppingResult {
    sealed class GetListListShoppingResult : ListShoppingResult() {
        object InProgress : GetListListShoppingResult()
        data class Success(val listUser: List<String>) : GetListListShoppingResult()
        object Error : GetListListShoppingResult()
        object Empty : GetListListShoppingResult()
    }
}
