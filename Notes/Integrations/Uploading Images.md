# From Internet
## Loading Image
``` Kotlin
suspend fun loadImageFromUrl(url: String): Bitmap? {  
    return withContext(Dispatchers.IO) {  
        try {  
            val connection = URL(url).openConnection() as HttpURLConnection  
            connection.doInput = true  
            connection.connect()  
            val inputStream = connection.inputStream  
            BitmapFactory.decodeStream(inputStream)  
        } catch (e: Exception) {  
            e.printStackTrace()  
            null  
        }  
    }  
}
```
## Display
``` Kotlin
@Composable  
fun NetworkImage(url: String) {  
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }  
  
    LaunchedEffect(url) {  
        bitmap = loadImageFromUrl(url)  
    }  
  
    Box(  
        modifier = Modifier  
            .size(200.dp)  
            .clip(CircleShape)  
            .background(Color.Gray),  
        contentAlignment = Alignment.Center  
    ) {  
        bitmap?.let {  
            Image(  
                bitmap = it.asImageBitmap(),  
                contentDescription = null,  
                contentScale = ContentScale.Crop,  
                modifier = Modifier.matchParentSize().clip(CircleShape)  
            )  
        } ?: CircularProgressIndicator()  
    }  
}
```

# From Camera
- Refer to [[Camera]]

# From Assets
## Directory
```
app
 |-- src
      |-- main
           |--assets
```
## Loading Image
``` Kotlin
@Composable  
fun LoadImageFromAssets(imageFileName: String) {  
    val context = LocalContext.current  
    val bitmap: Bitmap? = remember(imageFileName) {  
        try {  
            val inputStream = context.assets.open(imageFileName) // Open file from assets  
            BitmapFactory.decodeStream(inputStream) // Convert to Bitmap  
        } catch (e: Exception) {  
            e.printStackTrace()  
            null  
        }  
    }  
  
    bitmap?.let {  
        Image(  
            painter = BitmapPainter(it.asImageBitmap()),  
            contentDescription = "Image from assets",  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(200.dp),  
            contentScale = ContentScale.Crop  
        )  
    } ?: Text("Failed to load image")  
}
```
## Display
``` Kotlin
LoadImageFromAssets("image.jpg")
```