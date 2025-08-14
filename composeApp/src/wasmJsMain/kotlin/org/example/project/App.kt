package org.example.project

import TwoPointersScreenGpt
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.browser.window
import org.example.project.components.NavItem
import org.example.project.navigation.navigateBack
import org.example.project.screens.algorithms.AlgorithmConstants
import org.example.project.screens.algorithms.AlgorithmsPage
import org.example.project.screens.algorithms.linear_algorithms.*
import org.w3c.dom.events.Event

const val TWO_POINTERS_SCREEN_GPT = "two_pointers_gpt"

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
                    AlgorithmsPage(
                        onBack = { navigateBack() },
                        pages = listOf(
                            NavItem(
                                id = QUICK_MERGE_SORT_SCREEN,
                                title = "Быстрая сортировка, слиянием",
                                description = "",
                                categoryId = AlgorithmConstants.LINEAR_ALGORITHMS_CATEGORY,
                                page = { QuickMergeSortScreen(onBack = { navigateBack() }) }
                            ),
                            NavItem(
                                id = TWO_POINTERS_SCREEN_GEM,
                                title = "TwoPointersScreen gem",
                                description = "",
                                categoryId = AlgorithmConstants.LINEAR_ALGORITHMS_CATEGORY,
                                page = { TwoPointersScreenGem(onBack = { navigateBack() }) }
                            ),
                            NavItem(
                                id = TWO_POINTERS_SCREEN_GROK,
                                title = "TwoPointersScreen grok",
                                description = "",
                                categoryId = AlgorithmConstants.LINEAR_ALGORITHMS_CATEGORY,
                                page = { TwoPointersScreenGrok(onBack = { navigateBack() }) }
                            ),
                            NavItem(
                                id = TWO_POINTERS_SCREEN_GPT,
                                title = "TwoPointersScreen gpt",
                                description = "",
                                categoryId = AlgorithmConstants.LINEAR_ALGORITHMS_CATEGORY,
                                page = { TwoPointersScreenGpt(onBack = { navigateBack() }) }
                            )
                        ),
                        startDestination = QUICK_MERGE_SORT_SCREEN
                    )
                }

            }
        }
    }
}
