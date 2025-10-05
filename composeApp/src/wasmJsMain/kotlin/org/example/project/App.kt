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
import org.example.project.components.NavPagePage
import org.example.project.compression.CompressionRLE
import org.example.project.navigation.navigateBack
import org.example.project.navigation.navList
import org.example.project.numeric_methods.LinearMethodScreen
import org.example.project.screens.algorithms.linear_algorithms.QuickMergeSortScreen
import org.w3c.dom.events.Event

@Composable
fun App() {
    MaterialTheme {
        SelectionContainer {
            setupNavigation()

            val algorytmsCategory = NavCategory("algorithms", "Algorithms", Color(0xFFEC407A))
            val compressionCategory = NavCategory("compression", "Compression", Color(0xFF42A5F5))
            val numericMethodsCategory = NavCategory("Numeric Methods", "Численные методы", Color(0xFF42A5F5))


            NavPagePage(
                title = "Main page",
                onBack = { navigateBack() },
                pages = navList {
                    category(
                        category = numericMethodsCategory,
                        id = "Numeric Methods",
                        title = "Numeric Methods",
                        description = "https://youtu.be/8JVHfRv7fyo?si=KMluYhO6rLgTWcvy"
                    ) {
                        leaf(
                            id = "Numeric Methods",
                            title = "Численные методы",
                            description = "https://youtu.be/8JVHfRv7fyo?si=KMluYhO6rLgTWcvy",
                            page = { LinearMethodScreen(onBack = { navigateBack() }) }
                        )
                    }

                    category(
                        category = algorytmsCategory,
                        id = "algos",
                        title = "Algos",
                        description = "Algos…"
                    ) {
                        leaf(
                            id = "QuickMergeSortScreen",
                            title = "QuickMergeSortScreen",
                            description = "QuickMergeSortScreen…",
                            page = { QuickMergeSortScreen(onBack = { navigateBack() }) }
                        )
                    }

                    category(
                        category = compressionCategory,
                        id = "compression",
                        title = "Compression",
                        description = "Compression…"
                    ) {
                        leaf(
                            id = "CompressionScreen",
                            title = "Compression Algos",
                            description = "…",
                            page = { CompressionScreen(onBack = { navigateBack() }) }
                        )
                        leaf(
                            id = "compression_rle",
                            title = "Run-Length Encoding (RLE)",
                            description = "Run-Length Encoding (RLE) compression method the most simplest way to compress data.",
                            page = { CompressionRLE() }
                        )
                    }
                },
            )
        }
    }
}

@Composable
fun setupNavigation() {
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
}