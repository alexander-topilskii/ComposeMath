package org.example.project.compression

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.rememberViewModel


@Composable
fun CompressionScreen(onBack: () -> Unit) {
    CompressionRLE()
}

//Run-Length Encoding (RLE)
@Composable
fun CompressionRLE() {
    val viewModel = rememberViewModel { CompressionViewModel() }
    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        OutlinedTextField(
            value = uiState.inputText,
            onValueChange = viewModel::onInputTextChanged,
            label = { Text("Enter text to compress: ") }
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = viewModel::onCompressClick
        ) {
            Text("Compress")
        }

        CompressionStateCard(uiState)
    }
}


@Composable
fun CompressionStateCard(
    state: CompressionState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Compression Result",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // Input block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("Input:", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = state.inputText,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Size: ${state.inputSize} characters",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Output block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("Output:", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = state.outputText.ifEmpty { "—" },
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Size: ${state.outputSize} chars",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Decoded block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("Decoded:", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = state.decoded.ifEmpty { "—" },
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Size: ${state.decodedSize} chars",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Compression rate
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Compression rate:", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = "${state.compressRate}%",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
