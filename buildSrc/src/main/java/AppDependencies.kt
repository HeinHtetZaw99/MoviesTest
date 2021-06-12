import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    //std lib
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val material = "com.google.android.material:material:1.3.0"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    //test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
//        add(kotlinStdLib)
        add(coreKtx)
        add(material)
        add(appcompat)
        add(constraintLayout)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

object Room {
    private const val version = "2.3.0" // last check date 7th Jun 2021
    private val core = "androidx.room:room-runtime:$version"
    val compiler = "androidx.room:room-compiler:$version"
    private val ktx = "androidx.room:room-ktx:$version"

    val roomImplementations = arrayListOf(core, ktx)
}

object Dagger {
    private const val version = "2.28.2" // last check date 7th Jun 2021
    private val core = "com.google.dagger:dagger:$version"
    private val complier = "com.google.dagger:dagger-compiler:$version"
    private val androidSupport = "com.google.dagger:dagger-android-support:$version"
    private val androidProcessorCompiler = "com.google.dagger:dagger-android-processor:$version"

    val daggerImplementations = arrayListOf(core, androidSupport)
    val daggerKapts = arrayListOf(complier, androidProcessorCompiler)
}

object RxJava{

    private val rxJava =  "io.reactivex.rxjava2:rxjava:2.2.8"
    private val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    private val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    val rxJavaImplementations = arrayListOf(rxJava, rxAndroid, rxKotlin)
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}