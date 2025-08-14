package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
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
import org.example.project.screens.algorithms.*
import org.example.project.screens.algorithms.AlgorithmConstants.INSERT_BUBBLE_SORT
import org.example.project.screens.algorithms.linear_algorithms.QUICK_MERGE_SORT_SCREEN
import org.example.project.screens.algorithms.linear_algorithms.QuickMergeSortScreen
import org.example.project.screens.algorithms.linear_algorithms.TWO_POINTERS_SCREEN_GEM
import org.example.project.screens.algorithms.linear_algorithms.TWO_POINTERS_SCREEN_GROK
import org.example.project.screens.algorithms.linear_algorithms.TwoPointersScreenGem
import org.example.project.screens.algorithms.linear_algorithms.TwoPointersScreenGrok
import org.example.project.screens.calculator.CalculatorScreen
import org.example.project.screens.details.DetailsScreen
import org.example.project.screens.examples.ExamplesPage
import org.example.project.screens.home.HomeScreen
import org.w3c.dom.events.Event

@Composable
fun App() {
    MaterialTheme {
        SelectionContainer {
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
                composable("details") {
                    DetailsScreen(onNavigateBack = { navigateBack() })
                }
                composable("function") {
                    FunctionDrawer(modifier = Modifier.fillMaxSize(), onBack = { navigateBack() })
                }
                composable("examples") {
                    ExamplesPage(onBack = { navigateBack() })
                }
                composable("algorithms") {
                    AlgorithmsPage(
                        onBack = { navigateBack() },
                        pages = listOf(
                            QUICK_MERGE_SORT_SCREEN to { QuickMergeSortScreen(onBack = { navigateBack() }) },
                            TWO_POINTERS_SCREEN_GEM to { TwoPointersScreenGem(onBack = { navigateBack() }) },
                            TWO_POINTERS_SCREEN_GROK to { TwoPointersScreenGrok(onBack = { navigateBack() }) },
                            INSERT_BUBBLE_SORT to { InsertBubbleSortScreen(onBack = { navigateBack() }) }
                        ),
                        startDestination = QUICK_MERGE_SORT_SCREEN
                    )
                }

//
//                composable(AlgorithmConstants.BINARY_LINEAR_SEARCH) {
//                    BinaryLinearSearchScreen(onBack = { navigateBack() })
//                }
//                composable(AlgorithmConstants.BFS_DFS) {
//                    BfsDfsScreen(onBack = { navigateBack() })
//                }
//                composable(AlgorithmConstants.DIJKSTRA_BELLMAN_FORD) {
//                    DijkstraBellmanFordScreen(onBack = { navigateBack() })
//                }
//                composable(AlgorithmConstants.BRIDGES_COMPONENTS) {
//                    BridgesComponentsScreen(onBack = { navigateBack() })
//                }
//                composable(AlgorithmConstants.FLOYD_WARSHALL_PRIM) {
//                    FloydWarshallPrimScreen(onBack = { navigateBack() })
//                }
                // #EndRegion

                composable("calculator") {
                    CalculatorScreen(onBack = { navigateBack() })
                }
            }
        }
    }
}
