# Build Issues Reference Guide

This document records the build issues encountered during Phase 2 Android foundation implementation, and how they were resolved. Use this as a reference if similar build problems occur in the future.

## Project Configuration

- **Package Name**: `com.antifire.owl`
- **minSdk**: 24
- **targetSdk**: 37
- **compileSdk**: 37
- **JDK**: 17
- **AGP**: 8.10.0
- **Gradle**: 8.12.1
- **Kotlin**: 2.1.0

## Issue 1: Kotlin DSL Compilation Errors

**Problem**: Using `build.gradle.kts` with version catalog references caused compilation errors:
```
ScriptCompilationError(message=Expecting ')', location=build.gradle.kts (7:16))
Function invocation 'task(...)' expected
```

**Root Cause**: The version catalog (`libs.versions.toml`) was not properly configured, and the `gradle.properties` had incompatible Gradle 9 settings for the Kotlin DSL.

**Solution**: 
- Switched from Kotlin DSL to Groovy DSL (`build.gradle`)
- Removed `libs.versions.toml` version catalog
- Used direct plugin IDs and dependency declarations in `build.gradle` files
- Used `buildscript` block with explicit classpath dependencies

## Issue 2: Repository Configuration Conflict

**Problem**: 
```
InvalidUserCodeException: Build was configured to prefer settings repositories over project repositories but repository 'Google' was added by build file 'build.gradle'
```

**Root Cause**: `dependencyResolutionManagement` in `settings.gradle` had `FAIL_ON_PROJECT_REPOS` mode, but `allprojects` in `build.gradle` was also adding repositories.

**Solution**: Removed the `allprojects` block from `build.gradle` entirely. Repositories are only defined in `settings.gradle`.

## Issue 3: Clean Task Syntax Error

**Problem**: 
```
ScriptCompilationError(message=Expecting ')', location=build.gradle.kts (9:16))
Function invocation 'task(...)' expected
```

**Root Cause**: The `task clean(type: Delete)` syntax in Kotlin DSL was incorrect for the Gradle version being used.

**Solution**: Removed the `clean` task from `build.gradle` since Gradle provides it by default.

## Issue 4: Invalid Dependency Versions

**Problem**: 
```
Could not find androidx.core:core-ktx:1.16.1
Could not find androidx.activity:activity-ktx:1.10.6
```

**Root Cause**: Attempted to use AndroidX dependency versions that don't exist in the Maven repositories yet.

**Solution**: Updated all dependency versions to available published versions:
- `androidx.core:core-ktx`: 1.15.0
- `androidx.activity:activity-ktx`: 1.9.0
- `androidx.lifecycle`: 2.8.7
- `androidx.navigation`: 2.7.7
- `androidx.fragment:fragment-ktx`: 1.8.5

## Issue 5: Adaptive Icon SDK Requirement

**Problem**: 
```
AAPT: error: <adaptive-icon> elements require a sdk version of at least 26
```

**Root Cause**: Adaptive icons require API level 26+, but our minSdk is 24.

**Solution**: 
- Replaced adaptive-icon XML with simple vector drawables
- Used `<vector>` elements with colored paths instead
- This works on all API levels including API 24

## Issue 6: Groovy DSL Syntax for testOptions

**Problem**: 
```
Could not set unknown property 'isIncludeAndroidResources' for object of type com.android.build.gradle.internal.dsl.TestOptions$UnitTestOptions
```

**Root Cause**: Kotlin DSL syntax (`isIncludeAndroidResources = true`) was used in a Groovy build file. In Groovy, boolean properties should not have the `is` prefix.

**Solution**: Changed `isIncludeAndroidResources = true` to `includeAndroidResources = true`

## Issue 7: Hilt/DI Framework Annotation Processing Failure

**Problem**: 
```
@error.NonExistentClass()
incompatible types: NonExistentClass cannot be converted to Annotation
```

**Root Cause**: The `MainActivity` and `MainViewModel` had `@AndroidEntryPoint` and `@HiltViewModel` annotations, but Hilt was not properly configured as a dependency in the project.

**Solution**: 
- Removed Hilt annotations from `MainActivity` and `MainViewModel` for Phase 2 foundation
- Removed Hilt dependencies from `build.gradle`
- Created a simple POJO-based DI module placeholder instead
- Will integrate proper DI framework (Hilt) in later phases when needed

## Issue 8: Android SDK Platform 37 Installation

**Problem**: 
```
Failed to find Platform SDK with path: platforms;android-37
```

**Root Cause**: The `android-actions/setup-android@v3` action was configured with `api-level: 37` but the actual Android SDK platform package is named `platforms;android-37.1` (with version suffix).

**Solution**: Added explicit SDK manager command in the CI workflow:
```yaml
- name: Install Android Platform 37
  run: |
    echo "y" | sdkmanager "platforms;android-37.1" "build-tools;37.0.0"
```

## Final Working Configuration

### Build Configuration (`./build.gradle`):
```groovy
buildscript {
    ext {
        agp_version = '8.10.0'
        kotlin_version = '2.1.0'
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:$agp_version"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0"
        classpath "org.jetbrains.kotlin:kotlin-serialization:2.1.0"
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

### App Configuration (`app/build.gradle`):
- Uses Groovy DSL (not Kotlin DSL)
- AGP 8.10.0
- Kotlin 2.1.0 with serialization plugin
- Direct dependency declarations (no version catalog)
- Gradle caching enabled in CI
- Builds successfully with GitHub Actions

### CI Workflow (`.github/workflows/android-build.yml`):
- JDK 17 via actions/setup-java
- Android SDK 37 via android-actions/setup-android
- Gradle 8.12.1 via gradle/gradle-build-action
- Explicit SDK platform installation
- Build + test steps
- Artifact upload

## Key Learnings for Future Builds

1. **Use Groovy DSL for Android builds** - More stable and widely supported than Kotlin DSL for build files
2. **Verify dependency versions** - Check Maven repositories before using specific versions
3. **Match minSdk with icon requirements** - Adaptive icons need API 26+, vector drawables work on API 24+
4. **Explicit SDK installation in CI** - Even when `android-actions/setup-android` is used, explicitly install the exact platform package
5. **Avoid unnecessary annotation processors in Phase 2** - Keep dependencies minimal for foundation phase
6. **Always verify GitHub Actions workflow changes** - Test each iteration of the workflow rather than making multiple changes at once
