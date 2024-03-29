import org.gradle.api.artifacts.dsl.DependencyHandler

object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleViewmodelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout${Versions.constraintLayout}"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout${Versions.coordinatorLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    const val material = "com.google.android.material:material:${Versions.material}"

    const val cicerone = "com.github.terrakok:cicerone:${Versions.cicerone}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger2Compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"

    const val androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    val lifecycle = mapOf(
        "implementation" to lifecycleViewmodelKtx,
        "implementation" to lifecycleViewmodelSavedState,
        "implementation" to lifecycleCommon,
    )

    val androidUi = mapOf(
        "implementation" to appCompat,
        "implementation" to constraintLayout,
        "implementation" to coordinatorLayout,
        "implementation" to ktxCore,
        "implementation" to material,
    )

    val room = mapOf(
        "implementation" to roomRuntime,
        "implementation" to roomKtx,
        "kapt" to roomCompiler
    )

    val dagger = mapOf(
        "implementation" to dagger2,
        "kapt" to dagger2Compiler,
    )
}

fun DependencyHandler.addAll(dependencies: Map<String, String>) {
    dependencies.forEach { (configurationName, dependencyNotation) ->
        add(configurationName, dependencyNotation)
    }
}