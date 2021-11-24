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

    addAll(Deps.lifecycle)
    addAll(Deps.androidUi)
    addAll(Deps.rxJava)
    addAll(Deps.dagger)

    implementation(project(":utils"))
    implementation(project(":core:common"))
    implementation(project(":core:di"))
    implementation(project(":features:roster:domain"))
    implementation(project(":features:roster:common"))
    implementation(project(":features:roster:roster-details-screen"))
    implementation(project(":features:roster:roster-list-screen"))
}