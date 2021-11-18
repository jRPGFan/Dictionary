import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.example.dictionary"
    const val compile_sdk = 31
    const val min_sdk = 31
    const val target_sdk = 31
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val base = ":base"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //Features
    const val description = ":description"
    const val history = ":history"
}

object Versions {
    //Design
    //const val appcompat = "1.3.1"
    const val appcompat = "1.4.0-alpha03"
    const val material = "1.4.0"
    const val constraintLayout = "2.1.1"
    const val swipeRefreshLayout = "1.1.0"

    //Kotlin
    const val core = "1.6.0"
    const val stdlib = "1.5.21"
    const val coroutinesCore = "1.5.1"
    const val coroutinesAndroid = "1.5.0"

    // Rx-Java
    const val rxAndroid = "2.1.0"
    const val rxJava = "2.2.8"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    //const val interceptor = "3.12.1"
    const val interceptor = "4.9.1"
    const val adapterCoroutines = "0.9.2"
    const val rxJavaAdapter = "1.0.0"

    //Koin
    const val koinCore = "3.1.2"
    const val koinAndroid = "3.1.2"
    //const val koinViewModel = "2.1.6"
    const val koinAndroidCompat = "3.1.2"

    //Coil
    const val coil = "0.11.0"

    //Room
    const val roomKtx = "2.3.0"
    const val runtime = "2.3.0"
    const val roomCompiler = "2.3.0"

    //Test
    const val jUnit = "4.13.2"
    const val runner = "1.2.0"
    //const val espressoCore = "3.2.0"
    const val espressoCore = "3.4.0"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object RxJava {
    const val rx_android = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rx_java = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val rx_java_adapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.rxJavaAdapter}"
}

object Koin {
    const val koin_android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    //const val koin_view_model = "io.insert-koin:koin-androidx-viewmodel:${Versions.koinViewModel}"
    const val koin_core = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koin_android_compat = "io.insert-koin:koin-android-compat:${Versions.koinAndroidCompat}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}
