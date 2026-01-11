``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun Dos(){  
    var isRefreshing = remember { mutableStateOf(false) }  
    var scope = rememberCoroutineScope()  
  
    fun onRefresh(){  
        scope.launch{  
            isRefreshing.value = true  
            delay(5000)  
            isRefreshing.value = false  
        }  
    }  
    PullToRefreshBox(  
        isRefreshing.value, {onRefresh()}  
    ) {  
        LazyColumn(  
            modifier = Modifier.fillMaxSize(),  
            verticalArrangement = Arrangement.Center,  
            horizontalAlignment = Alignment.CenterHorizontally  
        ){  
            item{  
                Text("Dos")  
            }  
        }    
    }
}
```
- `1000` = 1 second
- the screen will auto re-compose during this composition