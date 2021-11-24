plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("base-android-module-plugin") {
            id = "base-android-module-plugin"
            implementationClass = "BaseAndroidModulePlugin"
        }
    }
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    val gradleVersion = "7.0.3"
    val kotlinVersion = "1.5.31"
    implementation("com.android.tools.build:gradle:$gradleVersion")
    implementation("com.android.tools.build:gradle-api:$gradleVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}