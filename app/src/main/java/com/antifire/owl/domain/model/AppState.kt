package com.antifire.owl.domain

/**
 * Domain model representing the application state.
 * 
 * Phase 2 Foundation - Domain model shell.
 * 
 * This is a placeholder model that will be expanded with concrete
 * domain entities in later phases when protection state management
 * and authorization integration are implemented.
 */
sealed class AppState {
    object Stopped : AppState()
    object Preparing : AppState()
    object Ready : AppState()
    object Running : AppState()
    data class Error(val message: String) : AppState()
}
