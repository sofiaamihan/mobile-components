# Syntax
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun AppBar(title: String){  
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())  
    Scaffold (  
        modifier = Modifier  
            .nestedScroll(scrollBehavior.nestedScrollConnection),  
        topBar = {  
            CenterAlignedTopAppBar(  
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
				floatingActionButton = {  // FAB is the param inside BottomAppBar NOT Scaffold
					FloatingActionButton(  
						onClick = { /* do something */ },  
						containerColor = BottomAppBarDefaults.bottomAppBarFabColor,  
						elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()  
					) {  
						Icon(Icons.Filled.Add, "Localized description")  
					}  
				}    )  
			},  
    ){  
        var padding = it  
    }
}
```
- However, this would just layer the top app bar at the bottom of the content when applied
- Better usage would be to insert content into the scaffold, allowing the app bar to sit on top of the content for pinned scrolling
``` Kotlin
// Inserting content into scaffold composable
@Composable
fun AppBar(  
    title: String,  
    content: @Composable (PaddingValues) -> Unit  
){
    Scaffold(){ paddingValues ->  
        content(paddingValues)  
    }
}

// Application
fun AppBarsScreen(){  
    AppBar(  
        "Test"  
    ){  
        Contents()  
    }
}
```
- For creating back buttons,
# Types
- `TopAppBar`
- `CenterAlignedTopAppBar`
- `MediumTopAppBar`
- `LargeTopAppBar`
Generally, all of them work the same way it just depends which one you want to use

# Modifications
1. `title`
2. `navigationIcon` - LEFT
3. `actions` - RIGHT (Acts as a row so you can stack icon buttons after one another)
4. `scrollBehavior`
5. `colors`

# Scrolls
``` Kotlin
// Normal
val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())  

// Medium becomes Small when Scrolled
val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
```

