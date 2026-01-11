# Static
## Configurations
1. Create a `raw` directory in the `res` package to hold all the audio recordings
``` Kotlin
val context = LocalContext.current  
val mediaPlayer = MediaPlayer.create(context, R.raw.audio)
```
## Start
``` Kotlin
Button(  onClick = { mediaPlayer.start() }  ) {  Text("Play Audio")  }
```
## Pause
``` Kotlin
Button(  onClick = { mediaPlayer.pause() }  ) {  Text("Pause Audio")  }
```
## Stop
``` Kotlin
Button(  onClick = { mediaPlayer.stop() }  ) {  Text("Stop Audio")  }
```
## Play in background
``` Kotlin
// PUT IN MAIN ACTIVITY
val context = LocalContext.current  
val mediaPlayer = MediaPlayer.create(context, R.raw.audio)  
LaunchedEffect(Unit) {  
    mediaPlayer.start()  
}
```
## Adjust Volume 
``` Kotlin
var volume by remember { mutableStateOf(1.0f) }

Text("Volume: ${(volume * 100).toInt()}%")  
Slider(  
    value = volume,  
    onValueChange = { newVolume ->  
        volume = newVolume  
        mediaPlayer.setVolume(newVolume, newVolume)  
    },  
    valueRange = 0f..1f  
)
```

# Recording
## Configurations
### `AndroidManifest.xml`
``` Kotlin
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

## Audio Recorder Interface
``` Kotlin
interface AudioRecorder {  
    fun start(outputFile: File)  
    fun stop()  
}
```

## Audio Recorder Class
``` Kotlin
class AndroidAudioRecorder(private val context: Context) : AudioRecorder {  
  
    private var recorder: MediaRecorder? = null  
  
    private fun createRecorder(): MediaRecorder {  
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {  
            MediaRecorder(context)  
        } else MediaRecorder()  
    }  
  
    override fun start(outputFile: File) {  
        createRecorder().apply {  
            setAudioSource(MediaRecorder.AudioSource.MIC)  
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)  
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)  
            setOutputFile(outputFile.absolutePath)  
  
            prepare()  
            start()  
            recorder = this  
        }  
    }  
  
    override fun stop() {  
        recorder?.stop()  
        recorder?.reset()  
        recorder = null  
    }  
}
```

## Audio Player Interface
``` Kotlin
interface AudioPlayer {  
    fun playFile(file: File)  
    fun stop()  
}
```

## Audio Player Class
``` Kotlin
class AndroidAudioPlayer(private val context: Context) : AudioPlayer {  
  
    private var player: MediaPlayer? = null  
  
    override fun playFile(file: File) {  
        player?.release()  
        player = MediaPlayer().apply {  
            setDataSource(file.absolutePath)  
            prepare()  
            start()  
        }  
    }  
  
    override fun stop() {  
        player?.stop()  
        player?.release()  
        player = null  
    }  
}
```

## Application
### Audio Item
``` Kotlin
@Composable  
fun AudioFileItem(file: File, onPlay: () -> Unit) {  
    Card(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(8.dp)  
            .clickable { onPlay() }  
    ) {  
        Row(  
            modifier = Modifier.padding(16.dp),  
            verticalAlignment = Alignment.CenterVertically  
        ) {  
            Text(text = file.name, modifier = Modifier.weight(1f))  
            Button(onClick = onPlay) {  
                Text("Play")  
            }  
        }    
    }
}
```
### Screen
``` Kotlin
@Composable  
fun AudioRecorderScreen(applicationContext: Context) {  
    val recorder = remember { AndroidAudioRecorder(applicationContext) }  
    val player = remember { AndroidAudioPlayer(applicationContext) }  
  
    var isRecording by remember { mutableStateOf(false) }  
    var audioFiles by remember { mutableStateOf(listOf<File>()) }  
  
    Column(  
        modifier = Modifier.fillMaxSize().padding(16.dp),  
        verticalArrangement = Arrangement.Top,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ) {  
        Button(onClick = {  
            if (!isRecording) {  
                val fileName = "audio_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.mp3"  
                val audioFile = File(applicationContext.cacheDir, fileName)  
                recorder.start(audioFile)  
                isRecording = true  
            }  
        }) {  
            Text(text = if (isRecording) "Recording..." else "Start Recording")  
        }  
  
        Button(onClick = {  
            if (isRecording) {  
                recorder.stop()  
                isRecording = false  
                audioFiles = (audioFiles + (applicationContext.cacheDir.listFiles()  
                    ?.filter { it.extension == "mp3" } ?: listOf())).distinct()  
            }  
        }) {  
            Text(text = "Stop Recording")  
        }  
  
  
        Spacer(modifier = Modifier.height(20.dp))  
  
        Text("Recorded Files:")  
  
        LazyColumn(  
            modifier = Modifier.fillMaxSize(),  
            verticalArrangement = Arrangement.spacedBy(10.dp)  
        ) {  
            items(audioFiles.size) { index ->  
                var file = audioFiles[index]  
                AudioFileItem(file, onPlay = { player.playFile(file) })  
            }  
        }    
    }
}
```