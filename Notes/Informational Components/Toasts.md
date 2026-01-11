``` Kotlin
Column {
    val context = LocalContext.current
    Button (
        onClick = {
            Toast.makeText(context, "This is a toast that will pop you", Toast.LENGTH_SHORT).show()
        }
    ){
        Text("Show Toast")
    }
}
```