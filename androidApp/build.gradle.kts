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
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.brandyodhiambo"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.brandyodhiambo.PoemPulse"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes{
        getByName("debug") {
            versionNameSuffix = " - debug-1"
            applicationIdSuffix = ".debug"
            buildConfigField("int", "PATCH_VERSION_CODE", "1")
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release"){
            isMinifyEnabled = true
            isShrinkResources = true

        }
    }

    buildFeatures{
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

}

kotlin {
    androidTarget()
    sourceSets {
            androidMain.dependencies {
                implementation(project(":shared"))
            }
        }
    }




dependencies {
    implementation(libs.koin.android)
    implementation(libs.koin.core)
}