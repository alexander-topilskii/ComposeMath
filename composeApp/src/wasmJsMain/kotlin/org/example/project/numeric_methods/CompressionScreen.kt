package org.example.project.numeric_methods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.project.viewmodel.ViewModel
import org.example.project.viewmodel.rememberViewModel


@Composable
fun LinearMethodScreen(onBack: () -> Unit) {
    LinearMethod()
}

@Composable
fun LinearMethod() {
    val viewModel = rememberViewModel { CompressionViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Text(uiState.text)
    }
}


data class LinearMethodState(
    val text: String = "Hello World!",
)

class CompressionViewModel : ViewModel() {

    val uiState: StateFlow<LinearMethodState> = MutableStateFlow(LinearMethodState())

    init {

    }
}
