package cl.mobdev.features.mvi.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import cl.mobdev.features.mvi.presentation.user.UserViewModel
import cl.mobdev.features.mvi.ui.listuser.UserIntentHandler
import cl.mobdev.features.mvi.ui.listuser.UserScreen
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
internal fun NavGraphBuilder.userNav(
    viewModel: UserViewModel,
    intentHandler: UserIntentHandler,
) = composable(
    route = POCRoutes.User.path,
) {

    val userUiState = remember {
        viewModel.uiState()
    }.collectAsState(initial = viewModel.defaultUiState)

    UserScreen(
        viewModel = viewModel,
        intentHandler = intentHandler,
        uiState = userUiState,
    )
}
