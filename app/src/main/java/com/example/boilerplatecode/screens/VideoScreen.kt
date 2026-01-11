package com.example.boilerplatecode.screens

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun VideoScreen(){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var permission by remember {
        mutableStateOf(context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }
    val permissionActivity = rememberLauncherForActivityResult( ActivityResultContracts.RequestPermission() ){
        permission = it
    }
    var currentUri by remember { mutableStateOf<Uri?>(null) }
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
                }
            )
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
            }
            Slider(
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
            }
            items(videoList.size){ index ->
                Button(
                    onClick = { videoToShow = videoList[index] }
                ) { Text("Play video ${index + 1}") }
            }
        }
    }
}

fun createVideoFile(context: Context): Uri {
    val videoFile = File.createTempFile("video", ".mp4", context.cacheDir)
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", videoFile)
}

