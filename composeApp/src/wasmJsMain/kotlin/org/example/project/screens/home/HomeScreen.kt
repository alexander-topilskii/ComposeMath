package org.example.project.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.components.NavCategory
import org.example.project.components.NavItem
import org.example.project.navigation.navigateWithHistory
import org.example.project.screens.algorithms.AlgorithmsPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: () -> Unit,
    onNavigateToFunction: () -> Unit,
    onNavigateToExamples: () -> Unit,
    navController: androidx.navigation.NavController? = null
) {
    val density = LocalDensity.current

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationCard(
    card: NavItem,
    categoryColor: Color
) {
    // Generate a unique shade based on the card ID
    val cardColor = remember(card.id) {
        // Create a variation of the category color
        val hashValue = card.id.hashCode()
        val lightnessAdjust = (hashValue % 30) / 100f  // -0.3 to 0.3 adjustment

        // Simple way to lighten/darken
        val factor = 1f + lightnessAdjust
        Color(
            red = (categoryColor.red * factor).coerceIn(0f, 1f),
            green = (categoryColor.green * factor).coerceIn(0f, 1f),
            blue = (categoryColor.blue * factor).coerceIn(0f, 1f),
            alpha = categoryColor.alpha
        )
    }

    // Determine text color based on background brightness
    val textColor = if ((cardColor.red * 0.299 + cardColor.green * 0.587 + cardColor.blue * 0.114) > 0.5) {
        Color.Black
    } else {
        Color.White
    }
//
//    Card(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(150.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = cardColor)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = card.title,
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                color = textColor
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = card.description,
//                style = MaterialTheme.typography.bodyMedium,
//                textAlign = TextAlign.Center,
//                color = textColor.copy(alpha = 0.8f),
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
}
