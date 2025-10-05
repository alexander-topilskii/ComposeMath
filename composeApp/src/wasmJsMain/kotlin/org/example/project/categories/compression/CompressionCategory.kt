package org.example.project.categories.compression

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
import org.example.project.compression.CompressionRLE
import org.example.project.compression.CompressionScreen
import org.example.project.navigation.NavListBuilder
import org.example.project.navigation.navigateBack

private val CompressionCategory = NavCategory(
    id = "compression",
    name = "Compression",
    color = Color(0xFF42A5F5)
)

fun NavListBuilder.compressionCategory() {
    category(
        category = CompressionCategory,
        description = "Добро пожаловать в раздел Compression. Здесь собраны алгоритмы сжатия данных — от базовых техник до практических примеров. Изучайте визуальные демонстрации и шаги работы алгоритмов, таких как RLE."
    ) {
        // Welcome page
        leaf(
            id = "${CompressionCategory.id}_welcome",
            title = "Обзор раздела",
            description = "Что вы найдёте в разделе Compression",
            page = { CompressionWelcomePage() }
        )

        leaf(
            id = "CompressionScreen",
            title = "Compression Algos",
            description = "Обзор и сравнение алгоритмов сжатия.",
            page = { CompressionScreen(onBack = { navigateBack() }) }
        )
        leaf(
            id = "compression_rle",
            title = "Run-Length Encoding (RLE)",
            description = "Простейший метод сжатия последовательностей повторяющихся символов.",
            page = { CompressionRLE() }
        )
    }
}

@Composable
private fun CompressionWelcomePage() {
    Column {
        Text("Compression", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(
            "Раздел посвящён алгоритмам сжатия данных. Вы узнаете, как работают простые и популярные методики, увидите примеры преобразований и поймёте, когда их имеет смысл применять. Начните с обзора, затем переходите к конкретным алгоритмам."
        )
    }
}
