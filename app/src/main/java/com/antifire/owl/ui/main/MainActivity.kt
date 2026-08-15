package com.antifire.owl.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

/**
 * Main activity for the AntiFire application.
 * 
 * Phase 2 Foundation - Basic activity shell.
 * 
 * This is a placeholder activity that establishes the foundation for
 * future phases. It does not implement any protection functionality.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // Phase 2 foundation - no complex UI yet
        // Actual UI content will be added in later phases
    }
}
