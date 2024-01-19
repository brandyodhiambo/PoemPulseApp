plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Koin
                api("io.insert-koin:koin-core:3.5.0")
                api("io.insert-koin:koin-compose:1.1.0")

                // Ktor
                implementation("io.ktor:ktor-client-core:2.3.6")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
                implementation("io.ktor:ktor-client-json:2.3.6")
                implementation("io.ktor:ktor-client-logging:2.3.6")
                implementation("io.ktor:ktor-client-serialization:2.3.6")

                // Voyager
                val voyagerVersion = "1.0.0-rc10"
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

                //sql delight
                val sqldelight = "2.0.1"
                implementation("app.cash.sqldelight:runtime:$sqldelight")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight")
                implementation("app.cash.sqldelight:primitive-adapters:$sqldelight")

                // multiplatform settings
                val multiplatformsetting = "1.1.1"
                api("com.russhwolf:multiplatform-settings-no-arg:$multiplatformsetting")
                api("com.russhwolf:multiplatform-settings-coroutines:$multiplatformsetting")

                // window size
                implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")

                //Kottie animation
                implementation("io.github.ismai117:kottie:1.3.1")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.8.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")

                api("io.ktor:ktor-client-android:2.3.6")
                implementation("io.coil-kt:coil-compose:2.5.0")
                implementation("app.cash.sqldelight:android-driver:2.0.1")
                implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies{
                implementation("io.ktor:ktor-client-darwin:2.3.6")
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
                implementation("io.ktor:ktor-client-java:2.3.6")
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.brandyodhiambo.poempulse"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/commonMain/resources")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

sqldelight {
    databases {
        create("PoemDatabase") {
            packageName.set("com.brandyodhiambo.poempulse.database")
        }
    }
}
