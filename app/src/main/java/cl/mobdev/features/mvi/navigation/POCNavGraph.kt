package cl.mobdev.features.mvi.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cl.mobdev.features.mvi.presentation.user.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.mobdev.features.mvi.ui.listuser.UserIntentHandler
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun MviNavGraph(
    startDestination: String = POCRoutes.User.path,
) {
    val navController = rememberAnimatedNavController()
    val coroutine = rememberCoroutineScope()
    val userViewModel: UserViewModel = viewModel()
    val userIntentHandler = UserIntentHandler().apply {
        this.coroutineScope = coroutine
    }
    userViewModel.processUserIntentsAndObserveUiStates(userIntentHandler.userIntents())

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        userNav(
            viewModel = userViewModel,
            intentHandler = userIntentHandler
        )
    }
}
