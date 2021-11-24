plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.compileSdk
    defaultConfig {
        applicationId = "com.nanlagger.packinglist"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(Deps.kotlin)
    implementation(Deps.cicerone)
    implementation(Deps.timber)
    implementation(Deps.fragmentKtx)
    implementation(Deps.activityKtx)

    addAll(Deps.lifecycle)
    addAll(Deps.androidUi)
    addAll(Deps.room)
    addAll(Deps.rxJava)
    addAll(Deps.dagger)

    implementation(project(":utils"))
    implementation(project(":core:common"))
    implementation(project(":core:di"))
    implementation(project(":features:roster:domain"))
    implementation(project(":features:roster:data"))
    implementation(project(":features:roster:common"))
    implementation(project(":features:roster:roster-details-screen"))
    implementation(project(":features:roster:roster-list-screen"))
    implementation(project(":features:roster:roster-flow"))
    implementation(project(":features:main"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}