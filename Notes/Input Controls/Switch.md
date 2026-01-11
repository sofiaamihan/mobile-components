``` Kotlin
var isChecked by remember { mutableStateOf(false) }  
Row(  
    verticalAlignment = Alignment.CenterVertically,  
    horizontalArrangement = Arrangement.spacedBy(8.dp)  
) {  
    Switch(  
        checked = isChecked,  
        onCheckedChange = { isChecked = it },  
        colors = SwitchDefaults.colors(  
            checkedThumbColor = Color.Green,  
            uncheckedThumbColor = Color.Red  
        )  
    )  
}
```
