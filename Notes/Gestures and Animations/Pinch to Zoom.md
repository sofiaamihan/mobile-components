``` Kotlin
@Composable  
fun AnotherScreen() {  
    var scale by remember { mutableStateOf(1f) } // Stores zoom level  
    var offset by remember { mutableStateOf(Offset.Zero) } // Stores pan offset  
  
    Box(  
        modifier = Modifier  
            .fillMaxSize()  
            .pointerInput(Unit) {  
                detectTransformGestures { _, pan, zoom, _ ->  
                    scale *= zoom  
                    offset += pan  
                }  
            }            .graphicsLayer(  
                scaleX = scale,  
                scaleY = scale,  
                translationX = offset.x,  
                translationY = offset.y  
            )  
            .background(Color.Red),  
        contentAlignment = Alignment.Center  
    ) {  
        Column(  
            horizontalAlignment = Alignment.CenterHorizontally,  
            verticalArrangement = Arrangement.Center  
        ) {  
            Share("Share this plez n tq", LocalContext.current)  
        }  
    }
}
```

# Issues
- Need to set limit
- This is only for the screen, not sure how the element will look like