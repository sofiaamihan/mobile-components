package com.example.boilerplatecode.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackScreen(){
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Column (
            Modifier.padding(start = 24.dp, top = 32.dp)
        ){
            IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back") }
        }
    }
}