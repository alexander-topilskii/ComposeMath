package org.example.project.categories.numeric

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
import org.example.project.numeric_methods.LinearMethodScreen

private val NumericMethodsCategory = NavCategory(
    id = "Numeric Methods",
    name = "Численные методы",
    color = Color(0xFF42A5F5)
)

fun NavListBuilder.numericMethodsCategory() {
    category(
        category = NumericMethodsCategory,
        description = "Добро пожаловать в раздел «Численные методы». Здесь представлены практические алгоритмы для решения прикладных задач: линейные методы, оптимизация, интерполяция и др. Каждый пример сопровождается кратким объяснением и интерактивом."
    ) {
        // Welcome page
        leaf(
            id = "${NumericMethodsCategory.id}_welcome",
            title = "Обзор раздела",
            description = "Что вы найдёте в разделе «Численные методы»",
            page = { NumericWelcomePage() }
        )

        leaf(
            id = "Numeric Methods",
            title = "Численные методы",
            description = "Пример экрана линейных методов (демо).",
            page = { LinearMethodScreen(onBack = { navigateBack() }) }
        )
    }
}

@Composable
private fun NumericWelcomePage() {
    Column {
        Text("Численные методы", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Раздел содержит интерактивные примеры численных методов: решения систем уравнений, оптимизация, аппроксимация и другие вычислительные техники. Используйте обзор для ориентации и переходите к интересующим вас темам."
        )
    }
}
