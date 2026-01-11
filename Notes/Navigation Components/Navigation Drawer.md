# Syntax
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun NavigationDrawer(){  
	val items = listOf("Home", "Profile", "Discover")
    var selectedItemIndex by rememberSaveable {  mutableStateOf(0)  }  
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)  
    val scope = rememberCoroutineScope()  
    ModalNavigationDrawer(  
        drawerState = drawerState,  
        drawerContent = {  
            ModalDrawerSheet {  
                Spacer(modifier = Modifier.height(16.dp)) //space (margin) from top  
                items.forEachIndexed { index, item ->  
                    NavigationDrawerItem(  
                        label = { Text(text = item.title) },  
                        selected = index == selectedItemIndex,  
                        onClick = {  
                            // ----- INCLUDE ROUTE LOGIC HERE -----
                            if (index == 0) {  
							    toProfile()  
							}
  
                            selectedItemIndex = index  
                            scope.launch {  
                                drawerState.close()  
                            }  
                        },  
                        modifier = Modifier  
                            .padding(NavigationDrawerItemDefaults.ItemPadding) //padding between items  
                    )  
                }  
  
            }        },  
        gesturesEnabled = true  
    ) {  
        Scaffold(  
            topBar = {
                TopAppBar(  
                    title = {  
                        Text(text = "Navigation Drawer Example")  
                    },  
                    navigationIcon = {  
                        IconButton(onClick = {  
                            scope.launch {  
                                drawerState.apply {  
                                    if (isClosed) open() else close()  
                                }  
                            }                        }) {  
                            Icon(  //Show Menu Icon on TopBar  
                                imageVector = Icons.Default.Menu,  
                                contentDescription = "Menu"  
                            )  
                        }  
                    }                )  
            }  
        ) {  
            var padding = it  
        }        
    }  
}
```
- Remember that the top app bar has to be in the scaffold of the side drawer, not the other way around
- You can place anything in the `ModalDrawerSheet`, it sort of acts like a column 
# Modifications for `NavigationDrawerItem`
1. `label`
2. `selected` - Boolean
3. `onClick`
4. `icon` - Appears before label
5. `badge` - You can put a text that will appear on the right-most side, suggesting there are items unread or whatever

# Others
- `.padding(top = padding.calculateTopPadding())`
- `gesturesEnabled = false`: Keeps background content interactive while drawer is open.