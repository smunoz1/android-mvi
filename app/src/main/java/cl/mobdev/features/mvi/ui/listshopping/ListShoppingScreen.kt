package cl.mobdev.features.mvi.ui.listshopping

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import cl.mobdev.features.mvi.R
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingUiState
import cl.mobdev.features.mvi.ui.listshopping.components.DefaultComponent
import cl.mobdev.features.mvi.ui.listshopping.components.EmptyComponent
import cl.mobdev.features.mvi.ui.listshopping.components.ErrorComponent
import cl.mobdev.features.mvi.ui.listshopping.components.ListShoppingComponent
import cl.mobdev.features.mvi.ui.listshopping.components.LoadingComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
internal fun ListShoppingScreen(
    intentHandler: ShoppingIntentHandler,
    uiState: State<ListShoppingUiState>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            )
        },
        content = {
            ListShoppingContent(
                intentHandler = intentHandler,
                uiState = uiState
            )
        }
    )
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
private fun ListShoppingContent(
    intentHandler: ShoppingIntentHandler,
    uiState: State<ListShoppingUiState>,
) {
    when (val currentState = uiState.value) {
        is ListShoppingUiState.DefaultUiState -> DefaultComponent {
            intentHandler.getListShoppingUIntent()
        }
        is ListShoppingUiState.DisplayListShoppingUiState -> ListShoppingComponent(
            listShopping = currentState.listShopping,
            intentHandler = intentHandler
        )
        is ListShoppingUiState.EmptyUiState -> EmptyComponent {
            intentHandler.retryIntent()
        }
        is ListShoppingUiState.ErrorUiState -> ErrorComponent {
            intentHandler.retryIntent()
        }
        ListShoppingUiState.LoadingUiState -> LoadingComponent()
    }
}
