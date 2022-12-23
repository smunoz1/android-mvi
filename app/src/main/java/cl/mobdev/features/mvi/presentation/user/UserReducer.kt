package cl.mobdev.features.mvi.presentation.user

import cl.mobdev.features.mvi.presentation.user.UserUiState.DefaultUiState
import cl.mobdev.features.mvi.presentation.user.UserUiState.DisplayUserUiState
import cl.mobdev.features.mvi.presentation.user.UserUiState.EmptyUiState
import cl.mobdev.features.mvi.presentation.user.UserUiState.ErrorUiState
import cl.mobdev.features.mvi.presentation.user.UserUiState.LoadingUiState

internal class UserReducer {

    infix fun UserUiState.reduceWith(result: UserResult): UserUiState {
        return when (val previousState = this) {
            is LoadingUiState -> previousState reduceWith result
            is DisplayUserUiState -> previousState reduceWith result
            is EmptyUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is DefaultUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        is UserResult.GetListUserResult.Success -> DisplayUserUiState(
            listUser = result.listUser
        )
        is UserResult.GetListUserResult.Error -> ErrorUiState
        is UserResult.GetListUserResult.Empty -> EmptyUiState
    }

    private infix fun DisplayUserUiState.reduceWith(result: UserResult) =
        when (result) {
            is UserResult.GetListUserResult.InProgress -> LoadingUiState
            else -> throw unsupportedReduceCase()
        }

    private infix fun ErrorUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun EmptyUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }
    private infix fun DefaultUiState.reduceWith(result: UserResult) = when (result) {
        is UserResult.GetListUserResult.InProgress -> LoadingUiState
        else -> throw unsupportedReduceCase()
    }

    private fun unsupportedReduceCase() = RuntimeException()

}
