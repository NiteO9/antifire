package com.antifire.owl

import android.app.Application

/**
 * AntiFire Android Application class.
 * 
 * Phase 2 Foundation - Application layer entry point.
 * 
 * Implements Clean Architecture + MVVM with dependency injection.
 * 
 * This application communicates ONLY with the existing Cloudflare Worker
 * and NEVER connects directly to Supabase. It does NOT contain:
 * - SUPABASE_SERVICE_KEY
 * - ADMIN_PASSWORD
 * - Any Worker administrative secrets
 */
class AntiFireApp : Application()
