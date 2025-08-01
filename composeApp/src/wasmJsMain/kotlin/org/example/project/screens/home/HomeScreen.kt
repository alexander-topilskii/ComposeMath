package org.example.project.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToDetails: () -> Unit,
    onNavigateToFunction: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Home Screen")

        Button(onClick = onNavigateToDetails) {
            Text("Go to Details")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToFunction) {
            Text("Open Function Drawer")
        }
    }
}
