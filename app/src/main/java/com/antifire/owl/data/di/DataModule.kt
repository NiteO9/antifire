package com.antifire.owl.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Data layer dependency injection module.
 * 
 * Phase 2 Foundation - Data DI module.
 * 
 * This module provides dependencies for the data layer. Additional
 * dependencies will be added in later phases as data sources and
 * repositories are implemented.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    // Data layer dependencies will be added in later phases
    // - Network service interfaces
    // - Repository implementations
    // - Local data sources
    // - Remote data sources
}
