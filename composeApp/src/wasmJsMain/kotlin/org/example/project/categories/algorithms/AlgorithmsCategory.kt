package org.example.project.categories.algorithms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.components.NavCategory
import org.example.project.navigation.NavListBuilder
import org.example.project.navigation.navigateBack
import org.example.project.screens.algorithms.linear_algorithms.QuickMergeSortScreen

private val AlgorithmsCategory = NavCategory(
    id = "algorithms",
    name = "Algorithms",
    color = Color(0xFFEC407A)
)

fun NavListBuilder.algorithmsCategory() {
    category(
        category = AlgorithmsCategory,
        description = "Добро пожаловать в раздел Algorithms. Здесь собраны алгоритмы и структуры данных с интерактивными демонстрациями, сравнениями и визуализациями. Начните с обзора, затем переходите к конкретным алгоритмам: сортировки, поиск, разложение и др."
    ) {
        // Welcome page
        leaf(
            id = "${AlgorithmsCategory.id}_welcome",
            title = "Обзор раздела",
            description = "Что вы найдёте в разделе Algorithms",
            page = { AlgorithmsWelcomePage() }
        )

        // Demo leaves
        leaf(
            id = "QuickMergeSortScreen",
            title = "QuickMergeSortScreen",
            description = "Быстрая демонстрация гибридной сортировки (quick/merge).",
            page = { QuickMergeSortScreen(onBack = { navigateBack() }) }
        )
    }
}

@Composable
private fun AlgorithmsWelcomePage() {
    Column {
        Text("Algorithms", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Этот раздел посвящён алгоритмам и структурам данных. Здесь доступны интерактивные примеры, визуализации и краткие объяснения. Начните с обзора, затем изучайте отдельные алгоритмы: сортировки, поиск, жадные и динамические подходы."
        )
    }
}
