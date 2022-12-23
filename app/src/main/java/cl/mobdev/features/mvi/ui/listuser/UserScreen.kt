package cl.mobdev.features.mvi.ui.listuser

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cl.mobdev.features.mvi.presentation.user.UserUiState
import cl.mobdev.features.mvi.presentation.user.UserViewModel
import cl.mobdev.features.mvi.ui.listuser.components.DefaultComponent
import cl.mobdev.features.mvi.ui.listuser.components.EmptyComponent
import cl.mobdev.features.mvi.ui.listuser.components.ErrorComponent
import cl.mobdev.features.pocmvi.ui.listuser.components.ListUserComponent
import cl.mobdev.features.pocmvi.ui.listuser.components.LoadingComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
internal fun UserScreen(
    viewModel: UserViewModel,
    intentHandler: UserIntentHandler,
    uiState: State<UserUiState>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "POC MVI",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            )
        },
        content = {
            UserContent(
                viewModel = viewModel,
                intentHandler = intentHandler,
                uiState = uiState
            )
        }
    )
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun UserContent(
    viewModel: UserViewModel,
    intentHandler: UserIntentHandler,
    uiState: State<UserUiState>,
) {
    when (val currentState = uiState.value) {
        is UserUiState.DefaultUiState -> DefaultComponent {
            intentHandler.getListUserUIntent()
        }
        is UserUiState.DisplayUserUiState -> ListUserComponent(
            listUser = currentState.listUser
        )
        is UserUiState.EmptyUiState -> EmptyComponent {
            intentHandler.retryIntent()
        }
        is UserUiState.ErrorUiState -> ErrorComponent {
            intentHandler.retryIntent()
        }
        is UserUiState.LoadingUiState -> LoadingComponent()
    }
}
