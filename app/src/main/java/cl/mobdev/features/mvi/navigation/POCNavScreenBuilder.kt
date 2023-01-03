package cl.mobdev.features.mvi.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingViewModel
import cl.mobdev.features.mvi.ui.listshopping.ShoppingIntentHandler
import cl.mobdev.features.mvi.ui.listshopping.ListShoppingScreen
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
internal fun NavGraphBuilder.userNav(
    viewModel: ListShoppingViewModel,
    intentHandler: ShoppingIntentHandler,
) = composable(
    route = POCRoutes.User.path,
) {

    val userUiState = remember {
        viewModel.uiState()
    }.collectAsState(initial = viewModel.defaultUiState)

    ListShoppingScreen(
        intentHandler = intentHandler,
        uiState = userUiState,
    )
}
