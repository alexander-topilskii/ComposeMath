package org.example.project.compression

import kotlinx.coroutines.flow.MutableStateFlow
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
    val uiState = _uiState.asStateFlow()

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
            _uiState.update {
                it.copy(
                    outputText = result,
                    outputSize = result.length.toString(),
                    compressRate = ((_uiState.value.inputSize.toDouble() / result.length.toDouble())).toString(),
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

        for (i in input.indices) {
            val isNotDigit = !input[i].isDigit()
            if (isNotDigit) {
                currentSymbol = input[i]
                for (j in i+1 until input.length-1) {
                    val isNotDigit = !input[j].isDigit()
                    if (isNotDigit) {
                        characterSize = input.substring(i+1,j-1).toInt()
                        break
                    }
                }
                output += currentSymbol.toString().repeat(characterSize)
            }
        }

        return output
    }
}


