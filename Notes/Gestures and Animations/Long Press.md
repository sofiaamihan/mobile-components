``` Kotlin
modifier = Modifier  
    .pointerInput (Unit){  
        detectTapGestures(  
            onLongPress = { startActivity(context, shareIntent, null) }  
        )  
    }
```