package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data class representing a navigation card
 */
data class NavItem(
    val id: String,
    val title: String,
    val description: String,
    val categoryId: String,
    val onClick: () -> Unit
)

/**
 * Data class representing a category for navigation items
 */
data class NavCategory(
    val id: String,
    val name: String,
    val color: Color
)

/**
 * A reusable navigation template that can be used for creating navigation pages
 * with categorized items displayed as cards.
 *
 * @param title The title of the navigation page
 * @param categories List of categories to organize navigation items
 * @param items List of navigation items to display
 * @param onBack Callback for handling back navigation, null if it's a root page
 * @param modifier Modifier for the component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTemplate(
    title: String,
    categories: List<NavCategory>,
    items: List<NavItem>,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var screenWidth by remember { mutableStateOf(0.dp) }

    // State for selected categories (multi-select)
    val selectedCategories = remember { mutableStateMapOf<String, Boolean>() }

    // Initialize with all categories selected
    LaunchedEffect(Unit) {
        categories.forEach { category -> 
            selectedCategories[category.id] = true 
        }
    }

    // Filter items based on selected categories
    val filteredItems = if (selectedCategories.isEmpty() || selectedCategories.none { it.value }) {
        items
    } else {
        items.filter { item -> selectedCategories[item.categoryId] == true }
    }

    // Group items by category
    val groupedItems = filteredItems.groupBy { it.categoryId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    onBack?.let {
                        IconButton(onClick = onBack) {
                            Text("←")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .onSizeChanged { size ->
                    with(density) {
                        screenWidth = size.width.toDp()
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Determine if mobile-like layout (< 768dp ~ tablets/phones)
            val isMobile = screenWidth < 768.dp

            // Category filter chips
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category filter row with colored chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategories[category.id] == true
                        FilterChip(
                            selected = isSelected,
                            onClick = { 
                                selectedCategories[category.id] = !isSelected 
                            },
                            label = { Text(category.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = category.color.copy(alpha = 0.7f),
                                selectedLabelColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // Cards grouped by category
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    val categoryItems = groupedItems[category.id] ?: emptyList()

                    if (categoryItems.isNotEmpty()) {
                        item {
                            Column {
                                // Category header
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .background(category.color, RoundedCornerShape(4.dp))
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = category.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Grid of cards for this category
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(if (isMobile) 1 else 3),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    userScrollEnabled = false,  // Prevent nested scrolling
                                    modifier = Modifier.height(
                                        if (isMobile) {
                                            (categoryItems.size * 150).dp
                                        } else {
                                            ((categoryItems.size + 2) / 3 * 150).dp
                                        }
                                    )
                                ) {
                                    items(categoryItems) { item ->
                                        NavigationCard(
                                            item = item,
                                            categoryColor = category.color
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A card component for displaying a navigation item with styling based on its category color
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationCard(
    item: NavItem,
    categoryColor: Color
) {
    // Generate a unique shade based on the item ID
    val cardColor = remember(item.id) {
        // Create a variation of the category color
        val hashValue = item.id.hashCode()
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

    Card(
        onClick = item.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
