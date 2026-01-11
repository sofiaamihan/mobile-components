# Basic
``` Kotlin
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
```
- Snack bars will appear into the screen as an element at the bottom, this will PUSH the rest of the elements upwards
- In order to prevent this, snackbars are usually placed into scaffolds, such that it would appear above the screen content

# Scaffolded
``` Kotlin
@Composable  
fun Scaffolded(){  
    val scope = rememberCoroutineScope()  
    val snackbarHostState = remember { SnackbarHostState() }  
    Scaffold(  
        snackbarHost = {  
            SnackbarHost(hostState = snackbarHostState)  
        },  
        floatingActionButton = {  
            ExtendedFloatingActionButton(  
                text = { Text("Show snackbar") },  
                icon = { Icon(Icons.Filled.Image, contentDescription = "") },  
                onClick = {  
                    scope.launch {  
                        snackbarHostState.showSnackbar("Snackbar")  
                    }  
                }            )  
        }  
    ) { contentPadding ->  
        Text("Content")  
    }  
}
```
- contentPadding will appear red but the code should still be executable
- otherwise, replace with `var padding = it`

# With Action
``` Kotlin
val scope = rememberCoroutineScope()
val snackbarHostState = remember { SnackbarHostState() }
Scaffold(
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
    floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Show snackbar") },
            icon = { Icon(Icons.Filled.Image, contentDescription = "") },
            onClick = {
                scope.launch {
                    val result = snackbarHostState
                        .showSnackbar(
                            message = "Snackbar",
                            actionLabel = "Action",
                            // Defaults to SnackbarDuration.Short
                            duration = SnackbarDuration.Indefinite
                        )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            /* Handle snackbar action performed */
                        }
                        SnackbarResult.Dismissed -> {
                            /* Handle snackbar dismissed */
                        }
                    }
                }
            }
        )
    }
) { contentPadding ->
    // Screen content
}
```
# (WIP) Link with Repository Status