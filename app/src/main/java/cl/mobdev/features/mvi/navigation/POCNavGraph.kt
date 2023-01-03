package cl.mobdev.features.mvi.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cl.mobdev.features.mvi.presentation.listshopping.ListShoppingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.mobdev.features.mvi.ui.listshopping.ShoppingIntentHandler
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
    val listShoppingViewModel: ListShoppingViewModel = viewModel()
    val shoppingIntentHandler = ShoppingIntentHandler().apply {
        this.coroutineScope = coroutine
    }
    listShoppingViewModel.processUserIntentsAndObserveUiStates(shoppingIntentHandler.userIntents())

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        userNav(
            viewModel = listShoppingViewModel,
            intentHandler = shoppingIntentHandler
        )
    }
}
