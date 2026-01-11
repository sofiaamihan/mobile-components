``` Kotlin
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
```