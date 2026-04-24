package com.chelo.splitmouse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chelo.splitmouse.ui.screens.EventDetailScreen
import com.chelo.splitmouse.ui.screens.MainScreen
import com.chelo.splitmouse.ui.screens.components.SettlementScreen
import com.chelo.splitmouse.viewmodel.EventDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {

    NavHost(navController, startDestination = Home) {


        composable<Home> {
            MainScreen(navigateToDetail = {
                navController.navigate(EventDetail(it))
            })
        }

        composable<EventDetail> { backStackEntry ->
            val eventDetails: EventDetail = backStackEntry.toRoute()
            val vm: EventDetailViewModel = koinViewModel { parametersOf(eventDetails.eventId) }
            EventDetailScreen(
                onBack = {
                    navController.navigate(Home) {
                        popUpTo(Home) {
                            inclusive = true
                        }
                    }
                },
                navToDebt = {eventId ->
                    navController.navigate(DebtDetail(eventId)){
                        popUpTo(Home){
                            inclusive = true
                        }
                    }
                },
                viewModel = vm
            )
        }


        composable<DebtDetail> { backStackEntry ->
            val eventDetails: EventDetail = backStackEntry.toRoute()
            val vm: EventDetailViewModel = koinViewModel { parametersOf(eventDetails.eventId) }
            SettlementScreen(
                onBack = {
                    navController.navigate(Home) {
                        popUpTo(Home) {
                            inclusive = true
                        }
                    }
                },
                vm

            )
        }
    }
}
