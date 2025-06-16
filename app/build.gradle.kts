import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "nft.nftcreator.pixel.pixelart"
    compileSdk = 36

    defaultConfig {
        applicationId = "nft.nftcreator.pixel.pixelart"
        minSdk = 26
        targetSdk = 36
        versionCode = 201
        versionName = "2.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildTypes {
        debug {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    bundle {
        abi {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        language {
            enableSplit = false // Optional: only keep one language
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    signingConfigs {
        // We use a bundled debug keystore, to allow debug builds from CI to be upgradable
        named("debug") {}
        create("release") {
            val keystorePropertiesFile = project.rootProject.file("local.properties")
            val properties = Properties()
            properties.load(keystorePropertiesFile.inputStream())

            storeFile = rootProject.file("keystore.jks")
            storePassword = properties["STORE_PASSWORD"] as String
            keyAlias = properties["KEY_ALIAS"] as String
            keyPassword = properties["KEY_PASSWORD"] as String
        }
    }
}

dependencies {
    // Compose BOM for consistent versioning
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Core AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.datastore.preferences)

    // Compose-Related Dependencies
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose)
    implementation(libs.accompanist.adaptive)
    implementation(libs.coil.kt.compose)

    // Koin Dependency Injection
    implementation(libs.koin.android)
    api(libs.koin.core)

    // Kotlin and Coroutines
    implementation(libs.bundles.kotlinx)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Window Management
    implementation(libs.androidx.window)
    implementation(libs.androidx.window.core)

    // Google Services
    implementation(libs.google.ads)
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)


    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.vectordrawable:vectordrawable:1.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")
    implementation("androidx.navigation:navigation-fragment:2.9.0")
    implementation("androidx.annotation:annotation:1.9.1")


    // Third-Party Libraries
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.android.billingclient:billing:7.1.1")
    implementation("com.facebook.android:audience-network-sdk:6.20.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("com.google.android.ads.consent:consent-library:1.0.8")
    implementation("com.larswerkman:HoloColorPicker:1.5")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.facebook.android:audience-network-sdk:6.20.0") {
        exclude("com.android.support")
    }
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}