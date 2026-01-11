- I used an upload icon I got from the internet to replace the black background
# Android Manifest
``` XML
<uses-feature  
    android:name="android.hardware.camera"  
    android:required="false" />  
  
<uses-permission android:name="android.permission.CAMERA" />  
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"  
    android:maxSdkVersion="32"  
    tools:ignore="ScopedStorage" />

<application
	<provider  
	    android:name="androidx.core.content.FileProvider"  
	    android:authorities="${applicationId}.provider"  
	    android:exported="false"  
	    android:grantUriPermissions="true">  
	    <meta-data        android:name="android.support.FILE_PROVIDER_PATHS"  
	        android:resource="@xml/file_paths" />  
	</provider>
</application>
```

# res / xml / `file_paths.xml`
``` XML
<?xml version="1.0" encoding="utf-8"?>  
<paths xmlns:android="http://schemas.android.com/apk/res/android">  
    <cache-path name="images" path="." />  
    <external-path name="external_files" path="." />  
</paths>
```

# Dependencies
`libs.versions.toml`
``` TOML
[versions]
coilCompose = "3.1.0"

[libraries]
coil-compose = { module = "io.coil-kt.coil3:coil-compose", version.ref = "coilCompose" }
```
`build.gradle.kts`
``` Kotlin
implementation(libs.coil.compose)
```

# Create File Function
``` Kotlin
fun createFile(context: Context): Uri {  
    val file = File.createTempFile("image.png", context.cacheDir.name)  
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)  
}
```

# Loading Bitmap Function
``` Kotlin
fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {  
    return try {  
        context.contentResolver.openInputStream(uri)?.use { inputStream ->  
            BitmapFactory.decodeStream(inputStream)  
        }  
    } catch (e: Exception) {  
        e.printStackTrace()  
        null  
    }  
}
```

# Camera
``` Kotlin
@Composable  
fun CameraZ(){  
    val context = LocalContext.current  
    var permission by remember { mutableStateOf(context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }  
    var permissionActivity = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permission = it }  
    var currentUri by remember { mutableStateOf<Uri?>(null) }  
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }  
  
    val cameraActivity = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->  
        if (success && currentUri != null) {  
            bitmap = loadBitmapFromUri(context, currentUri!!)   
        }  
    }  
  
    val galleryActivity = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->  
        if (uri != null) {  
            currentUri = uri  
            bitmap = loadBitmapFromUri(context, uri)  
        }  
    }  
  
  
    Column(  
        modifier = Modifier.fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ){  
        TextButton(  
            onClick = {  
                galleryActivity.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))  
            }  
        ) { Text("Gallery") }  
        TextButton(  
            onClick = {  
                if(permission){  
                    val uri = createFile(context)  
                    currentUri = uri  
                    cameraActivity.launch(uri)  
                } else {  
                    permissionActivity.launch(android.Manifest.permission.CAMERA)  
                }  
            }  
        ) { Text("Camera") }  
        Box(  
            modifier = Modifier  
                .size(200.dp)  
                .clip(CircleShape)  
                .background(Color.Black)  
        ){  
            bitmap?.let {  
                Image(  
                    bitmap = it.asImageBitmap(),  
                    contentDescription = null,  
                    contentScale = ContentScale.Crop,  
                    modifier = Modifier.matchParentSize().clip(CircleShape)  
                )  
            }  
        }    
    }
}
```

# Camera Profile (Old, withdialog, using Coil)
``` Kotlin
@Composable  
fun CameraScreen() {  
    val context = LocalContext.current  
    var showDialog by remember { mutableStateOf(false) }  
    var permission by remember { mutableStateOf(context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }  
    val permissionActivity = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permission = it }  
    var currentUri by remember { mutableStateOf<String?>(null) }  
    var photo by remember { mutableStateOf<String?>(null) }  
    val cameraActivity = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {  
        if (it) {  
            photo = currentUri  
            showDialog = false  
        }  
    }  
    val galleryActivity = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {  
        photo = it.toString()  
        showDialog = false  
    }  
  
    if (showDialog) {  
        Dialog(  
            onDismissRequest = {  
                showDialog = false  
            }  
        ) {  
            Card(  
                modifier = Modifier  
                    .fillMaxWidth()  
            ) {  
                Column(  
                    modifier = Modifier  
                        .fillMaxWidth()  
                        .padding(16.dp)  
                ) {  
                    Text("Select a picture from the gallery or take a picture")  
                    Row(  
                        modifier = Modifier  
                            .fillMaxWidth(),  
                        horizontalArrangement = Arrangement.SpaceBetween  
                    ) {  
                        TextButton(  
                            onClick = {  
                                galleryActivity.launch(PickVisualMediaRequest(  
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly  
                                ))  
                            }  
                        ) {  
                            Text("Gallery")  
                        }  
                        TextButton(  
                            onClick = {  
                                if (permission) {  
                                    val uri = createFile(context)  
                                    currentUri = uri.toString()  
                                    cameraActivity.launch(uri)  
                                } else {  
                                    permissionActivity.launch(android.Manifest.permission.CAMERA)  
                                }  
                            }  
                        ) {  
                            Text("Take Picture")  
                        }  
                        TextButton(  
                            onClick = {  
                                showDialog = false  
                            }  
                        ) {  
                            Text("Cancel")  
                        }  
                    }                
                }            
            }        
        }    
    }  
  
    Column (  
        modifier = Modifier.fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ){  
        Box(  
            modifier = Modifier  
                .size(200.dp)  
                .clip(CircleShape)  
                .clickable {  
                    showDialog = true  
                },  
        ) {  
            // Background image  
            Box (  
                modifier = Modifier  
                    .matchParentSize() // Make the foreground image fill the Box  
                    .clip(CircleShape), // Clip to circle shape  
                contentAlignment = Alignment.Center  
            ){  
                Image(  
                    painter = painterResource(R.drawable.upload),  
                    contentDescription = "Upload image",  
                    modifier = Modifier.size(100.dp)  
                )  
  
            }  
  
            // Foreground image  
            AsyncImage(  
                model = photo,  
                contentDescription = null,  
                contentScale = ContentScale.Crop,  
                modifier = Modifier  
                    .matchParentSize()  
                    .clip(CircleShape)  
            )  
        }  
    }}
```

