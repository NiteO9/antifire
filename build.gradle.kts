plugins {
    alias(libs.plugins.android.application) version "8.7.3" apply false
    alias(libs.plugins.kotlin.android) version "2.1.0" apply false
    alias(libs.plugins.kotlin.serialization) version "2.1.0" apply false
}

task clean(type: Delete) {
    delete(rootProject.layout.buildDirectory)
}
