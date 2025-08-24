package org.example.project.compression

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.components.PlaceholderScreen


@Composable
fun CompressionScreen(onBack: () -> Unit): Unit = PlaceholderScreen("алгоритмы сжатия", onBack)

//Run-Length Encoding (RLE)
@Composable
fun CompressionRLE() {
    var input by remember { mutableStateOf("Hello World!") }
    var output by remember { mutableStateOf("Hello World!") }

    Column (modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter text to compress: ") }
        )

        Button(
            onClick = {
                output = compress(input)
            }
        ) {
            Text("Compress")
        }

        Text("Output: $output")
    }
}

fun compress(input: String): String {


    return "$input c"
}