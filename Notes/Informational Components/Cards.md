`Card`
`OutlinedCard`
`ElevatedCard`
- .clickable() is experimental
# Basic
``` Kotlin
Card (  
    modifier = Modifier  
        .height(200.dp)  
        .clickable{toBlog()}  
        .fillMaxWidth(0.8f),  
    shape = RoundedCornerShape(24.dp),  // Change the shape
    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),  // Change the colour
    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp), // Elevation shadow
){  
    Column (  
        modifier = Modifier  
            .fillMaxWidth()  
    ){  
        Image(  
            painter = painterResource(id = banner),  
            contentDescription = "Banner",  
            modifier = Modifier  
                .height(125.dp),  
            contentScale = ContentScale.FillWidth  // Wrapping a banner
        )
    }
}
```

# Custom
``` Kotlin
// -------------------- GRADIENT BACKGROUND --------------------
val horizontalGradientBrush = Brush.horizontalGradient(  
    colors = listOf(  
        MaterialTheme.colorScheme.primaryContainer,  
        MaterialTheme.colorScheme.tertiaryContainer  
    )  
)  
Card(  
    modifier = Modifier  
        .height(120.dp)  
        .clickable{toSensorScreen()}  
        .fillMaxWidth(0.8f),  
    shape = RoundedCornerShape(32.dp)  
) {  
    Box(  
        modifier = Modifier  
            .fillMaxSize()  
            .background(horizontalGradientBrush, alpha = 0.5f)  
            .height(100.dp)  
            .fillMaxWidth(0.8f),  
    ){}
    }
```