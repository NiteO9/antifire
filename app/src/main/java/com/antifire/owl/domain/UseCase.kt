package com.antifire.owl.domain

/**
 * Base interface for all use cases in the domain layer.
 * 
 * Phase 2 Foundation - Domain layer base class.
 * 
 * This establishes the Clean Architecture pattern where use cases
 * represent business logic operations. Phase 2 does not implement
 * any concrete use cases yet - only the base interface.
 */
interface UseCase<Params, Result> {
    suspend operator fun invoke(params: Params): Result
}

/**
 * Base interface for use cases that don't require parameters.
 */
interface UseCaseNoParams<Result> {
    suspend operator fun invoke(): Result
}
