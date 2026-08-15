#!/bin/bash

# This is a placeholder for the Gradle wrapper script
# In CI environments, the official Gradle wrapper should be used
# This file ensures the project can be built with system Gradle as fallback

# Check if gradlew exists
if [ -f "./gradlew" ]; then
    ./gradlew "$@"
    exit $?
fi

# Fallback to system Gradle
if command -v gradle >/dev/null 2>&1; then
    gradle "$@"
    exit $?
fi

echo "Error: Neither gradlew nor gradle found"
echo "Please install Gradle or run in CI environment"
exit 1
