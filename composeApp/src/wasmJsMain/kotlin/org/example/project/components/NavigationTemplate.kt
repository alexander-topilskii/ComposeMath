package org.example.project.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.navigation.navigateBack
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Data class representing a navigation card
 */
data class NavItem(
    val id: String,
    val title: String,
    val description: String,
    val category: NavCategory,
    val pages: List<NavItem> = emptyList(),
    val page: @Composable () -> Unit = {
        NavPagePage(
            title = category.name,
            onBack = { navigateBack() },
            pages = pages
        )
    }
)

/**
 * Data class representing a category for navigation items
 */
data class NavCategory(
    val id: String,
    val name: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiButton(
    emoji: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = emoji,
        modifier = modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .wrapContentSize(Alignment.Center),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTemplate(
    categories: List<NavCategory>,
    items: List<NavItem>,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    selectedContent: @Composable () -> Unit,
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var isCollapsed by remember { mutableStateOf(false) }
    val sidebarWidth by animateDpAsState(if (isCollapsed) 64.dp else 280.dp, label = "sidebarWidth")

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sidebar
            Surface(
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(sidebarWidth)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = if (isCollapsed) 8.dp else 12.dp, vertical = 12.dp)
                ) {
                    // Collapse / Expand (emoji)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        EmojiButton(
                            emoji = if (isCollapsed) "▶" else "◀",
                            contentDescription = if (isCollapsed) "Expand menu" else "Collapse menu",
                            onClick = { isCollapsed = !isCollapsed }
                        )

                        if (!isCollapsed && onBack != null) {
                            Spacer(Modifier.width(8.dp))
                            // Back as emoji text
                            Text(
                                text = "← Back",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable(onClick = onBack)
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    // Categories + Items
                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = if (isCollapsed) 0.dp else 4.dp,
                            end = if (isCollapsed) 0.dp else 4.dp,
                            top = 4.dp, bottom = 16.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        categories.forEach { category ->
                            item(key = "cat_header_${category.id}") {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = if (isCollapsed) 4.dp else 4.dp,
                                            end = if (isCollapsed) 4.dp else 8.dp,
                                            top = 4.dp, bottom = 2.dp
                                        )
                                ) {
                                    // category color dot replaced with emoji square for WASM safety
                                    Text(
                                        text = "■",
                                        color = category.color,
                                        modifier = Modifier.padding(end = if (isCollapsed) 0.dp else 8.dp)
                                    )
                                    if (!isCollapsed) {
                                        Text(
                                            text = category.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            if (!isCollapsed) {
                                val catItems = items.filter { it.category == category }
                                items(
                                    count = catItems.size,
                                    key = { catItems[it].id }
                                ) { idx ->
                                    val item = catItems[idx]
                                    NavigationDrawerItem(
                                        label = {
                                            Column(Modifier.padding(vertical = 6.dp)) {
                                                Text(item.title, style = MaterialTheme.typography.bodyMedium)
                                                if (item.description.isNotBlank()) {
                                                    Spacer(Modifier.height(2.dp))
                                                    Text(
                                                        item.description,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        maxLines = 2
                                                    )
                                                }
                                            }
                                        },
                                        selected = currentRoute == item.id,
                                        onClick = {
                                            navController.navigate(item.id) {
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(Modifier.padding(16.dp)) {
                        selectedContent()
                    }
                }
            }
        }
    }
}



