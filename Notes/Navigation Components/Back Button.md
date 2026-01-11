Generally, I like putting it into a top app bar so that it stays fixed in place when the user scrolls and it's easier to read, but a simple box works fine

# Box
``` Kotlin
Box(  
    modifier = Modifier.fillMaxWidth()  
){  
    Column (  
        Modifier.padding(start = 24.dp, top = 32.dp)  
    ){  
        IconButton(onClick = {}) { Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back") }  
    }
}
```

# App Bar
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun AppBar(  
    modifier: Modifier = Modifier,  
    title: String,  
    toHome: () -> Unit  
){  
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())  
    Scaffold (  
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),  
        topBar = {  
            TopAppBar(  
                title = { Text(title) },  
                scrollBehavior = scrollBehavior,  
                navigationIcon = {  
                    IconButton(onClick = {  
                        toHome()  
                    }) {  
                        Icon(  
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,  
                            contentDescription = "Back"  
                        )  
                    }  
                },  
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(  
                    containerColor = MaterialTheme.colorScheme.secondaryContainer  
                )  
            )  
        }  
    ){  
        var padding = it  
    }}
```