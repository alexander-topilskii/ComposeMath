package org.example.project.screens.algorithms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun QuickMergeSortScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Быстрая сортировка, сортировка слиянием", onBack)
}

@Composable
fun InsertionBubbleSortScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Сортировка вставками, пузырьком", onBack)
}

@Composable
fun BinaryLinearSearchScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Бинарный поиск, линейный поиск", onBack)
}

@Composable
fun BfsDfsScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Поиск в ширину (BFS), в глубину (DFS)", onBack)
}

@Composable
fun DijkstraBellmanFordScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Алгоритм Дейкстры, Беллмана-Форда", onBack)
}

@Composable
fun BridgesComponentsScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Поиск мостов, компонент связности", onBack)
}

@Composable
fun FloydWarshallPrimScreen(onBack: () -> Unit) {
    AlgorithmPlaceholderScreen("Алгоритм Флойда-Уоршелла, Прима", onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlgorithmPlaceholderScreen(title: String, onBack: () -> Unit) {
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
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Страница в разработке")
        }
    }
}

