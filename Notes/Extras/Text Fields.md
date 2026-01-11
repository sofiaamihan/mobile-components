# Red Validation with supporting text
``` Kotlin
fun validate(info: String): String?{  
    if (info.length > 40){  
        return "This is too long"  
    }  
    return null  
}  
  
@Composable  
fun TestingTextFields(){  
    Column (  
        modifier = Modifier.fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ){  
        var info by remember { mutableStateOf("") }  
        var hasInteracted by remember { mutableStateOf(false) }  
        var errorMsg by remember { mutableStateOf<String?>(null) }  
  
        OutlinedTextField(  
            value = info,  
            onValueChange = {  
                info = it  
                hasInteracted = true  
                errorMsg = validate(it)  
            },  
            label = {Text("Information")},  
            isError = !errorMsg.isNullOrBlank() && hasInteracted,  
            supportingText = {  
               if (!errorMsg.isNullOrBlank()){  
                   Text(errorMsg!!, color = Color.Red)  
               }  
            }  
        )  
  
    }  
}
```
- If you need the validation display to only appear after the button has been clicked, then simply create a variable that would show whether or not the validation has been clicked

# Validation to appear only after the button has been clicked
Simply create a variable that would show whether or not the validation has been clicked

# Monitoring word count
1. Make the supporting text display the length of the variable assigned to the text field
2. Only allow the value to be changed if it's less than the max
Note: Theres some errors here where you can't delete characters once it has reached the character limit

# Integers and Doubles in Normal Screen
``` Kotlin
var testingInt by remember { mutableIntStateOf(0) }

value = testingInt.takeIf { it != 0 }?.toString() ?: "",  
onValueChange = {  
	if (it.isEmpty() || it.toIntOrNull() != null) {  
		testingInt = it.toIntOrNull() ?: 0  
	}
```
- The value does pre-fill normally and can be changed
- For double just replace everything accordingly

# Forms located in Modals
## Strings
```Kotlin
// Dialog
@Composable  
fun SampleDialog(  
    info: String,  
    onInfoChange: (String) -> Unit,  
    onDismiss: () -> Unit  
){  
    AlertDialog(  
        onDismissRequest = onDismiss,  
        confirmButton = {TextButton(onClick = onDismiss) { Text("Confirm") }},  
        text = {  
            OutlinedTextField(  
                value = info,  
                onValueChange = {  
                    onInfoChange(it)  
                },  
                label = {Text("Testing another method")}  
            )  
        }  
    )  
}

// Application
var info by remember { mutableStateOf("") }
if (showDialog){  
    SampleDialog(  
        onDismiss = {showDialog = false},  
        info = info,  
        onInfoChange = {info = it}  
    )  
}
```

## Int
``` Kotlin
// Change in Dialog
value = info.takeIf { it != 0 }?.toString() ?: "",  
onValueChange = {  
    onInfoChange(it.toIntOrNull() ?: 0)  
},

// Application remains the same
```

## Doubles
``` Kotlin
// Change in Dialog
value = info.takeIf { it != 0.0 }?.toString() ?: "",  
onValueChange = {  
    onInfoChange(it.toDoubleOrNull() ?: 0.0)  
},

// Application remains the same
```