package org.example.project.navigation

import androidx.navigation.NavController
import kotlinx.browser.window

fun NavController.navigateWithHistory(route: String) {
    window.history.pushState(null, "", window.location.href)
    this.navigate(route)
}

fun navigateBack() {
    window.history.back()
}
