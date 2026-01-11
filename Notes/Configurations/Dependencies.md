# `AndroidManifest.xml` 
Theres more but in the individual sections
```XML
<uses-permission android:name="android.permission.INTERNET"/>  
<application  
    android:usesCleartextTraffic="true">
```

# `build.gradle.kts` - Project Level
```Kotlin
id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
```
# `libs.versions.toml`
```TOML
[versions]
material3 = "1.4.0-alpha09"  
materialIconsExtended = "1.7.8"
datastorePreferences = "1.1.2"
navigationCompose = "2.8.5"
roomRuntime = "2.6.1"  
runtimeLivedata = "1.7.7"

[libraries]
material3 = { module = "androidx.compose.material3:material3", version.ref = "material3" }  
material-icons-extended = { module = "androidx.compose.material:material-icons-extended", version.ref = "materialIconsExtended" }
androidx-material-icons-extended = { module = "androidx.compose.material:material-icons-extended" }
androidx-datastore-preferences = { module = "androidx.datastore:datastore-preferences", version.ref = "datastorePreferences" }
androidx-lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-navigation-compose = { module = "androidx.navigation:navigation-compose", version.ref = "navigationCompose" }
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "roomRuntime" }  
androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "roomRuntime" }  
androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "roomRuntime" }  
androidx-runtime-livedata = { module = "androidx.compose.runtime:runtime-livedata", version.ref = "runtimeLivedata" }
```

# `build.gradle.kts` - App Level
``` Kotlin
plugins {
    id("com.google.devtools.ksp")
}

compileSdk = 35

dependencies {
    // Room Database
    implementation(libs.androidx.room.runtime)  
	ksp(libs.androidx.room.compiler)  
	implementation(libs.androidx.runtime.livedata)  
	implementation(libs.androidx.room.ktx)

    // Design and Icons
	implementation(libs.material3)  
	implementation(libs.material.icons.extended)
	implementation (libs.androidx.material.icons.extended)

	// Datastore Preferences  
	implementation(libs.androidx.datastore.preferences)

	// View Model  
	implementation (libs.androidx.lifecycle.viewmodel.compose)

	// Navigation and Animations
	implementation(libs.androidx.navigation.compose)
}
```

# Important Imports
``` Kotlin
// Using by remember
import androidx.compose.runtime.getValue  
import androidx.compose.runtime.setValue

// Setting up navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

// Room
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
```

# MAYBES
```KTS
// Splash Screen  
implementation(libs.androidx.core.splashscreen)  
  
// Paging Compose  
implementation(libs.accompanist.pager)  
implementation(libs.accompanist.pager.indicators)  

```

```TOML
[versions]
accompanistPager = "0.13.0"  
coreSplashscreen = "1.0.1"  

[libraries]
accompanist-pager = { module = "com.google.accompanist:accompanist-pager", version.ref = "accompanistPager" }  
accompanist-pager-indicators = { module = "com.google.accompanist:accompanist-pager-indicators", version.ref = "accompanistPager" }  
androidx-core-splashscreen = { module = "androidx.core:core-splashscreen", version.ref = "coreSplashscreen" }  
```

- i think theres still more libraries i haven't looked into but there might be one that uses plugins