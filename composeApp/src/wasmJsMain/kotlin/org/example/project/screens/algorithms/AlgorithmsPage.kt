package org.example.project.screens.algorithms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.components.NavCategory
import org.example.project.components.NavItem
import org.example.project.components.NavigationTemplate

@Composable
fun AlgorithmsPage(onBack: () -> Unit, onNavigate: (String) -> Unit) {
    val categories = listOf(
        NavCategory("search_sort", "Алгоритмы поиска и сортировки", Color(0xFF42A5F5)),
        NavCategory("graph", "Графовые алгоритмы", Color(0xFF66BB6A))
    )

    val navItems = listOf(
        NavItem(
            id = "quick_merge_sort",
            title = "Быстрая сортировка, сортировка слиянием",
            description = "",
            categoryId = "search_sort",
            onClick = { onNavigate("quick_merge_sort") }
        ),
        NavItem(
            id = "insertion_bubble_sort",
            title = "Сортировка вставками, пузырьком",
            description = "",
            categoryId = "search_sort",
            onClick = { onNavigate("insertion_bubble_sort") }
        ),
        NavItem(
            id = "binary_linear_search",
            title = "Бинарный поиск, линейный поиск",
            description = "",
            categoryId = "search_sort",
            onClick = { onNavigate("binary_linear_search") }
        ),
        NavItem(
            id = "bfs_dfs",
            title = "Поиск в ширину (BFS), в глубину (DFS)",
            description = "",
            categoryId = "graph",
            onClick = { onNavigate("bfs_dfs") }
        ),
        NavItem(
            id = "dijkstra_bellman_ford",
            title = "Алгоритм Дейкстры, Беллмана-Форда",
            description = "",
            categoryId = "graph",
            onClick = { onNavigate("dijkstra_bellman_ford") }
        ),
        NavItem(
            id = "bridges_components",
            title = "Поиск мостов, компонент связности",
            description = "",
            categoryId = "graph",
            onClick = { onNavigate("bridges_components") }
        ),
        NavItem(
            id = "floyd_warshall_prim",
            title = "Алгоритм Флойда-Уоршелла, Прима",
            description = "",
            categoryId = "graph",
            onClick = { onNavigate("floyd_warshall_prim") }
        )
    )

    NavigationTemplate(
        title = "Алгоритмы",
        categories = categories,
        items = navItems,
        onBack = onBack,
        modifier = Modifier
    )
}

