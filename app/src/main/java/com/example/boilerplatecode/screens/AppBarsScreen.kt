package com.example.boilerplatecode.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AppBarsScreen(){
    AppBar(
        "Test"
    ){
        Contents()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    content: @Composable (PaddingValues) -> Unit
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold (
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        letterSpacing = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        },
    ){ paddingValues ->
        content(paddingValues)
    }}

@Composable
fun Contents(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("HERE")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
        Text("Test")
    }

}

