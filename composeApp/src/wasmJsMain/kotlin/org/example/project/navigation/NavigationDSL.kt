package org.example.project.navigation

import androidx.compose.runtime.Composable
import org.example.project.components.NavCategory
import org.example.project.components.NavItem

/**
 * Top-level builder that returns a list of NavItem built via a DSL.
 */
fun navList(build: NavListBuilder.() -> Unit): List<NavItem> =
    NavListBuilder().apply(build).toList()

class NavListBuilder {
    private val items = mutableListOf<NavItem>()

    /**
     * Define a top-level category containing nested items.
     * Category id and title are taken from NavCategory to avoid duplication.
     */
    fun category(
        category: NavCategory,
        description: String = "",
        build: NavCategoryBuilder.() -> Unit
    ) {
        val cat = NavCategoryBuilder(category, description)
            .apply(build)
            .toNavItem()
        items += cat
    }

    /**
     * Optionally allow placing a leaf at the top level (without wrapping category).
     */
    fun leaf(
        category: NavCategory,
        id: String,
        title: String,
        description: String,
        page: @Composable () -> Unit
    ) {
        items += NavItem(
            id = id,
            title = title,
            description = description,
            category = category,
            page = page
        )
    }

    internal fun toList(): List<NavItem> = items
}

/**
 * Scoped builder inside a category; its category is applied to all nested leaves by default.
 */
class NavCategoryBuilder(
    private val category: NavCategory,
    private val description: String
) {
    private val children = mutableListOf<NavItem>()

    fun leaf(
        id: String,
        title: String,
        description: String,
        page: @Composable () -> Unit
    ) {
        children += NavItem(
            id = id,
            title = title,
            description = description,
            category = category,
            page = page
        )
    }

    internal fun toNavItem(): NavItem = NavItem(
        id = category.id,
        title = category.name,
        description = description,
        category = category,
        pages = children
    )
}
