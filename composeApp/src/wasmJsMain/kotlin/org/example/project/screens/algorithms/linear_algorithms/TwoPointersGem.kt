package org.example.project.screens.algorithms.linear_algorithms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.O
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

const val TWO_POINTERS_SCREEN_GEM = "two_pointers_gem"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoPointersScreenGem(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Алгоритм Two Pointers", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SectionTitle("Что такое Two Pointers?")
                SectionText(
                    "Алгоритм Two Pointers (два указателя) — это мощный и эффективный " +
                            "метод для решения задач, связанных с массивами, списками или строками. " +
                            "Он использует два указателя (переменные, хранящие индексы), " +
                            "которые движутся по структуре данных, чтобы найти нужный результат."
                )
            }

            item {
                SectionTitle("Виды и примеры")
                SectionText(
                    "Наиболее распространенные виды: " +
                            "1. **Оба указателя движутся в одном направлении.** Например, для поиска подмассива. " +
                            "2. **Один указатель с начала, другой с конца.** Идеально подходит для отсортированных данных. " +
                            "Это самый популярный вариант, который мы и рассмотрим."
                )
            }

            item {
                TwoPointersVisualization()
            }

            item {
                SectionTitle("Пример: Поиск пары с заданной суммой")
                SectionText(
                    "Задача: найти в отсортированном массиве `[2, 7, 11, 15]` пару чисел, " +
                            "сумма которых равна `9`."
                )
            }

            item {
                CodeExample()
            }

            item {
                SectionTitle("Преимущества")
                SectionText(
                    "🔸 **Эффективность по времени:** часто снижает сложность с $O(n^2)$ до $O(n)$.\n" +
                            "🔸 **Эффективность по памяти:** не требует дополнительной памяти."
                )
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SectionText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TwoPointersVisualization() {
    val array = remember { listOf(2, 7, 11, 15) }
    var leftIndex by remember { mutableStateOf(0) }
    var rightIndex by remember { mutableStateOf(array.size - 1) }
    var found by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Симуляция алгоритма
        val target = 9
        while (leftIndex < rightIndex && !found) {
            delay(1500)
            val currentSum = array[leftIndex] + array[rightIndex]
            if (currentSum == target) {
                found = true
            } else if (currentSum < target) {
                leftIndex++
            } else {
                rightIndex--
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Визуализация",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                array.forEachIndexed { index, value ->
                    val isLeftPointer = index == leftIndex
                    val isRightPointer = index == rightIndex
                    val isFoundPair = found && (isLeftPointer || isRightPointer)

                    val color by animateColorAsState(
                        targetValue = when {
                            isFoundPair -> Color(0xFF4CAF50) // Зеленый для найденной пары
                            isLeftPointer -> Color(0xFF2196F3) // Синий для левого указателя
                            isRightPointer -> Color(0xFFF44336) // Красный для правого указателя
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        animationSpec = tween(durationMillis = 500)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isLeftPointer) {
                            Text(text = "Левый", color = color, fontSize = 12.sp)
                        }
                        if (isRightPointer) {
                            Text(text = "Правый", color = color, fontSize = 12.sp)
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = color),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp)
                                .size(60.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = value.toString(),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            if (found) {
                Text(
                    text = "Пара найдена: ${array[leftIndex]} + ${array[rightIndex]} = 9",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                Text(
                    text = "Ищем пару...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun CodeExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "```kotlin\n" +
                        "fun findPairWithSum(arr: IntArray, target: Int): Pair<Int, Int>? {\n" +
                        "    var left = 0\n" +
                        "    var right = arr.size - 1\n" +
                        "\n" +
                        "    while (left < right) {\n" +
                        "        val sum = arr[left] + arr[right]\n" +
                        "\n" +
                        "        when {\n" +
                        "            sum == target -> return Pair(arr[left], arr[right])\n" +
                        "            sum < target -> left++\n" +
                        "            else -> right--\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    return null\n" +
                        "}\n" +
                        "```",
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}