```Kotlin
var selectedOption by remember { mutableStateOf("Option 1") }  
Column {  
    Text(text = "Choose an option:", fontSize = 16.sp)  
    listOf("Option 1", "Option 2", "Option 3").forEach { option ->  
        Row(  
            verticalAlignment = Alignment.CenterVertically,  
            modifier = Modifier.clickable { selectedOption = option }  
        ) {  
            RadioButton(  
                selected = (selectedOption == option),  
                onClick = { selectedOption = option }  
            )  
            Text(text = option)  
        }  
    }}
```