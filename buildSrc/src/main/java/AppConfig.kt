/**
 * Created by HeinHtetZaw on 6/6/21.
 */
object AppConfig {
    const val compileSdk = 30
    const val minSdk = 23
    const val targetSdk = 30

    const val buildToolsVersion = "30.0.3"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules = "consumer-rules.pro"
    const val dimension = "environment"


    private val versionMajor = 1
    private val versionMinor = 0
    private val versionPatch = 0
    private val rebuildNumber = 0

    val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + rebuildNumber * 10
    val versionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Versions {
    //app level
    const val gradle = "4.1.3"
    const val kotlin = "1.4.32"

    //libs
    val coreKtx = "1.2.0"
    val appcompat = "1.3.0-alpha01"
    val constraintLayout = "2.0.0-beta8"

    //test
    val junit = "4.12"
    val extJunit = "1.1.1"
    val espresso = "3.2.0"
}
