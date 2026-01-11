# Android Manifest
``` XML
<uses-feature  
    android:name="android.hardware.camera"  
    android:required="false" />  
  
<uses-permission android:name="android.permission.CAMERA" />  
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"  
    android:maxSdkVersion="32"  
    tools:ignore="ScopedStorage" />  
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"  
    android:maxSdkVersion="32" />  
<uses-permission android:name="android.permission.RECORD_AUDIO" />

<application
	<provider  
	    android:name="androidx.core.content.FileProvider"  
	    android:authorities="${applicationId}.provider"  
	    android:exported="false"  
	    android:grantUriPermissions="true">  
	    <meta-data        android:name="android.support.FILE_PROVIDER_PATHS"  
	        android:resource="@xml/provider_paths" />  
	</provider>
</application>
```

# res / xml / `provider_paths.xml`
``` XML
<?xml version="1.0" encoding="utf-8"?>  
<paths>  
    <cache-path name="cache" path="."/>  
</paths>
```

# Create File Function
``` Kotlin
fun createVideoFile(context: Context): Uri {  
    val videoFile = File.createTempFile("video", ".mp4", context.cacheDir)  
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", videoFile)  
}
```

# Take and Display
``` Kotlin
@Composable  
fun VideoScreen(){  
    val context = LocalContext.current  
  
    var permission by remember { mutableStateOf(context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }  
    var permissionActiviy = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permission = it }  
  
    var currentUri by remember { mutableStateOf<Uri?>(null) }  
    var videoList by remember { mutableStateOf<List<Uri>>(listOf()) }  
    var videoToShow by remember { mutableStateOf<Uri?>(null) }  
  
    var videoActivity = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) { success ->  
        if(success){  
            videoList = videoList + currentUri!!  
        }  
    }  
  
    if (videoToShow != null){  
        var started by remember { mutableStateOf(false) }  
        var play by remember { mutableStateOf(false) }  
        var end by remember { mutableStateOf(false) }  
        val mediaPlayer = remember { MediaPlayer() }  
        Column (  
            modifier = Modifier.fillMaxSize(),  
            verticalArrangement = Arrangement.Center,  
            horizontalAlignment = Alignment.CenterHorizontally  
        ){  
            AndroidView(  
                modifier = Modifier  
                    .height(500.dp)  
                    .fillMaxWidth(),  
                factory = { ctx ->  
                    SurfaceView(ctx).apply {  
                        holder.addCallback(  
                            object: SurfaceHolder.Callback {  
                                override fun surfaceCreated(holder: SurfaceHolder) {  
                                    mediaPlayer.apply {  
                                        setDataSource(context, videoToShow!!)  
                                        setOnCompletionListener{  
                                            end = true  
                                        }  
                                        prepare()  
                                        setDisplay(holder)  
                                    }  
                                }  
  
                                override fun surfaceChanged(  
                                    holder: SurfaceHolder,  
                                    format: Int,  
                                    width: Int,  
                                    height: Int  
                                ) {  
                                }  
  
                                override fun surfaceDestroyed(p0: SurfaceHolder) {  
                                }  
                            }  
                        )  
                    }  
                }            )  
            Spacer(modifier = Modifier.height(10.dp))  
            Row {  
                Button(onClick = {  
                    started = true  
                    if(!end) {  
                        if(play){  
                            mediaPlayer.pause()  
                        } else {  
                            mediaPlayer.start()  
                        }  
                        play = !play  
                    }  
                }, enabled = !end) { Text(if(play) "Pause" else "Play") }  
                Button(onClick = {  
                    mediaPlayer.release()  
                    videoToShow = null  
                }) { Text("Cancel") }  
                Button(onClick = {  
                    play = false  
                    end = false  
                    mediaPlayer.seekTo(0)  
                    mediaPlayer.pause()  
                }) { Text("Reset") }  
            }        }    } else {  
        LazyColumn (  
            modifier = Modifier.fillMaxSize(),  
            verticalArrangement = Arrangement.Center,  
            horizontalAlignment = Alignment.CenterHorizontally  
        ){  
            item{  
                Button(onClick = {  
                    if(permission){  
                        currentUri = createVideoFile(context)  
                        videoActivity.launch(currentUri!!)  
                    } else {  
                        permissionActiviy.launch(android.Manifest.permission.CAMERA)  
                    }  
                }) { Text("Take Video") }  
            }            items(videoList.size){ index ->  
                Button(  
                    onClick = {videoToShow = videoList[index]}, modifier = Modifier.fillMaxWidth(0.8f)  
                ) { Text("Play Video ${index+1}") }  
            }        
        }    
    }  
}
```

