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

@Composable
fun AlgorithmsPage(onBack: () -> Unit, navController: NavController) {
    val categories = listOf(
        NavCategory("search_sort", "Алгоритмы поиска и сортировки", Color(0xFFEC407A)),
        NavCategory("graph", "Графовые алгоритмы", Color(0xFF7E57C2))
    )

    val navItems = listOf(
        NavItem(
            id = QUICK_MERGE_SORT_SCREEN,
            title = "Быстрая сортировка, слиянием",
            description = "",
            categoryId = "search_sort",
            onClick = { navController.navigateWithHistory(QUICK_MERGE_SORT_SCREEN) }
        ),
        NavItem(
            id = "insert_bubble",
            title = "Сортировка вставками, пузырьком",
            description = "",
            categoryId = "search_sort",
            onClick = { navController.navigateWithHistory("insert_bubble") }
        ),
        NavItem(
            id = "binary_linear",
            title = "Бинарный поиск, линейный поиск",
            description = "",
            categoryId = "search_sort",
            onClick = { navController.navigateWithHistory("binary_linear") }
        ),
        NavItem(
            id = "bfs_dfs",
            title = "Поиск в ширину (BFS), в глубину (DFS)",
            description = "",
            categoryId = "graph",
            onClick = { navController.navigateWithHistory("bfs_dfs") }
        ),
        NavItem(
            id = "dijkstra_bellman_ford",
            title = "Алгоритм Дейкстры, Беллмана-Форда",
            description = "",
            categoryId = "graph",
            onClick = { navController.navigateWithHistory("dijkstra_bellman_ford") }
        ),
        NavItem(
            id = "bridges_components",
            title = "Поиск мостов, компонент связности",
            description = "",
            categoryId = "graph",
            onClick = { navController.navigateWithHistory("bridges_components") }
        ),
        NavItem(
            id = "floyd_warshall_prim",
            title = "Алгоритм Флойда-Уоршелла, Прима",
            description = "",
            categoryId = "graph",
            onClick = { navController.navigateWithHistory("floyd_warshall_prim") }
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

