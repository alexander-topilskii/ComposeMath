package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.browser.window
import org.example.project.navigation.navigateBack
import org.example.project.navigation.navigateWithHistory
import org.example.project.screens.FunctionDrawer
import org.example.project.screens.calculator.CalculatorScreen
import org.example.project.screens.details.DetailsScreen
import org.example.project.screens.examples.ExamplesPage
import org.example.project.screens.home.HomeScreen
import org.example.project.screens.algorithms.*
import org.w3c.dom.events.Event

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        DisposableEffect(navController) {
            val handler: (Event) -> Unit = {
                navController.popBackStack()
            }
            window.addEventListener("popstate", handler)
            onDispose {
                window.removeEventListener("popstate", handler)
            }
        }

        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onNavigateToDetails = { navController.navigateWithHistory("details") },
                    onNavigateToFunction = { navController.navigateWithHistory("function") },
                    onNavigateToExamples = { navController.navigateWithHistory("examples") },
                    navController = navController
                )
            }
            composable("algorithms") {
                AlgorithmsPage(
                    onBack = { navigateBack() },
                    onNavigate = { route -> navController.navigateWithHistory(route) }
                )
            }
            composable("quick_merge_sort") {
                QuickMergeSortScreen(onBack = { navigateBack() })
            }
            composable("insertion_bubble_sort") {
                InsertionBubbleSortScreen(onBack = { navigateBack() })
            }
            composable("binary_linear_search") {
                BinaryLinearSearchScreen(onBack = { navigateBack() })
            }
            composable("bfs_dfs") {
                BfsDfsScreen(onBack = { navigateBack() })
            }
            composable("dijkstra_bellman_ford") {
                DijkstraBellmanFordScreen(onBack = { navigateBack() })
            }
            composable("bridges_components") {
                BridgesComponentsScreen(onBack = { navigateBack() })
            }
            composable("floyd_warshall_prim") {
                FloydWarshallPrimScreen(onBack = { navigateBack() })
            }
            composable("details") {
                DetailsScreen(onNavigateBack = { navigateBack() })
            }
            composable("function") {
                FunctionDrawer(modifier = Modifier.fillMaxSize(), onBack = { navigateBack() })
            }
            composable("examples") {
                ExamplesPage(onBack = { navigateBack() })
            }
            composable("calculator") {
                CalculatorScreen(onBack = { navigateBack() })
            }
        }
    }
}

