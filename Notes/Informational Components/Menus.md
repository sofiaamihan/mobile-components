Generally, drop-down menus let users click an icon, text field, or other component, and then select from a list of options on a temporary surface.

# Basic
``` Kotlin
var expanded by remember { mutableStateOf(false) }  
Box(  
    modifier = Modifier  
        .padding(16.dp)  
) {  
    IconButton(onClick = { expanded = !expanded }) {  
        Icon(Icons.Default.MoreVert, contentDescription = "More options")  
    }  
    DropdownMenu(  
        expanded = expanded,  
        onDismissRequest = { expanded = false }  
    ) {  
        DropdownMenuItem(  
            text = { Text("Option 1") },  
            onClick = { /* Do something... */ }  
        )  
        DropdownMenuItem(  
            text = { Text("Option 2") },  
            onClick = { /* Do something... */ }  
        )  
    }  
}
```

# Detailed
``` Kotlin
var expanded2 by remember { mutableStateOf(false) }  
Box(  
    modifier = Modifier  
        .padding(16.dp)  
) {  
    IconButton(onClick = { expanded2 = !expanded2 }) {  
        Icon(Icons.Default.MoreVert, contentDescription = "More options")  
    }  
    DropdownMenu(  
        expanded = expanded2,  
        onDismissRequest = { expanded2 = false }  
    ) {  
        // First section  
        DropdownMenuItem(  
            text = { Text("Profile") },  
            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },  
            onClick = { /* Do something... */ }  
        )  
        DropdownMenuItem(  
            text = { Text("Settings") },  
            leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },  
            onClick = { /* Do something... */ }  
        )  
        HorizontalDivider()  
        // Second section  
        DropdownMenuItem(  
            text = { Text("Send Feedback") },  
            leadingIcon = { Icon(Icons.Outlined.Feedback, contentDescription = null) },  
            trailingIcon = { Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = null) },  
            onClick = { /* Do something... */ }  
        )  
        HorizontalDivider()  
        // Third section  
        DropdownMenuItem(  
            text = { Text("About") },  
            leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },  
            onClick = { /* Do something... */ }  
        )  
        DropdownMenuItem(  
            text = { Text("Help") },  
            leadingIcon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },  
            trailingIcon = { Icon(Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null) },  
            onClick = { /* Do something... */ }  
        )  
    }  
}
```