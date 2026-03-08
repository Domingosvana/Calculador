plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.calculador11"
    compileSdk = 35 // Padronizado com targetSdk (recomendado usar mesma versão)

    defaultConfig {
        applicationId = "com.example.calculador11"
        minSdk = 24
        targetSdk = 34 // Última versão estável
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        // Otimização de recursos
        resourceConfigurations.addAll(listOf("pt", "en", "xxhdpi")) // Inclui apenas recursos necessários
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // Alinhado com compileOptions
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all",
            "-Xstring-concat=inline"
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Configurações de otimização
          //  isDebuggable = false
           // isJniDebuggable = false
           // isRenderscriptDebuggable = false
           // isPseudoLocalesEnabled = false
        }

        debug {
            isDebuggable = true
          //  applicationIdSuffix = ".debug"
           // versionNameSuffix = "-DEBUG"
            // Ativa preview para debug
            //isProfileable = true
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11" // Versão estável
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "**/kotlin/**",
                "**/*.proto"
            )
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.1")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha")

    implementation ("net.objecthunter:exp4j:0.4.8")  // Biblioteca para cálculo matemático

    implementation(platform(libs.androidx.compose.bom))

    // Outros...
    implementation("androidx.compose.material:material-icons-extended")

    // ou se estiver usando o TOML:
   // implementation(libs.androidx.material.icons.extended)


}