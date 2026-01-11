# Today's Date
``` Kotlin
val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")  
val current = LocalDateTime.now().format(formatter)
```

# Calculating Top Padding
``` Kotlin
NavigationDrawer(  
    title = "Welcome Home",  
    toProfile = {toProfile()},  
    toHome = {toHome()}  
){ padding ->  
    Box(  
        modifier = Modifier  
            .padding(top = padding.calculateTopPadding())  
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))  
            .fillMaxWidth()  
            .height(80.dp)  
            .background(color = MaterialTheme.colorScheme.secondaryContainer),  
    ){
```