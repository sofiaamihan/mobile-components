# Custom
``` Kotlin
@Composable  
fun HyperlinkText(url: String, text: String) {  
    val context = LocalContext.current  
  
    Text(  
        text = text,  
        style = MaterialTheme.typography.bodyLarge.copy(  
            color = Color.Blue,  
            textDecoration = TextDecoration.Underline,  
            fontWeight = FontWeight.Bold  
        ),  
        modifier = Modifier.clickable {  
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))  
            context.startActivity(intent)  
        }  
    )  
}
```

# Application
``` Kotlin
@Composable  
fun ClickableHyperlinkExample() {  
    Column(  
        modifier = Modifier.padding(16.dp).fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ) {  
        Text("Click the link below to visit the website:")  
        Spacer(modifier = Modifier.height(8.dp))  
        HyperlinkText(url = "https://www.google.com", text = "Open Google")  
    }  
}
```