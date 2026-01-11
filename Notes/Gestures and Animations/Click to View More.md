``` Kotlin
@Composable  
fun ExpandableCard() {  
    var isExpanded by remember { mutableStateOf(false) }  
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +  
            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +  
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."  
  
    Card(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(16.dp),  
        elevation = CardDefaults.cardElevation(4.dp)  
    ) {  
        Column(  
            modifier = Modifier  
                .padding(16.dp)  
                .animateContentSize() // Smooth expansion  
        ) {  
            Text(  
                text = text,  
                maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Show full text when expanded  
                overflow = TextOverflow.Ellipsis,  
                fontSize = 16.sp  
            )  
  
            if (!isExpanded) {  
                TextButton(onClick = { isExpanded = true }) {  
                    Text("View More", color = Color.Blue)  
                }  
            }  
        }  
    }
}
```