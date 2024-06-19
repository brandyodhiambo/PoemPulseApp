/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight.plugin)
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 34
    namespace = "com.brandyodhiambo.poempulse"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/commonMain/composeResources")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")

    defaultConfig {
        minSdk = 24
    }
    kotlin {
        jvmToolchain(17)
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

   /* buildTypes {
        debug {
        }
        *//*create("staging") {
        }*//*
        getByName("release") {
        }
    }*/

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

kotlin {
    androidTarget()
    jvm()
    iosArm64()
    iosSimulatorArm64()


    sourceSets {
            commonMain.dependencies {
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

                api(libs.napier)

                // Voyager - Navigation
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottomSheetNavigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.tabNavigator)
                api(libs.voyager.koin)

                //sql delight
                val sqldelight = "2.0.1"
                implementation("app.cash.sqldelight:runtime:$sqldelight")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight")
                implementation("app.cash.sqldelight:primitive-adapters:$sqldelight")

                // multiplatform settings
                api(libs.mutliplatform.arg)
                api(libs.mutliplatform.coroutine)

                // window size
                implementation(libs.window.size)

                //Kottie animation
                implementation(libs.kottie.animation)
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

            iosMain.dependencies{
                implementation(libs.ktor.client.darwin)
                implementation("app.cash.sqldelight:native-driver:2.0.1")

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
            jvmMain.dependencies {
                implementation(compose.desktop.common)
                api(libs.swing)
                implementation(libs.ktor.client.java)
                implementation(libs.ktor.client.cio)
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
            }
    }
}



sqldelight {
    databases {
        create("PoemDatabase") {
            packageName.set("com.brandyodhiambo.poempulse.database")
        }
    }
}
