package org.example.project.screens.algorithms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import org.example.project.components.NavCategory
import org.example.project.components.NavItem
import org.example.project.components.NavigationTemplate
import org.example.project.navigation.navigateWithHistory
import org.example.project.screens.algorithms.linear_algorithms.QUICK_MERGE_SORT_SCREEN

object AlgorithmConstants {
    const val SEARCH_SORT = "search_sort"
    const val GRAPH = "graph"

    const val INSERT_BUBBLE_SORT = "insert_bubble"
    const val BINARY_LINEAR_SEARCH = "binary_linear"
    const val BFS_DFS = "bfs_dfs"
    const val DIJKSTRA_BELLMAN_FORD = "dijkstra_bellman_ford"
    const val BRIDGES_COMPONENTS = "bridges_components"
    const val FLOYD_WARSHALL_PRIM = "floyd_warshall_prim"
}

@Composable
fun AlgorithmsPage(onBack: () -> Unit, navController: NavController) {
    val categories = listOf(
        NavCategory(AlgorithmConstants.SEARCH_SORT, "Линейные алгоритмы", Color(0xFFEC407A)),
        NavCategory(AlgorithmConstants.GRAPH, "Графовые алгоритмы", Color(0xFF7E57C2))
    )

    val navItems = listOf(
        NavItem(
            id = QUICK_MERGE_SORT_SCREEN,
            title = "Быстрая сортировка, слиянием",
            description = "",
            categoryId = AlgorithmConstants.SEARCH_SORT,
            onClick = { navController.navigateWithHistory(QUICK_MERGE_SORT_SCREEN) }
        ),
        NavItem(
            id = AlgorithmConstants.INSERT_BUBBLE_SORT,
            title = "Сортировка вставками, пузырьком",
            description = "",
            categoryId = AlgorithmConstants.SEARCH_SORT,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.INSERT_BUBBLE_SORT) }
        ),
        NavItem(
            id = AlgorithmConstants.BINARY_LINEAR_SEARCH,
            title = "Бинарный поиск, линейный поиск",
            description = "",
            categoryId = AlgorithmConstants.SEARCH_SORT,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.BINARY_LINEAR_SEARCH) }
        ),
        NavItem(
            id = AlgorithmConstants.BFS_DFS,
            title = "Поиск в ширину (BFS), в глубину (DFS)",
            description = "",
            categoryId = AlgorithmConstants.GRAPH,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.BFS_DFS) }
        ),
        NavItem(
            id = AlgorithmConstants.DIJKSTRA_BELLMAN_FORD,
            title = "Алгоритм Дейкстры, Беллмана-Форда",
            description = "",
            categoryId = AlgorithmConstants.GRAPH,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.DIJKSTRA_BELLMAN_FORD) }
        ),
        NavItem(
            id = AlgorithmConstants.BRIDGES_COMPONENTS,
            title = "Поиск мостов, компонент связности",
            description = "",
            categoryId = AlgorithmConstants.GRAPH,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.BRIDGES_COMPONENTS) }
        ),
        NavItem(
            id = AlgorithmConstants.FLOYD_WARSHALL_PRIM,
            title = "Алгоритм Флойда-Уоршелла, Прима",
            description = "",
            categoryId = AlgorithmConstants.GRAPH,
            onClick = { navController.navigateWithHistory(AlgorithmConstants.FLOYD_WARSHALL_PRIM) }
        )
    )

    NavigationTemplate(
        title = "Алгоритмы",
        categories = categories,
        items = navItems,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlgorithmPlaceholderScreen(title: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Содержимое появится позже")
        }
    }
}

@Composable
fun InsertBubbleSortScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Сортировка вставками, пузырьком", onBack)

@Composable
fun BinaryLinearSearchScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Бинарный поиск, линейный поиск", onBack)

@Composable
fun BfsDfsScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Поиск в ширину (BFS), в глубину (DFS)", onBack)

@Composable
fun DijkstraBellmanFordScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Алгоритм Дейкстры, Беллмана-Форда", onBack)

@Composable
fun BridgesComponentsScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Поиск мостов, компонент связности", onBack)

@Composable
fun FloydWarshallPrimScreen(onBack: () -> Unit) = AlgorithmPlaceholderScreen("Алгоритм Флойда-Уоршелла, Прима", onBack)

