``` Kotlin
var checkedState by remember { mutableStateOf(false) }  
Row(  
    verticalAlignment = Alignment.CenterVertically,  
    horizontalArrangement = Arrangement.spacedBy(8.dp)  
) {  
    Checkbox(  
        checked = checkedState,  
        onCheckedChange = { checkedState = it }  
    )  
    Text(text = if (checkedState) "Checked" else "Unchecked")  
}
```

- Maybe try to code multiple without reference to anything