``` Kotlin
@Composable  
fun SwipeableCard() {  
    var offsetX by remember { mutableFloatStateOf(0f) }  
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "swipe offset")  
  
    Box(  
        modifier = Modifier  
            .fillMaxWidth()  
            .height(150.dp)  
            .background(Color.LightGray)  
            .pointerInput(Unit) {  
                detectHorizontalDragGestures(  
                    onDragEnd = {  
                        // Snap back when swipe is not enough  
                        offsetX = 0f  
                    },  
                    onHorizontalDrag = { _, dragAmount ->  
                        offsetX += dragAmount  
                    }  
                )  
            }  
    ) {  
        // Left swipe action (Delete)  
        if (offsetX < -100f) {  
            Icon(  
                imageVector = Icons.Default.Delete,  
                contentDescription = "Delete",  
                tint = Color.Red,  
                modifier = Modifier  
                    .align(Alignment.CenterEnd)  
                    .padding(end = 16.dp)  
                    .size(40.dp)  
            )  
        }  
  
        // Right swipe action (Edit)  
        if (offsetX > 100f) {  
            Icon(  
                imageVector = Icons.Default.Edit,  
                contentDescription = "Edit",  
                tint = Color.Green,  
                modifier = Modifier  
                    .align(Alignment.CenterStart)  
                    .padding(start = 16.dp)  
                    .size(40.dp)  
            )  
        }  
  
        // The actual card  
        Card(  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(150.dp)  
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }  
                .background(Color.White),  
            elevation = CardDefaults.cardElevation(8.dp)  
        ) {  
            Box(  
                modifier = Modifier.fillMaxSize(),  
                contentAlignment = Alignment.Center  
            ) {  
                Text(text = "Swipe me", fontSize = 20.sp, fontWeight = FontWeight.Bold)  
            }  
        }    
    }
}
```