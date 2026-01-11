``` Kotlin
// Root Nav Graph
val coroutineScope = rememberCoroutineScope()  
var startDestination by remember { mutableStateOf(Graph.AUTHENTICATION) }  
  
LaunchedEffect(Unit) {  
    coroutineScope.launch{  
        val token = tokenDataStore.getToken.first()  
        startDestination = if (!token.isNullOrEmpty()) Graph.HOME else Graph.AUTHENTICATION  
    }  
}
```
- coroutine scope above is redundant? launched effect already introduces a coroutine
- Same logic for viewing the onboarding screen once - use token data store to store either yes or no