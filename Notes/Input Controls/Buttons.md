# Normal
``` Kotlin
Button( onClick = {} ) { Text("Normal Button") }
```

# Text
``` Kotlin
TextButton( onClick = {} ) { Text("Text Button") }
```

# Elevated
``` Kotlin
ElevatedButton( onClick = {} ) { Text("Elevated Button") }
```

# Outlined
``` Kotlin
OutlinedButton( onClick = {} ) { Text("Outlined Button") }
```

# Tonal
``` Kotlin
FilledTonalButton( onClick = {} ) { Text("Tonal Button ")}
```

# Rounded Corner
``` Kotlin
Button( onClick = {}, shape = RoundedCornerShape(20)) { Text("Rounded Corner Button") }
```

# Rectangle
``` Kotlin
Button( onClick = {}, shape = RectangleShape ) { Text("Rectangle Button") }
```

# Circle
``` Kotlin
Button( onClick = {}, shape = CircleShape, modifier = Modifier.size(60.dp) ) { Text("C") }
```

# Coloured
``` Kotlin
Button( onClick = {}, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Coloured Button") }
```

# Icon and Text
``` Kotlin
Button( onClick = {} ) {  
    Icon(Icons.Filled.Favorite, contentDescription = "Favourite")  
    Text("Text and Icon Button")  
}
```

# Badged
``` Kotlin
Box(modifier = Modifier.padding(8.dp)) {  
    Button( onClick = {} ) { Text("Badged Button") }  
    Badge(  
        modifier = Modifier  
            .align(Alignment.TopEnd)  
            .offset(x = (-4).dp, y = 4.dp)  
    ) { Text("3") }  
}
```

# Gradient 
``` Kotlin
val horizontalGradientBrush = Brush.horizontalGradient(  
    colors = listOf(  
        MaterialTheme.colorScheme.primary,  
        MaterialTheme.colorScheme.tertiary  
    )  
)  
Button( onClick = {}, colors = ButtonDefaults.buttonColors(Color.Transparent) ) {  
    Box(  
        modifier = Modifier  
            .clip(RoundedCornerShape(50.dp))  
            .background(horizontalGradientBrush)  
    ){  
        Text("Gradient Button", modifier = Modifier.padding(10.dp))  
    }  
}
```

# Floating Action Button
``` Kotlin
FloatingActionButton(  
    onClick = {},  
    containerColor = MaterialTheme.colorScheme.tertiaryContainer  
) {  
    Text("Floating Action Button", modifier = Modifier.padding(5.dp))  
}
```

# Segmented
``` Kotlin
var selectedIndex = remember { mutableIntStateOf(0) }  
val options = listOf("Choice 1", "Choice 2", "Choice 3")  
SingleChoiceSegmentedButtonRow {  
    options.forEachIndexed { index, label ->  
        SegmentedButton(  
            shape = SegmentedButtonDefaults.itemShape(  
                index = index,  
                count = options.size  
            ),  
            onClick = {  
                selectedIndex.intValue = index  
                if (index == 0) {  
                } else {  
                }  
            },  
            selected = index == selectedIndex.intValue,  
            label = { Text(label) }  
        )  
    }  
}
```

# Icon
``` Kotlin
IconButton( onClick = {} ) { Icon(Icons.Default.Home, contentDescription = "Home") }
```

# Image
``` Kotlin
val image: Painter = painterResource(id = R.drawable.logo)  
Image(  
    painter = image,  
    contentDescription = "Image Button",  
    contentScale = ContentScale.Crop,  
    modifier = Modifier  
        .size(80.dp)  
        .clickable(onClick = {})  
)
```

# Toggle
``` Kotlin
var isToggled by remember { mutableStateOf(true) }  
Button(  
    onClick = { isToggled = !isToggled },  
    modifier = Modifier.padding(8.dp)  
) {  
    Icon(  
        imageVector = if (!isToggled) Icons.Default.PlayArrow else Icons.Default.Close,  
        contentDescription = if (isToggled) "Toggled" else "Not Toggled"  
    )  
    Spacer(modifier = Modifier.width(8.dp))  
    Text(if (isToggled) "Not Toggled" else "Toggled")  
}
```