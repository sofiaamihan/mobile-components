Once you modify the application icon, it will automatically become the splash screen, but we can further develop this by customising the background of the splash screen:
1. Adjust the application API version from 24 to 31
2. Set up the colour you want

`build.gradle.kts`
``` Kotlin
defaultConfig {  
    minSdk = 31
}
```

`res` > `values` > `colors.xml`
``` XML
<color name="teal_200">#FF03DAC5</color>
```

`res` > `values` > `themes.xml`
``` XML
<?xml version="1.0" encoding="utf-8"?>  
<resources>  
    <style name="Theme.FUnk" parent="android:Theme.Material.Light.NoActionBar">  
        <item name="android:windowSplashScreenBackground">@color/teal_200</item>  
        <item name="android:windowSplashScreenIconBackgroundColor">@color/teal_200</item>  
    </style></resources>
```