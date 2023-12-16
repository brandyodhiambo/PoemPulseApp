plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
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
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Koin
                api("io.insert-koin:koin-core:3.2.2")

                // Ktor
                implementation("io.ktor:ktor-client-core:2.2.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.2.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.1")
                implementation("io.ktor:ktor-client-json:2.2.1")
                implementation("io.ktor:ktor-client-logging:2.2.1")
                implementation("io.ktor:ktor-client-serialization:2.2.1")

                // Voyager
                val voyagerVersion = "1.0.0-rc04"
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                api("io.ktor:ktor-client-android:2.2.1")
                implementation("io.coil-kt:coil-compose:2.3.0")
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
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.brandyodhiambo.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
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
