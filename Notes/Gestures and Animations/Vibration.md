# AndroidManifest.xml
``` XML
<uses-permission android:name="android.permission.VIBRATE" />
```

# Vibrate Function
``` Kotlin
fun vibratePhone(context: Context) {  
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {  
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager  
        vibratorManager.defaultVibrator  
    } else {  
        @Suppress("DEPRECATION")  
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator  
    }  
  
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))  
    } else {  
        @Suppress("DEPRECATION")  
        vibrator.vibrate(200)  
    }  
}
```

# Application
``` Kotlin
val context = LocalContext.current
Button(  
    onClick = {  
        vibratePhone(context)  
    }  
) {  
    Text("Vibrate phone")  
}
```