# Take and Display - With Slider
``` Kotlin
@Composable  
fun VideoScreen(){  
    val context = LocalContext.current  
    val coroutineScope = rememberCoroutineScope()  
    var permission by remember {  
        mutableStateOf(context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)  
    }  
    val permissionActivity = rememberLauncherForActivityResult( ActivityResultContracts.RequestPermission() ){  
        permission = it  
    }    var currentUri by remember { mutableStateOf<Uri?>(null) }  
    var videoList by rememberSaveable { mutableStateOf<List<Uri>>(listOf()) }  
    val cameraActivity = rememberLauncherForActivityResult( ActivityResultContracts.CaptureVideo() ) {  
        if (it) {  
            videoList = videoList + currentUri !!  
        }  
    }  
    var videoToShow by rememberSaveable { mutableStateOf<Uri?>(null) }  
  
    if (videoToShow != null) {  
        var started by remember { mutableStateOf(false) }  
        var play by remember { mutableStateOf(false) }  
        var end by remember { mutableStateOf(false) }  
        var progress by remember { mutableFloatStateOf(0f) }  
        val mediaPlayer = remember { MediaPlayer() }  
        Column (  
            modifier = Modifier  
                .fillMaxSize()  
                .background(Color.Black),  
            verticalArrangement = Arrangement.Center,  
            horizontalAlignment = Alignment.CenterHorizontally  
        ){  
            AndroidView(  
                modifier = Modifier  
                    .fillMaxWidth()  
                    .height(500.dp),  
                factory = { ctx ->  
                    SurfaceView(ctx).apply {  
                        holder.addCallback(  
                            object : SurfaceHolder.Callback {  
                                override fun surfaceCreated(holder: SurfaceHolder) {  
                                    mediaPlayer.apply {  
                                        setDataSource(context, videoToShow!!)  
                                        setOnCompletionListener{  
                                            end = true  
                                            progress = duration.toFloat()  
                                        }  
                                        setOnPreparedListener {  
                                            coroutineScope.launch(Dispatchers.IO) {  
                                                while (true) {  
                                                    if (play) {  
                                                        progress = currentPosition.toFloat() / duration.toFloat()  
                                                    }  
                                                    delay(500)  
                                                }  
                                            }  
                                        }                                        prepare()  
                                        setDisplay(holder)  
                                    }  
                                }  
  
                                override fun surfaceChanged(  
                                    holder: SurfaceHolder,  
                                    format: Int,  
                                    width: Int,  
                                    height: Int  
                                ) {  
                                }  
  
                                override fun surfaceDestroyed(p0: SurfaceHolder) {  
                                }  
  
                            }  
                        )  
                    }  
                }            )  
            Spacer(Modifier.height(10.dp))  
            Row (horizontalArrangement = Arrangement.spacedBy(10.dp)){  
                Button(  
                    onClick = {  
                        started = true  
                        if(!end) {  
                            if(play) {  
                                mediaPlayer.pause()  
                            } else {  
                                mediaPlayer.start()  
                            }  
                            play = !play  
                        }  
                    },  
                    enabled = !end  
                ) { Text( if(play) "Pause" else "Play") }  
                Button(  
                    onClick = {  
                        mediaPlayer.release()  
                        videoToShow = null  
                    }  
                ) { Text("Cancel") }  
                Button(  
                    onClick = {  
                        mediaPlayer.seekTo(0)  
                        mediaPlayer.pause()  
                        play = false  
                        end = false  
                        progress = 0f  
                    }  
                ) { Text("Reset") }  
            }            Slider(  
                modifier = Modifier  
                    .fillMaxWidth()  
                    .padding(horizontal = 20.dp),  
                value = progress,  
                valueRange = 0f..1f,  
                onValueChange = {  
                    if(started){  
                        mediaPlayer.pause()  
                        play = false  
                        progress = it  
                    }  
                },  
                onValueChangeFinished = {  
                    end = false  
                    mediaPlayer.seekTo((progress*mediaPlayer.duration).toInt())  
                }  
            )  
        }  
    } else {  
        LazyColumn (  
            modifier = Modifier  
                .fillMaxSize(),  
            verticalArrangement = Arrangement.Center,  
            horizontalAlignment = Alignment.CenterHorizontally  
        ){  
            item{  
                Button(  
                    onClick = {  
                        if(permission) {  
                            currentUri = createVideoFile(context)  
                            cameraActivity.launch(currentUri!!)  
                        } else {  
                            permissionActivity.launch(android.Manifest.permission.CAMERA)  
                        }  
                    }  
                ) {  
                    Text("Take Video")  
                }  
            }            items(videoList.size){ index ->  
                Button(  
                    onClick = { videoToShow = videoList[index] }  
                ) { Text("Play video ${index + 1}") }  
            }        
        }    
    }  
}
```

