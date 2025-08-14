package org.example.project.screens.algorithms.linear_algorithms

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val TWO_POINTERS_SCREEN_GROK = "two_pointers_grok"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoPointersScreenGrok(onBack: () -> Unit) {
    val isDesktop = LocalDensity.current.density < 2f // Rough check for desktop vs mobile
    val horizontalPadding = if (isDesktop) 64.dp else 16.dp
    val fontSizeBody = if (isDesktop) 18.sp else 14.sp
    val fontSizeTitle = if (isDesktop) 32.sp else 24.sp
    val fontSizeCode = if (isDesktop) 16.sp else 12.sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Two Pointers Technique") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = horizontalPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Introduction to Two Pointers",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSizeTitle),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "The two pointers technique is a common algorithmic pattern used to solve problems efficiently, especially those involving arrays or lists. It involves using two indices (pointers) that traverse the data structure from different positions, often meeting in the middle or adjusting based on conditions. This approach typically achieves O(n) time complexity with O(1) extra space.",
                fontSize = fontSizeBody
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "How It Works",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSizeTitle * 0.75f),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "1. Initialize two pointers: usually one at the start (left) and one at the end (right) of the array.\n" +
                "2. Compare or compute based on the values at the pointers.\n" +
                "3. Move the pointers inward:\n" +
                "   - If the condition is not met, increment the left pointer or decrement the right pointer.\n" +
                "4. Repeat until the pointers meet or the condition is satisfied.\n" +
                "Common scenarios include sorted arrays for finding pairs, sums, or removing duplicates.",
                fontSize = fontSizeBody
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Example: Two Sum in a Sorted Array",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSizeTitle * 0.75f),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Problem: Given a sorted array of integers and a target sum, find two numbers that add up to the target.\n" +
                "Assume the array is sorted in ascending order.",
                fontSize = fontSizeBody
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Code Snippet
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        """
                        fun twoSum(numbers: IntArray, target: Int): IntArray {
                            var left = 0
                            var right = numbers.size - 1
                            while (left < right) {
                                val sum = numbers[left] + numbers[right]
                                if (sum == target) {
                                    return intArrayOf(left + 1, right + 1) // 1-indexed if needed
                                } else if (sum < target) {
                                    left++
                                } else {
                                    right--
                                }
                            }
                            return intArrayOf() // No solution
                        }
                        """.trimIndent(),
                        fontFamily = FontFamily.Monospace,
                        fontSize = fontSizeCode
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Visualization",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSizeTitle * 0.75f),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Let's visualize with array [2, 7, 11, 15] and target = 9.\n" +
                "Steps:\n" +
                "- left=0 (2), right=3 (15), sum=17 > 9 → right--\n" +
                "- left=0 (2), right=2 (11), sum=13 > 9 → right--\n" +
                "- left=0 (2), right=1 (7), sum=9 == 9 → Found!",
                fontSize = fontSizeBody
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Interactive Visualization
            var left by remember { mutableStateOf(0) }
            var right by remember { mutableStateOf(3) }
            val array = listOf(2, 7, 11, 15)
            val target = 9
            val currentSum = if (left < right) array[left] + array[right] else 0

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        array.forEachIndexed { index, num ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        when {
                                            index == left -> Color.Green
                                            index == right -> Color.Red
                                            else -> Color.LightGray
                                        },
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    num.toString(),
                                    fontSize = fontSizeBody,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(32.dp)
                                )
                            }
                        }
                    }
                    Text("Left: ${array[left]} (index $left), Right: ${array[right]} (index $right), Sum: $currentSum")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {
                            if (currentSum < target && left < right) left++
                        }) {
                            Text("Move Left")
                        }
                        Button(onClick = {
                            if (currentSum > target && left < right) right--
                        }) {
                            Text("Move Right")
                        }
                        Button(onClick = {
                            left = 0
                            right = array.size - 1
                        }) {
                            Text("Reset")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Other Use Cases",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSizeTitle * 0.75f),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "- Removing duplicates from a sorted array.\n" +
                "- Finding the longest palindrome substring.\n" +
                "- Container with most water (max area).\n" +
                "- Merging two sorted arrays.\n" +
                "Advantages: Efficient, reduces time from O(n²) to O(n).",
                fontSize = fontSizeBody
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}