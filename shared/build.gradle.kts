plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
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

                implementation("io.insert-koin:koin-core:3.1.4")

                implementation("com.russhwolf:multiplatform-settings:0.9")
                implementation("com.russhwolf:multiplatform-settings-no-arg:0.9")
                implementation("com.russhwolf:multiplatform-settings-coroutines-native-mt:0.9")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:2.0.1")

                implementation("com.russhwolf:multiplatform-settings:0.8.1")

                implementation("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }
//        val androidTest by getting
        val iosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.1")
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.1")
            }
        }
        val iosSimulatorArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.1")
            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.1")

                implementation("com.russhwolf:multiplatform-settings:0.8.1")

                implementation ("com.squareup.sqldelight:native-driver:1.5.3")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
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
}

sqldelight {
    database("CryptoPriceDb") {
        packageName = "com.shahryar.shared"
//        schemaOutputDirectory = file("src/commonMain/kotlin/com/shahryar/shared/data/db")
    }
}