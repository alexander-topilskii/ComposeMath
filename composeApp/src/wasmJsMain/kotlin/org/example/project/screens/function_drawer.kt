package org.example.project.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


import org.example.project.components.AdaptiveScreen

@Composable
fun FunctionDrawer(modifier: Modifier, onBack: () -> Unit) {
    var selectedFunction by remember { mutableStateOf("sin(x)") }
    var scale by remember { mutableStateOf(1f) }

    AdaptiveScreen(
        title = "Визуализатор математических функций",
        onBack = onBack,
        footerText = "© 2025 Визуализатор функций. Сделано с использованием Kotlin и Compose Multiplatform.",
        modifier = modifier
    ) { isVerticalLayout, screenWidth ->
        // Main content area with controls and graph
        if (isVerticalLayout) {
            // Vertical layout (mobile/tablet)
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Controls(selectedFunction, { selectedFunction = it }, scale, { scale = it })
                Spacer(Modifier.height(16.dp))
                FunctionGraph(selectedFunction, scale, Modifier.fillMaxWidth().height(300.dp))
            }
        } else {
            // Horizontal layout (desktop/larger screens)
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Controls(
                    selectedFunction,
                    { selectedFunction = it },
                    scale,
                    { scale = it },
                    Modifier.weight(0.3f)
                )
                Spacer(Modifier.width(16.dp))
                FunctionGraph(selectedFunction, scale, Modifier.weight(0.7f).height(400.dp))
            }
        }

        // Algorithm section
        Card(
            modifier = Modifier.fillMaxWidth(0.9f).padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Алгоритм построения графика", style = MaterialTheme.typography.headlineMedium)
                Text("1. Генерация точек: Создаем массив x-значений от -10 до 10 с шагом 0.1, умноженным на масштаб.")
                Text("2. Вычисление значений: Для каждой точки x вычисляем y в зависимости от выбранной функции (sin(x), cos(x) или x²).")
                Text("3. Интерполяция: Рисуем линии между последовательными точками для создания гладкой кривой с использованием линейной интерполяции.")
                Text("4. Отрисовка осей: Добавляем оси координат для ориентира.")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Controls(
    selectedFunction: String,
    onFunctionChange: (String) -> Unit,
    scale: Float,
    onScaleChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Dropdown for function
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                readOnly = true,
                value = selectedFunction,
                onValueChange = {},
                label = { Text("Функция") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("sin(x)") }, onClick = { onFunctionChange("sin(x)"); expanded = false })
                DropdownMenuItem(text = { Text("cos(x)") }, onClick = { onFunctionChange("cos(x)"); expanded = false })
                DropdownMenuItem(text = { Text("x²") }, onClick = { onFunctionChange("x²"); expanded = false })
            }
        }

        // Slider for scale
        Slider(
            value = scale,
            onValueChange = onScaleChange,
            valueRange = 0.5f..5f,
            steps = 45, // 0.1 steps
            modifier = Modifier.fillMaxWidth()
        )
        Text("Масштаб: ${scale}x")
    }
}

@Composable
private fun FunctionGraph(function: String, scale: Float, modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(4.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2
            val centerY = height / 2

            // Draw axes
            drawLine(Color.Black, Offset(0f, centerY), Offset(width, centerY))
            drawLine(Color.Black, Offset(centerX, 0f), Offset(centerX, height))

            // Scale factors
            val xScale = (width / 20f) * scale
            val yScale = (height / 20f) * scale

            // Draw function
            drawFunction(function, centerX, centerY, xScale, yScale, width)
        }
    }
}

private fun DrawScope.drawFunction(
    function: String,
    centerX: Float,
    centerY: Float,
    xScale: Float,
    yScale: Float,
    width: Float
) {
    var prevX: Float? = null
    var prevY: Float? = null

    for (i in 0..width.toInt()) {
        val x = (i - centerX) / xScale
        val y = when (function) {
            "sin(x)" -> sin(x.toDouble()).toFloat()
            "cos(x)" -> cos(x.toDouble()).toFloat()
            "x²" -> (x * x)
            else -> 0f
        }
        val canvasX = i.toFloat()
        val canvasY = centerY - (y * yScale)

        if (prevX != null && prevY != null) {
            drawLine(Color.Blue, Offset(prevX!!, prevY!!), Offset(canvasX, canvasY), strokeWidth = 2f)
        }

        prevX = canvasX
        prevY = canvasY
    }
}