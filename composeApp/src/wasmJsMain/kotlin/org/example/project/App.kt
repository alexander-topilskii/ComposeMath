package org.example.project

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.rememberNavController
import kotlinx.browser.window
import org.example.project.components.NavPagePage
import org.example.project.navigation.navigateBack
import org.example.project.navigation.navList
import org.example.project.categories.algorithms.algorithmsCategory
import org.example.project.categories.compression.compressionCategory
import org.example.project.categories.numeric.numericMethodsCategory
import org.w3c.dom.events.Event

@Composable
fun App() {
    MaterialTheme {
        SelectionContainer {
            setupNavigation()

            NavPagePage(
                title = "Main page",
                onBack = { navigateBack() },
                pages = navList {
                    algorithmsCategory()
                    compressionCategory()
                    numericMethodsCategory()
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