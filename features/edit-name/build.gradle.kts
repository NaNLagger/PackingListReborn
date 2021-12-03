plugins {
    id("base-android-module-plugin")
    id("kotlin-kapt")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Deps.kotlin)
    implementation(Deps.cicerone)
    implementation(Deps.timber)
    implementation(Deps.fragmentKtx)
    implementation(Deps.activityKtx)

    addAll(Deps.lifecycle)
    addAll(Deps.androidUi)
    addAll(Deps.dagger)

    implementation(project(":utils"))
    implementation(project(":core:common"))
    implementation(project(":core:di"))
}