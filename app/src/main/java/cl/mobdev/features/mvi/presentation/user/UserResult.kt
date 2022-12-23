package cl.mobdev.features.mvi.presentation.user

internal sealed class UserResult {
    sealed class GetListUserResult : UserResult() {
        object InProgress : GetListUserResult()
        data class Success(val listUser: List<String>) : GetListUserResult()
        object Error : GetListUserResult()
        object Empty : GetListUserResult()
    }
}
