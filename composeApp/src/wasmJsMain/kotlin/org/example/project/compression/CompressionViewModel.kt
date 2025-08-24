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
)

class CompressionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CompressionState())
    val uiState = _uiState.asStateFlow()

    init {
        onInputTextChanged(_uiState.value.inputText)
        onCompressClick()
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(
            inputText = text,
            inputSize = text.length.toString()
        ) }

    }

    fun onCompressClick() {
        viewModelScope.launch {
            val result = compress(_uiState.value.inputText)
            _uiState.update { it.copy(
                outputText = result,
                outputSize = result.length.toString(),
                compressRate = (result.length.toDouble() / _uiState.value.inputSize.toDouble()).toString()
            ) }
        }
    }

    private fun compress(input: String): String {
        return "$input c 123"
    }
}
