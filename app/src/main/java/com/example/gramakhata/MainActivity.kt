package com.example.gramakhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gramakhata.ui.screens.customer.CustomerProfileScreen
import com.example.gramakhata.ui.screens.customer.OnboardCustomerScreen
import com.example.gramakhata.ui.screens.dashboard.DashboardScreen
import com.example.gramakhata.ui.screens.dashboard.DashboardViewModel
import com.example.gramakhata.ui.screens.splash.SplashScreen
import com.example.gramakhata.ui.theme.GramaKhataTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.remember
import com.example.gramakhata.ui.screens.auth.LoginScreen
import com.example.gramakhata.ui.screens.auth.SignupScreen
import com.example.gramakhata.ui.screens.auth.ProfileScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GramaKhataTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(
                                onNavigate = { destination ->
                                    navController.navigate(destination) {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onNavigateToDashboard = {
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToSignup = {
                                    navController.navigate("signup")
                                }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true } // Clear entire backstack
                                    }
                                },
                                onNavigateToSignup = {
                                    navController.navigate("signup") {
                                        popUpTo(0) { inclusive = true } // Clear entire backstack
                                    }
                                }
                            )
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                onNavigateToCustomer = { customerId ->
                                    navController.navigate("customer/$customerId")
                                },
                                onNavigateToAddCustomer = {
                                    navController.navigate("onboard")
                                },
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onNavigateToSignup = {
                                    navController.navigate("signup") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("onboard") {
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry("dashboard")
                            }
                            val dashboardViewModel: DashboardViewModel = hiltViewModel(parentEntry)
                            OnboardCustomerScreen(
                                onBackClick = { navController.popBackStack() },
                                onSaveCustomer = { name, phone, photoUri ->
                                    dashboardViewModel.addCustomer(name, phone, photoUri)
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = "customer/{customerId}",
                            arguments = listOf(navArgument("customerId") { type = NavType.LongType })
                        ) {
                            CustomerProfileScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
