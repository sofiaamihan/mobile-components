# Open
```Kotlin
val isDropDownExpanded = remember { mutableStateOf(false) }  
val roles = listOf("Current Location", "Tampines")  
val itemPosition = remember { mutableStateOf(0) }

Row(  
    horizontalArrangement = Arrangement.Start,  
    modifier = Modifier  
        .fillMaxWidth()  
){  
    Box{  
        Row(  
            horizontalArrangement = Arrangement.Start,  
            modifier = Modifier  
                .clickable {  
                    isDropDownExpanded.value = true  
                }  
                .padding(start = 24.dp)  
        ) {  
  
            Text(  
                text = roles[itemPosition.value],  
                fontSize = 11.sp,  
                textAlign = TextAlign.Left,  
                lineHeight = 12.sp,  
                modifier = Modifier  
                    .padding(top = 10.dp, start = 10.dp)  
            )  
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Arrow down")  
        }  
        DropdownMenu(  
            expanded = isDropDownExpanded.value,  
            onDismissRequest = {  
                isDropDownExpanded.value = false  
            },  
        ) {  
            roles.forEachIndexed { index, role ->  
                DropdownMenuItem(  
                    text = { Text(  
                        role,  
                        fontSize = 10.sp,  
                        textAlign = TextAlign.Left,  
                        lineHeight = 12.sp,  
                        modifier = Modifier  
                            .padding(top = 10.dp, start = 10.dp)  
                    ) },  
                    onClick = {  
                        isDropDownExpanded.value = false  
                        itemPosition.value = index  
                    }  
                )  
            }  
        }    
    }
}
```

# Outlined
```Kotlin
val context = LocalContext.current  
val roles = listOf("Admin", "User")  
val roleState = remember { mutableStateOf("") }  
val selectedTextState = remember { mutableStateOf(roles[0]) }  
val expanded = remember { mutableStateOf(false) }

ExposedDropdownMenuBox(  
    expanded = expanded.value,  
    onExpandedChange = {  
        expanded.value = !expanded.value  
    }  
) {  
    TextField(  
        value = selectedTextState.value,  
        onValueChange = {},  
        readOnly = true,  
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)},  
        modifier = Modifier.menuAnchor()  
    )
    ExposedDropdownMenu(  
        expanded = expanded.value,  
        onDismissRequest = { expanded.value = false }  
    ) {  
        roles.forEach{ item ->  
            DropdownMenuItem(  
                text = { Text(text = item) },  
                onClick = {  
                    selectedTextState.value = item  
                    roleState.value = item  
                    expanded.value = false  
                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()  
                }  
            )  
        }  
    }
}
```
- `onContentCategoryIdChange(index+1)` - when taking note of the index to match
- `menuAnchor()` - IS NEEDED FOR THIS
- `roleState` - is only needed if you intent to do something with the value chosen but I honestly don't see the necessity