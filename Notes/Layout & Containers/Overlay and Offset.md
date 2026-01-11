# Pins on Map
``` Kotlin
Box (  
    modifier = Modifier.fillMaxSize()  
){  
    Image(  
        painter = painterResource(R.drawable.map),  
        contentDescription = "map",  
        modifier = Modifier.fillMaxSize(),  
        contentScale = ContentScale.FillHeight  
    )  
    IconButton(  
        onClick = {  
        },  
        modifier = Modifier  
            .offset(x = 100.dp, y = 100.dp,)  
    ) {  
        Icon(Icons.Default.LocationOn, "Location", tint = Color.Red, modifier = Modifier.size(80.dp))  
    }  
}
```

- Everything must be in the box in order for the offset to work
- Must specify which is `x` and `y` for coordinates
- Offset must go in the Icon button not the Icon