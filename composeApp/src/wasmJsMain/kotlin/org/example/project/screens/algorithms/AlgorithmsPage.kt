package org.example.project.screens.algorithms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.components.NavItem
import org.example.project.components.NavigationTemplate

object AlgorithmConstants {
    const val LINEAR_ALGORITHMS_CATEGORY = "linear algorithms"
    const val GRAPH = "graph"

}

@Composable
fun NavPagePage(onBack: () -> Unit, pages: List<NavItem>, startDestination: String? = null) {
    val navController = rememberNavController()

    val categories = pages.distinctBy { it.category }.map { it.category }

    NavigationTemplate(
        title = "Алгоритмы",
        categories = categories,
        items = pages,
        onBack = onBack,
        selectedContent = {
            NavHost(navController = navController, startDestination = startDestination?: pages.first().id) {
                pages.forEach { page ->
                    composable(page.id) {
                        page.page()
                    }
                }
            }
        },
        navController = navController
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
            Text("Содержимое появится позже: $title")
        }
    }
}
