# Basic Alert
``` Kotlin
val openAlertDialog = remember { mutableStateOf(false) }  
Button ( onClick = {  
    openAlertDialog.value = true  
}){ Text("Show Alert Dialog / Modal") }  
when {  
    openAlertDialog.value -> {  
        AlertDialog(  
            onDismissRequest = { openAlertDialog.value = false },  
            confirmButton = { },  
            dismissButton = { },  
            title = { Text("Alert dialog example") },  
            text = { Text("This is an example of an alert dialog with buttons.")},  
            icon = {Icon(Icons.Default.Info, contentDescription = "Icon")}  
        )  
    }  
}
```
- For forms, you can `enabled = isFormValid` to disable/enable the text button acting as a confirm button
- To modify content in the dialog, add elements INSIDE `text = {}` such as columns, etc
- Use Alert Dialogs instead of normal dialogs or else you need to manually specify the size and shape:
``` Kotlin
Dialog(onDismissRequest = { onDismissRequest() }) {  
        // Draw a rectangle shape with rounded corners inside the dialog  
        Card(  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(375.dp)  
                .padding(16.dp),  
            shape = RoundedCornerShape(16.dp),  
        ) {  
    }
}
```
- But for this one, you can customise content in the card itself
- Ultimately, alert dialog is more preferred