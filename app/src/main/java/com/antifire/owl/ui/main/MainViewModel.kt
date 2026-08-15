package com.antifire.owl.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main ViewModel for the AntiFire application.
 * 
 * Phase 2 Foundation - ViewModel shell.
 * 
 * This establishes the MVVM pattern foundation. More complex ViewModels
 * will be added in later phases as functionality is implemented.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
            // Phase 2 foundation - no complex logic yet
            // Future phases will populate this
            _uiState.value = MainUiState.Ready
        }
    }
}

/**
 * UI state for the main screen.
 * 
 * Currently very simple - will be expanded when protection
 * functionality is implemented in later phases.
 */
sealed interface MainUiState {
    object Loading : MainUiState
    object Ready : MainUiState
    data class Error(val message: String) : MainUiState
}
