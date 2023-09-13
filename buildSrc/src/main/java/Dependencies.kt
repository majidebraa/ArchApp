object ApplicationId {
    const val id = "com.majidebrahimi.archapp"
}

object Modules {
    const val app = ":app"

    const val navigation = ":navigation"

    const val common = ":common"
    const val commonTest = ":common_test"

    const val local = ":data:local"
    const val remote = ":data:remote"
    const val model = ":data:model"
    const val repository = ":data:repository"

    const val domain = ":domain"

    const val featureHome = ":features:home"
    const val featureDetail = ":features:detail"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val kotlin = "1.9.0"
    const val gradle = "8.1.1"
    const val compileSdk = 34
    // const val buildToolsVersion = "31.0.0"
    const val minSdk = 21
    const val targetSdk = 34
    const val appCompat = "1.5.1"
    const val coreKtx = "1.9.0"
    const val constraintLayout = "2.1.4"
    const val junit = "4.13.2"
    const val androidTestRunner = "1.3.0"
    const val espressoCore = "3.3.0"
    const val retrofit = "2.9.0"
    const val retrofitCoroutines = "0.9.2"
    const val retrofitGson = "2.9.0"
    const val gson = "2.10.1"
    const val okHttp = "4.10.0"
    const val coroutines = "1.7.3"
    const val koin = "3.4.3"
    const val lifecycle = "2.6.2"
    const val nav = "2.7.2"
    const val room = "2.6.0-beta01"
    const val recyclerView = "1.2.1"
    const val coil = "1.2.2"
    const val mockWebServer = "2.7.5"
    const val archCoreTest = "2.1.0"
    const val androidJunit = "1.1.0"
    const val mockk = "1.11.0"
    const val fragmentTest = "1.3.4"
    const val dataBinding = "3.6.1"
}

object Libraries {
    // KOIN
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koinViewModel = "io.insert-koin:koin-android-viewmodel:${Versions.koin}"
    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    // RETROFIT
    const val retrofitCoroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    // COIL
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object AndroidLibraries {
    // KOTLIN
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    // ANDROID
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
}

object TestLibraries {
    // ANDROID TEST
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val fragmentNav = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"
    // KOIN
    const val koin = "io.insert-koin:koin-test:${Versions.koin}"
    // MOCK WEBSERVER
    const val mockWebServer = "com.squareup.okhttp:mockwebserver:${Versions.mockWebServer}"
    // MOCK
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    // COROUTINE
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    // DATA BINDING
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.dataBinding}"
}