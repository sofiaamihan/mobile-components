package com.example.boilerplatecode.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScaffoldScreen(){
    Scaffold (
        floatingActionButton = { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Icon") } },
        floatingActionButtonPosition = FabPosition.Center
    ){ innerPadding ->
        Text(
            "Floating Action Button Demo",
            modifier = Modifier.padding(innerPadding)
            )
    }
}