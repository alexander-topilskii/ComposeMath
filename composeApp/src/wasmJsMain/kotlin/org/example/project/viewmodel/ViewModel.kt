package org.example.project.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class ViewModel {
    val viewModelScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    open fun onCleared() {
        viewModelScope.cancel()
    }
}

@Composable
inline fun <reified T: ViewModel> rememberViewModel(
    crossinline viewModelFactory: @DisallowComposableCalls () -> T
): T {
    val viewModel = remember { viewModelFactory() }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onCleared()
        }
    }
    return viewModel
}
