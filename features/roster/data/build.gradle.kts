plugins {
    id("base-android-module-plugin")
    id("kotlin-kapt")
}

dependencies {
    implementation(Deps.kotlin)
    implementation(Deps.timber)

    addAll(Deps.rxJava)
    addAll(Deps.room)

    implementation(project(":core:common"))
    implementation(project(":features:roster:domain"))
}