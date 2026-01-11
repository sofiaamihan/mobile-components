``` Kotlin
modifier = Modifier  
    .pointerInput (Unit){  
        detectTapGestures(  
            onDoubleTap = { startActivity(context, shareIntent, null) }  
        )  
    }
```
- but this needs to be tested again just to be sure