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

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val rebuildNumber = 0

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + rebuildNumber * 10
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Versions {
    //app level
    const val gradle = "4.1.3"
    const val kotlin = "1.4.32"

    //libs
    const val coreKtx = "1.2.0"
    const val appcompat = "1.3.0-alpha01"
    const val constraintLayout = "2.0.0-beta8"

    //test
    const val junit = "4.12"
    const val extJunit = "1.1.1"
    const val espresso = "3.2.0"
}
