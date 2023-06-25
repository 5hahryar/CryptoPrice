plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.21"
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    ios {
        binaries { framework { baseName = "shared" } }
    }
    iosSimulatorArm64 {
        binaries {
            framework {
                baseName = "shared"
                isStatic = true
            }
        }
    }

    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.0.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.1")
                implementation("io.ktor:ktor-client-logging:2.0.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.0.1")

                implementation("ch.qos.logback:logback-classic:1.2.11")
                implementation("io.github.aakira:napier:2.5.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")

                implementation("io.insert-koin:koin-core:3.1.4")

                implementation("com.russhwolf:multiplatform-settings:0.9")
                implementation("com.russhwolf:multiplatform-settings-no-arg:0.9")
                implementation("com.russhwolf:multiplatform-settings-coroutines-native-mt:0.9")

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("com.arkivanov.decompose:decompose:2.0.0-compose-experimental-alpha-02")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-compose-experimental-alpha-02")

                implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-rc05")
                implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.0.0-rc05")
                implementation("cafe.adriel.voyager:voyager-transitions:1.0.0-rc05")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:2.0.1")

                implementation("com.russhwolf:multiplatform-settings:0.8.1")

                implementation("com.squareup.sqldelight:android-driver:1.5.5")
            }
        }
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.1")

                implementation("com.russhwolf:multiplatform-settings:0.8.1")

                implementation("com.squareup.sqldelight:native-driver:1.5.5")
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    namespace = "com.shahryar.shared"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    database("CryptoPriceDb") {
        packageName = "com.shahryar.shared"
    }
}