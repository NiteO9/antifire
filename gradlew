#!/bin/sh

# Gradle Wrapper Bootstrap Script
# This script ensures the Gradle wrapper is properly set up in CI environments
# It will detect if the proper wrapper jar exists, and if not, use system Gradle

# Determine if we're in CI
if [ -n "$CI" ]; then
    echo "Running in CI environment - using system Gradle"
    if command -v gradle >/dev/null 2>&1; then
        exec gradle "$@"
    elif [ -f "$GITHUB_WORKSPACE/.github/scripts/gradlew-wrapper.sh" ]; then
        exec sh "$GITHUB_WORKSPACE/.github/scripts/gradlew-wrapper.sh" "$@"
    else
        echo "ERROR: No Gradle found in CI environment"
        exit 1
    fi
fi

# Check for actual Gradle wrapper jar
WRAPPER_JAR=$(find . -name "gradle-wrapper.jar" -print -quit 2>/dev/null)
if [ -n "$WRAPPER_JAR" ]; then
    exec java -cp "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
fi

# Fallback to system Gradle
if command -v gradle >/dev/null 2>&1; then
    echo "WARNING: Gradle wrapper jar not found, using system Gradle"
    exec gradle "$@"
fi

echo "ERROR: Neither Gradle wrapper nor system Gradle found"
echo "Please install Gradle or run in a CI environment"
exit 1
