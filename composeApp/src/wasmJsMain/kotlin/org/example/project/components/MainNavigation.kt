package org.example.project.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
