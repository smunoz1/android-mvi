package cl.mobdev.features.mvi.presentation.listshopping

import cl.mobdev.features.mvi.data.POCDataRepository
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingResult.GetListListShoppingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

internal class ListShoppingProcessor {
    private val repository = POCDataRepository()

    fun actionProcessor(actions: ListShoppingAction): Flow<ListShoppingResult> =
        when (actions) {
            ListShoppingAction.GetListListShoppingAction -> loadListShoppingProcessor()
        }

    private fun loadListShoppingProcessor(): Flow<GetListListShoppingResult> =
        repository.getListShopping()
            .map { list ->
                if (list.isEmpty()) {
                    GetListListShoppingResult.Empty
                } else {
                    GetListListShoppingResult.Success(list)
                }
            }.onStart {
                emit(GetListListShoppingResult.InProgress)
            }.catch {
                emit(GetListListShoppingResult.Error)
            }.flowOn(Dispatchers.IO)
}
