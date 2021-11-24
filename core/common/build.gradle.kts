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
    implementation(Deps.timber)
    implementation(Deps.fragmentKtx)
    implementation(Deps.activityKtx)
    implementation(Deps.roomRuntime)
    implementation(Deps.cicerone)

    addAll(Deps.lifecycle)
    addAll(Deps.androidUi)
    addAll(Deps.rxJava)

    implementation(project(":utils"))
}