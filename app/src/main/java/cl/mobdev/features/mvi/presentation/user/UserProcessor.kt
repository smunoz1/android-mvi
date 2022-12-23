package cl.mobdev.features.mvi.presentation.user

import cl.mobdev.features.mvi.data.POCDataRepository
import cl.mobdev.features.mvi.presentation.user.UserResult.GetListUserResult.Empty
import cl.mobdev.features.mvi.presentation.user.UserResult.GetListUserResult.Error
import cl.mobdev.features.mvi.presentation.user.UserResult.GetListUserResult.InProgress
import cl.mobdev.features.mvi.presentation.user.UserResult.GetListUserResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

internal class UserProcessor {
    private val repository = POCDataRepository()

    fun actionProcessor(actions: UserAction): Flow<UserResult> =
        when (actions) {
            UserAction.GetListUserAction -> loadListUserProcessor()
        }

    private fun loadListUserProcessor(): Flow<UserResult.GetListUserResult> =
        repository.getListUser()
            .map { list ->
                if (list.isEmpty()) {
                    Empty
                } else {
                    Success(list)
                }
            }.onStart {
                emit(InProgress)
            }.catch {
                emit(Error)
            }.flowOn(Dispatchers.IO)
}
