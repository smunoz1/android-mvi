package cl.mobdev.features.mvi.ui.listuser

import cl.mobdev.features.mvi.presentation.user.UserUIntent
import cl.mobdev.features.mvi.presentation.user.UserUIntent.PressingBtnGetListUserUIntent
import cl.mobdev.features.mvi.presentation.user.UserUIntent.RetryUIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class UserIntentHandler {
    private val userIntents = MutableSharedFlow<UserUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<UserUIntent> = userIntents.asSharedFlow()

    fun getListUserUIntent() {
        coroutineScope?.launch {
            userIntents.emit(PressingBtnGetListUserUIntent)
        }
    }

    fun retryIntent() {
        coroutineScope?.launch {
            userIntents.emit(RetryUIntent)
        }
    }
}
