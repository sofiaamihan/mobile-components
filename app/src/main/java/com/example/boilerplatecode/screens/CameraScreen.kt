package com.example.boilerplatecode.screens

import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.boilerplatecode.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun createFile(context: Context): Uri {
    val file = File.createTempFile("image.png", context.cacheDir.name)
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

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
    }
}
