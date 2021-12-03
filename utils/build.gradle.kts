plugins {
    id("base-android-module-plugin")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Deps.kotlin)

    addAll(Deps.lifecycle)
    addAll(Deps.androidUi)
}