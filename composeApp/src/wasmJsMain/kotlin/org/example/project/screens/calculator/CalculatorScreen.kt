package org.example.project.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.AdaptiveScreen

@Composable
fun CalculatorScreen(onBack: () -> Unit) {
    var displayValue by remember { mutableStateOf("0") }
    var operation by remember { mutableStateOf<String?>(null) }
    var firstOperand by remember { mutableStateOf<Double?>(null) }
    var clearOnNextDigit by remember { mutableStateOf(false) }

    AdaptiveScreen(
        title = "Калькулятор",
        onBack = onBack,
        footerText = "© 2025 Простой калькулятор"
    ) { isVerticalLayout, screenWidth ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = displayValue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.End,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Calculator buttons
            val buttonSpacing = 8.dp
            val buttonModifier = if (isVerticalLayout) {
                Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            } else {
                Modifier
                    .height(64.dp)
                    .width(64.dp)
            }

            // Function to handle digit buttons
            fun onDigit(digit: Int) {
                displayValue = if (displayValue == "0" || clearOnNextDigit) {
                    digit.toString()
                } else {
                    displayValue + digit.toString()
                }
                clearOnNextDigit = false
            }

            // Function to handle operations
            fun onOperation(op: String) {
                try {
                    val currentValue = displayValue.toDouble()

                    if (firstOperand != null && operation != null) {
                        // Calculate result from previous operation
                        val result = when (operation) {
                            "+" -> firstOperand!! + currentValue
                            "-" -> firstOperand!! - currentValue
                            "×" -> firstOperand!! * currentValue
                            "÷" -> firstOperand!! / currentValue
                            else -> currentValue
                        }
                        displayValue = formatResult(result)
                        firstOperand = result
                    } else {
                        firstOperand = currentValue
                    }

                    if (op == "=") {
                        // Reset operation state after equals
                        operation = null
                        firstOperand = null
                    } else {
                        operation = op
                        clearOnNextDigit = true
                    }
                } catch (e: Exception) {
                    displayValue = "Error"
                    operation = null
                    firstOperand = null
                    clearOnNextDigit = true
                }
            }

            // Function to clear
            fun onClear() {
                displayValue = "0"
                operation = null
                firstOperand = null
                clearOnNextDigit = false
            }

            // Adaptive layout for the keypad
            if (isVerticalLayout) {
                // Phone/tablet layout (grid)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "C", buttonType = ButtonType.FUNCTION, modifier = buttonModifier) { onClear() }
                        CalculatorButton(text = "±", buttonType = ButtonType.FUNCTION, modifier = buttonModifier) {
                            if (displayValue != "0" && displayValue != "Error") {
                                displayValue = if (displayValue.startsWith("-")) {
                                    displayValue.substring(1)
                                } else {
                                    "-$displayValue"
                                }
                            }
                        }
                        CalculatorButton(text = "%", buttonType = ButtonType.FUNCTION, modifier = buttonModifier) {
                            try {
                                val value = displayValue.toDouble() / 100
                                displayValue = formatResult(value)
                            } catch (e: Exception) {
                                displayValue = "Error"
                            }
                        }
                        CalculatorButton(text = "÷", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("÷") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "7", modifier = buttonModifier) { onDigit(7) }
                        CalculatorButton(text = "8", modifier = buttonModifier) { onDigit(8) }
                        CalculatorButton(text = "9", modifier = buttonModifier) { onDigit(9) }
                        CalculatorButton(text = "×", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("×") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "4", modifier = buttonModifier) { onDigit(4) }
                        CalculatorButton(text = "5", modifier = buttonModifier) { onDigit(5) }
                        CalculatorButton(text = "6", modifier = buttonModifier) { onDigit(6) }
                        CalculatorButton(text = "-", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("-") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "1", modifier = buttonModifier) { onDigit(1) }
                        CalculatorButton(text = "2", modifier = buttonModifier) { onDigit(2) }
                        CalculatorButton(text = "3", modifier = buttonModifier) { onDigit(3) }
                        CalculatorButton(text = "+", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("+") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(
                            text = "0",
                            modifier = Modifier
                                .weight(2f)
                                .aspectRatio(2f)
                        ) { onDigit(0) }
                        CalculatorButton(text = ".", modifier = buttonModifier) {
                            if (!displayValue.contains(".")) {
                                displayValue += "."
                            }
                        }
                        CalculatorButton(text = "=", buttonType = ButtonType.EQUALS, modifier = buttonModifier) { onOperation("=") }
                    }
                }
            } else {
                // Desktop layout (two columns side by side)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Numeric keypad
                    Column(
                        modifier = Modifier.padding(end = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(text = "7", modifier = buttonModifier) { onDigit(7) }
                            CalculatorButton(text = "8", modifier = buttonModifier) { onDigit(8) }
                            CalculatorButton(text = "9", modifier = buttonModifier) { onDigit(9) }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(text = "4", modifier = buttonModifier) { onDigit(4) }
                            CalculatorButton(text = "5", modifier = buttonModifier) { onDigit(5) }
                            CalculatorButton(text = "6", modifier = buttonModifier) { onDigit(6) }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(text = "1", modifier = buttonModifier) { onDigit(1) }
                            CalculatorButton(text = "2", modifier = buttonModifier) { onDigit(2) }
                            CalculatorButton(text = "3", modifier = buttonModifier) { onDigit(3) }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(text = "0", modifier = Modifier.width(136.dp).height(64.dp)) { onDigit(0) }
                            CalculatorButton(text = ".", modifier = buttonModifier) {
                                if (!displayValue.contains(".")) {
                                    displayValue += "."
                                }
                            }
                        }
                    }

                    // Operations column
                    Column(
                        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "C", buttonType = ButtonType.FUNCTION, modifier = buttonModifier) { onClear() }
                        CalculatorButton(text = "÷", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("÷") }
                        CalculatorButton(text = "×", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("×") }
                        CalculatorButton(text = "-", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("-") }
                        CalculatorButton(text = "+", buttonType = ButtonType.OPERATION, modifier = buttonModifier) { onOperation("+") }
                        CalculatorButton(text = "=", buttonType = ButtonType.EQUALS, modifier = buttonModifier) { onOperation("=") }
                    }
                }
            }
        }
    }
}

enum class ButtonType { NUMBER, OPERATION, FUNCTION, EQUALS }

@Composable
fun CalculatorButton(
    text: String,
    buttonType: ButtonType = ButtonType.NUMBER,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (buttonType) {
        ButtonType.NUMBER -> MaterialTheme.colorScheme.surfaceVariant
        ButtonType.OPERATION -> MaterialTheme.colorScheme.primaryContainer
        ButtonType.FUNCTION -> MaterialTheme.colorScheme.secondaryContainer
        ButtonType.EQUALS -> MaterialTheme.colorScheme.primary
    }

    val contentColor = when (buttonType) {
        ButtonType.EQUALS -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

// Helper function to format calculator results
private fun formatResult(value: Double): String {
    return if (value == value.toLong().toDouble()) {
        value.toLong().toString()
    } else {
        value.toString()
    }
}
