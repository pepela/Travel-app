import Versions.androidXVersion
import Versions.androidxCoreVersion
import Versions.ankoVersion
import Versions.constraintLayoutVersion
import Versions.glideVersoin
import Versions.gsonVersion
import Versions.koinVersion
import Versions.kotlinVersion
import Versions.lifecycleVersion
import Versions.navVersion
import Versions.okHttpVersion
import Versions.retrofitVersion
import Versions.rxAndroidVersion
import Versions.rxJavaVersion
import Versions.rxKotlinVersion
import VersionsTest.androidJunitVersion
import VersionsTest.assertJVersion
import VersionsTest.espressoVersion
import VersionsTest.jUnitVersion
import VersionsTest.mockitoAndroidVersion
import VersionsTest.mockitoKotlinVersion
import VersionsTest.runnerVersion
import org.gradle.api.artifacts.dsl.DependencyHandler


object Versions {
    const val androidMinSdkVersion = "21"
    const val androidTargetSdkVersion = "28"
    const val androidCompileSdkVersion = "28"

    const val koinVersion = "2.0.1"
    const val kotlinVersion = "1.3.41"
    const val rxJavaVersion = "2.2.10"
    const val rxKotlinVersion = "2.4.0-beta.1"
    const val rxAndroidVersion = "2.1.1"
    const val androidXVersion = "1.0.0"
    const val constraintLayoutVersion = "2.0.0-alpha2"
    const val lifecycleVersion = "2.0.0"
    const val ankoVersion = "0.10.6"
    const val androidxCoreVersion = "1.0.2"
    const val gsonVersion = "2.8.5"
    const val okHttpVersion = "4.0.0"
    const val retrofitVersion = "2.6.0"
    const val navVersion = "2.1.0-alpha06"
    const val glideVersoin = "4.9.0"
}

object VersionsTest {
    const val jUnitVersion = "4.12"
    const val assertJVersion = "3.8.0"
    const val mockitoKotlinVersion = "1.6.0"
    const val runnerVersion = "1.2.0"
    const val espressoVersion = "3.2.0"
    const val androidJunitVersion = "1.1.0"
    const val mockitoAndroidVersion = "2.21.0"
}

object RxDependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${rxJavaVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${rxKotlinVersion}"
}

object DataDependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}

object AppDependencies {
    const val androidxCore = "androidx.core:core-ktx:${androidxCoreVersion}"
    const val koin = "org.koin:koin-android:${koinVersion}"
    const val koinScope = "org.koin:koin-androidx-scope:${koinVersion}"
    const val koinArch = "org.koin:koin-androidx-viewmodel:${koinVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${rxKotlinVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${rxAndroidVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlinVersion}"
    const val androidAnnotations = "androidx.annotation:annotation:${androidXVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${androidXVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${androidXVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}"
    const val material = "com.google.android.material:material:${androidXVersion}"
    const val archExtensions = "androidx.lifecycle:lifecycle-extensions:${lifecycleVersion}"
    const val ankoCommons = "org.jetbrains.anko:anko-commons:${ankoVersion}"
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${navVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${navVersion}"
    const val glide = "com.github.bumptech.glide:glide:${glideVersoin}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${glideVersoin}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${okHttpVersion}"
}

object AppTestDependencies {
    const val junit = "junit:junit:${jUnitVersion}"
    const val kotlinJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}"
    const val androidRunner = "androidx.test:runner:${runnerVersion}"
    const val rules = "androidx.test:rules:${runnerVersion}"
    const val androidJunit = "androidx.test.ext:junit:${androidJunitVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${espressoVersion}"
    const val koin = "org.koin:koin-test:${koinVersion}"
    const val archTesting = "android.arch.core:core-testing:${lifecycleVersion}"
    const val mockitoAndroid = "org.mockito:mockito-android:${mockitoAndroidVersion}"
    const val mockito = "com.nhaarman:mockito-kotlin:${mockitoKotlinVersion}"
}

object RemoteDependencies {
    const val gson = "com.google.code.gson:gson:${gsonVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlinVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${okHttpVersion}"
    const val okHttpLogger = "com.squareup.okhttp3:logging-interceptor:${okHttpVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${retrofitVersion}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${retrofitVersion}"
    const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava2:${retrofitVersion}"
}

object CacheDependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${rxJavaVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${rxKotlinVersion}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlinVersion}"
}

object CacheTestDependencies {
    const val junit = "junit:junit:${jUnitVersion}"
    const val kotlinJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}"
    const val assertj = "org.assertj:assertj-core:${assertJVersion}"
    const val mockito = "com.nhaarman:mockito-kotlin:${mockitoKotlinVersion}"
}

fun DependencyHandler.unitTesting() {
    implementation("junit:junit:${jUnitVersion}")
    implementation("org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}")
    implementation("org.assertj:assertj-core:${assertJVersion}")
    implementation("com.nhaarman:mockito-kotlin:${mockitoKotlinVersion}")
}

fun DependencyHandler.rx() {
    implementation(RxDependencies.rxJava)
    implementation(RxDependencies.rxKotlin)
}

private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.compileOnly(depName: String) {
    add("compileOnly", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}
