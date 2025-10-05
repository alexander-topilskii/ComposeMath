package org.example.project.compression

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.viewmodel.ViewModel

data class CompressionState(
    val inputText: String = "Hello World!",
    val inputSize: String = "0",
    val outputText: String = "",
    val outputSize: String = "0",
    val compressRate: String = "0",
    val decoded: String = "",
    val decodedSize: String = "0",
)

class CompressionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CompressionState())
    val uiState: StateFlow<CompressionState> = _uiState.asStateFlow()

    init {
        onInputTextChanged(_uiState.value.inputText)
        onCompressClick()
    }

    fun onInputTextChanged(text: String) {
        _uiState.update {
            it.copy(
                inputText = text,
                inputSize = text.length.toString()
            )
        }

    }

    fun onCompressClick() {
        viewModelScope.launch {
            val result = compress(_uiState.value.inputText)
            val decoded = decoder(result)
            val inputSizeDouble = _uiState.value.inputSize.toDouble()
            val resultLength = result.length
            val rate = if (resultLength > 0) inputSizeDouble / resultLength else 0.0
            _uiState.update {
                it.copy(
                    outputText = result,
                    outputSize = resultLength.toString(),
                    compressRate = rate.toString(),
                    decoded = decoded,
                    decodedSize = decoded.length.toString()
                )
            }
        }
    }

    private fun compress(input: String): String {
        var output = ""
        var currentSymbol: Char? = null
        var counter = 0

        for (i in input.indices) {
            if (input[i] == currentSymbol) {
                counter++
            } else {
                if (currentSymbol != null) {
                    output += "$currentSymbol$counter"
                }
                currentSymbol = input[i]
                counter = 1
            }
        }

        if (currentSymbol != null) {
            output += "$currentSymbol$counter"
        }

        return output
    }

    private fun decoder(input: String): String {
        var output = ""
        var currentSymbol: Char? = null
        var characterSize:Int = 0
        var i = 0 // Initialize i as mutable variable

        while (i < input.length) {
            if (!input[i].isDigit()) {
                currentSymbol = input[i]
                var j = i + 1
                // Reset characterSize for the next run-length
                characterSize = 0
                while (j < input.length && input[j].isDigit()) {
                    // Accumulate digit characters into an integer
                    characterSize = characterSize * 10 + (input[j] - '0')
                    j++
                }
                if (characterSize > 0) {
                    output += currentSymbol.toString().repeat(characterSize)
                }
                i = j - 1 // Move index to the end of the digit sequence
            } else {
                // Handle the case when the character is a digit (this should not happen in valid input)
                i++
            }
        }

        return output
    }
}


