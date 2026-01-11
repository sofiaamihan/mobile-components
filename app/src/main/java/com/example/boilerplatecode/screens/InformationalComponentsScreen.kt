package com.example.boilerplatecode.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun InformationalComponentsScreen(){

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // -------------------- CARD --------------------
        Card (
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.8f)
                .padding(10.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){ Text("Informational Components") }
        }

        // -------------------- TOAST --------------------
        val context = LocalContext.current
        Button ( onClick = {
                Toast.makeText(context, "This is a toast that will pop you", Toast.LENGTH_SHORT).show()
        }){ Text("Show Toast") }

        // -------------------- DETERMINATE CIRCULAR PROGRESS INDICATOR --------------------
        var currentProgress = remember { mutableFloatStateOf(0f) }
        var loading = remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        Box{
            if (loading.value) {
                CircularProgressIndicator(
                    progress = { currentProgress.floatValue },
                    modifier = Modifier.fillMaxWidth(0.1f),
                )
            } else {
                Button(onClick = {
                    loading.value = true
                    scope.launch {
                        loadProgress { progress ->
                            currentProgress.floatValue = progress
                        }
                        loading.value = false
                    }
                }, enabled = !loading.value) {
                    Text("Show Determinate Circular Progress Indicator")
                }
            }
        }

        // -------------------- DETERMINATE LINEAR PROGRESS INDICATOR --------------------
        var currentProgress2 = remember { mutableFloatStateOf(0f) }
        var loading2 = remember { mutableStateOf(false) }
        val scope2 = rememberCoroutineScope()
        Box{
            if (loading2.value) {
                LinearProgressIndicator(
                    progress = { currentProgress2.floatValue },
                    modifier = Modifier.fillMaxWidth(0.1f),
                )
            } else {
                Button(onClick = {
                    loading2.value = true
                    scope2.launch {
                        loadProgress { progress ->
                            currentProgress2.floatValue = progress
                        }
                        loading2.value = false
                    }
                }, enabled = !loading2.value) {
                    Text("Show Determinate Linear Progress Indicator")
                }
            }
        }

        // -------------------- INDETERMINATE CIRCULAR PROGRESS INDICATOR --------------------
        var currentProgress3 = remember { mutableFloatStateOf(0f) }
        var loading3 = remember { mutableStateOf(false) }
        val scope3 = rememberCoroutineScope()
        Box{
            if (loading3.value) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth(0.1f),
                )
            } else {
                Button(onClick = {
                    loading3.value = true
                    scope3.launch {
                        loadProgress { progress ->
                            currentProgress3.floatValue = progress
                        }
                        loading3.value = false
                    }
                }, enabled = !loading3.value) {
                    Text("Show Indeterminate Circular Progress Indicator")
                }
            }
        }

        // -------------------- INDETERMINATE LINEAR PROGRESS INDICATOR --------------------
        var currentProgress4 = remember { mutableFloatStateOf(0f) }
        var loading4 = remember { mutableStateOf(false) }
        val scope4 = rememberCoroutineScope()
        Box{
            if (loading4.value) {
                LinearProgressIndicator(
                    progress = { currentProgress4.floatValue },
                    modifier = Modifier.fillMaxWidth(0.1f),
                )
            } else {
                Button(onClick = {
                    loading4.value = true
                    scope4.launch {
                        loadProgress { progress ->
                            currentProgress4.floatValue = progress
                        }
                        loading4.value = false
                    }
                }, enabled = !loading4.value) {
                    Text("Show Indeterminate Linear Progress Indicator")
                }
            }
        }

        // -------------------- SNACKBAR --------------------
        val snackbarHostState = remember { SnackbarHostState() }
        val scope5 = rememberCoroutineScope()
        Button ( onClick = {
            scope5.launch {
                snackbarHostState.showSnackbar("This is a snackbar that will pop up")
            }
        }){ Text("Show Snackbar") }
        SnackbarHost(hostState = snackbarHostState) { snackbarData ->
            Snackbar(snackbarData)
        }

        // -------------------- BOTTOM SHEET --------------------
        val sheetState = rememberModalBottomSheetState()
        val scope6 = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(false) }
        Button ( onClick = {
                showBottomSheet = true
        }){ Text("Show Bottom Sheet") }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope6.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }

        // -------------------- ALERT DIALOG / MODAL --------------------
        val openAlertDialog = remember { mutableStateOf(false) }
        Button ( onClick = {
            openAlertDialog.value = true
        }){ Text("Show Alert Dialog / Modal") }
        when {
            openAlertDialog.value -> {
                AlertDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    confirmButton = { },
                    dismissButton = { },
                    title = { Text("Alert dialog example") },
                    text = { Text("This is an example of an alert dialog with buttons.")},
                    icon = {Icon(Icons.Default.Info, contentDescription = "Icon")}
                )
            }
        }

        // -------------------- BADGES --------------------
        var cartItemCount = remember { mutableIntStateOf(0) }
        fun addToCart() {
            cartItemCount.intValue = cartItemCount.intValue + 1
        }
        Box(modifier = Modifier.wrapContentSize()) {
            IconButton(onClick = { addToCart() }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "My Cart"
                )
            }
//            val cartItemCount = null
            if (cartItemCount.intValue > 0) {
                Badge(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd),
                    content = { Text(text = cartItemCount.intValue.toString()) },
                )
            }
        }

        // -------------------- MENU --------------------
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Option 1") },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Option 2") },
                    onClick = { /* Do something... */ }
                )
            }
        }

        // -------------------- DETAILED MENU --------------------
        var expanded2 by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            IconButton(onClick = { expanded2 = !expanded2 }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false }
            ) {
                // First section
                DropdownMenuItem(
                    text = { Text("Profile") },
                    leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Settings") },
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
                HorizontalDivider()
                // Second section
                DropdownMenuItem(
                    text = { Text("Send Feedback") },
                    leadingIcon = { Icon(Icons.Outlined.Feedback, contentDescription = null) },
                    trailingIcon = { Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
                HorizontalDivider()
                // Third section
                DropdownMenuItem(
                    text = { Text("About") },
                    leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
                DropdownMenuItem(
                    text = { Text("Help") },
                    leadingIcon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },
                    trailingIcon = { Icon(Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null) },
                    onClick = { /* Do something... */ }
                )
            }
        }

    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..50) {
        updateProgress(i.toFloat() / 50)
        delay(50)
    }
}
