# Button
```Kotlin
@Composable  
fun CopyTextButton(textToCopy: String) {  
    val clipboardManager: ClipboardManager = LocalClipboardManager.current  
    val context: Context = LocalContext.current  
  
    IconButton(  
        onClick = {  
            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(textToCopy))  
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()  
        }  
    ) {  
        Icon(  
            Icons.Default.CopyAll,  
            contentDescription = "Copy"  
        )  
    }  
}
```

# Application
``` Kotlin
@Composable  
fun CopyTextExample() {  
    val text = "Hello, Jetpack Compose!"  
  
    Column(  
        modifier = Modifier.padding(16.dp).fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ) {  
        Text(text = text, style = MaterialTheme.typography.bodyLarge)  
        Spacer(modifier = Modifier.height(8.dp))  
        CopyTextButton(textToCopy = text)  
    }  
}
```