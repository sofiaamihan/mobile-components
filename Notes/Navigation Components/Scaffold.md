# Syntax
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

# Parameters
1. `floatingActionButton`
2. `topBar`
3. `bottomBar`

# Content Methods
## Ignoring Content
``` Kotlin
Scaffold(){
    var padding = it
}
```
## Content in Scaffold
``` Kotlin
Scaffold(){ innerPadding ->
    Column(
    modifier = Modifier.padding(innerPadding)
    ){}
}
```

## Hoisted Content
``` Kotlin
@Composable
fun Screen(
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(){ paddingValues ->
        content(paddingValues)
    }
}

// Application
Screen(){
    Text() // This is the content
}
```