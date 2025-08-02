package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.project.screens.FunctionDrawer
import org.example.project.screens.calculator.CalculatorScreen
import org.example.project.screens.details.DetailsScreen
import org.example.project.screens.examples.ExamplesPage
import org.example.project.screens.home.HomeScreen
import org.example.project.screens.algorithms.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
    MaterialTheme {
        // Remove column wrapper since HomeScreen now uses Scaffold
        // and has its own layout handling
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        onNavigateToDetails = { navController.navigate("details") },
                        onNavigateToFunction = { navController.navigate("function") },
                        onNavigateToExamples = { navController.navigate("examples") },
                        navController = navController
                    )
                }
                composable("algorithms") {
                    AlgorithmsPage(
                        onBack = { navController.popBackStack() },
                        onNavigate = { route -> navController.navigate(route) }
                    )
                }
                composable("quick_merge_sort") {
                    QuickMergeSortScreen(onBack = { navController.popBackStack() })
                }
                composable("insertion_bubble_sort") {
                    InsertionBubbleSortScreen(onBack = { navController.popBackStack() })
                }
                composable("binary_linear_search") {
                    BinaryLinearSearchScreen(onBack = { navController.popBackStack() })
                }
                composable("bfs_dfs") {
                    BfsDfsScreen(onBack = { navController.popBackStack() })
                }
                composable("dijkstra_bellman_ford") {
                    DijkstraBellmanFordScreen(onBack = { navController.popBackStack() })
                }
                composable("bridges_components") {
                    BridgesComponentsScreen(onBack = { navController.popBackStack() })
                }
                composable("floyd_warshall_prim") {
                    FloydWarshallPrimScreen(onBack = { navController.popBackStack() })
                }
                composable("details") {
                    DetailsScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable("function") {
                    FunctionDrawer(modifier = Modifier.fillMaxSize(), onBack = { navController.popBackStack() })
                }
                composable("examples") {
                    ExamplesPage(onBack = { navController.popBackStack() })
                }
                composable("calculator") {
                    CalculatorScreen(onBack = { navController.popBackStack() })
                }
            }
    }
}
