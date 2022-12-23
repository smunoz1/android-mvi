package cl.mobdev.features.mvi.presentation.user

internal sealed class UserUIntent {
    object PressingBtnGetListUserUIntent : UserUIntent()
    object RetryUIntent : UserUIntent()
}
