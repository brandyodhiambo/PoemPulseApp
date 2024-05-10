plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight.plugin)
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
                // Compose Multiplatform
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // Koin
                api(libs.koin.core)
                api(libs.koin.compose)

                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.cio)

                // Voyager
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottom.sheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)

                //sql delight
                val sqldelight = "2.0.1"
                implementation("app.cash.sqldelight:runtime:$sqldelight")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight")
                implementation("app.cash.sqldelight:primitive-adapters:$sqldelight")

                // multiplatform settings
                val multiplatformsetting = "1.1.1"
                api(libs.mutliplatform.arg)
                api(libs.mutliplatform.coroutine)

                // window size
                implementation(libs.window.size)

                //Kottie animation
                implementation(libs.kottie.animation)
            }
        }
            androidMain.dependencies {
                api(libs.compose.activity)
                api(libs.appCompact)
                api(libs.core.ktx)

                api(libs.ktor.client.android)
                implementation(libs.coil.compose)
                implementation("app.cash.sqldelight:android-driver:2.0.1")
                implementation(libs.accompanist.systemuicontroller)

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
                implementation(libs.ktor.client.darwin)
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }
        }
            jvmMain.dependencies {
                implementation(compose.desktop.common)
                api(libs.swing)
                implementation(libs.ktor.client.java)
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
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
