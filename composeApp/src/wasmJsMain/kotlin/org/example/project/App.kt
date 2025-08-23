package org.example.project

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import kotlinx.browser.window
import org.example.project.components.NavCategory
import org.example.project.components.NavItem
import org.example.project.compression.CompressionScreen
import org.example.project.navigation.navigateBack
import org.example.project.screens.algorithms.NavPagePage
import org.example.project.screens.algorithms.linear_algorithms.QuickMergeSortScreen
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

            val categorymain = NavCategory("mainpage", "MainPage", Color(0xFFEC407A))

            NavPagePage(
                onBack = { navigateBack() },
                pages = listOf(
                    NavItem(
                        id = "algos",
                        title = "Algos",
                        description = "Algos…",
                        category = categorymain,
                        pages = listOf(
                            NavItem(
                                id = "QuickMergeSortScreen",
                                title = "QuickMergeSortScreen",
                                description = "QuickMergeSortScreen…",
                                category = categorymain,
                                page = { QuickMergeSortScreen(onBack = { navigateBack() }) }
                            )
                        )
                    ),
                    NavItem(
                        id = "compression",
                        title = "Compression",
                        description = "Compression…",
                        category = categorymain,
                        pages = listOf(
                            NavItem(
                                id = "CompressionScreen",
                                title = "Compression Algos",
                                description = "…",
                                category = categorymain,
                                page = { CompressionScreen(onBack = { navigateBack() }) }
                            )
                        )
                    )
                ),
            )
        }
    }
}
