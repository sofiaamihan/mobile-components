# Data Class
``` Kotlin
data class TabItem(  
    val title: String,  
    val unselectedIcon: ImageVector,  
    val selectedIcon: ImageVector  
)
```
# Horizontal
``` Kotlin
@Composable  
fun TabScreen() {  
    val tabItems = listOf(  
        TabItem(  
            title = "Home",  
            unselectedIcon = Icons.Outlined.Home,  
            selectedIcon = Icons.Filled.Home  
        ),  
        TabItem(  
            title = "Browse",  
            unselectedIcon = Icons.Outlined.ShoppingCart,  
            selectedIcon = Icons.Filled.ShoppingCart  
        ),  
        TabItem(  
            title = "Account",  
            unselectedIcon = Icons.Outlined.AccountCircle,  
            selectedIcon = Icons.Filled.AccountCircle  
        ),  
    )  
    M3SwipeableTabRowTheme {  
        Surface(  
            modifier = Modifier.fillMaxSize(),  
            color = MaterialTheme.colorScheme.background  
        ) {  
            var selectedTabIndex by remember { mutableIntStateOf(0) }  
            val pagerState = rememberPagerState { tabItems.size }  
            LaunchedEffect(selectedTabIndex) {  
                pagerState.animateScrollToPage(selectedTabIndex)  
            }  
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {  
                if(!pagerState.isScrollInProgress) {  
                    selectedTabIndex = pagerState.currentPage  
                }  
            }  
            
            Column(  
                modifier = Modifier  
                    .fillMaxSize()  
            ) {  
                TabRow(  
                    modifier = Modifier.padding(top = 64.dp),  
                    selectedTabIndex = selectedTabIndex) {  
                    tabItems.forEachIndexed { index, item ->  
                        Tab(  
                            selected = index == selectedTabIndex,  
                            onClick = {  
                                selectedTabIndex = index  
                            },  
                            text = {  
                                Text(text = item.title)  
                            },  
                            icon = {  
                                Icon(  
                                    imageVector = if (index == selectedTabIndex) {  
                                        item.selectedIcon  
                                    } else item.unselectedIcon,  
                                    contentDescription = item.title  
                                )  
                            }  
                        )  
                    }  
                }  
                HorizontalPager(  
                    state = pagerState,  
                    modifier = Modifier  
                        .fillMaxWidth()  
                        .weight(1f)  
                ) { index ->  
                    Box(  
                        modifier = Modifier.fillMaxSize(),  
                        contentAlignment = Alignment.Center  
                    ) {  
                        Text(text = tabItems[index].title)  
                    }  
                }            
            }        
        }    
    }
}
```

# Vertical
``` Kotlin
@Composable  
fun VerticalTabScreen() {  
    val tabItems = listOf(  
        TabItem(  
            title = "Home",  
            unselectedIcon = Icons.Outlined.Home,  
            selectedIcon = Icons.Filled.Home  
        ),  
        TabItem(  
            title = "Browse",  
            unselectedIcon = Icons.Outlined.ShoppingCart,  
            selectedIcon = Icons.Filled.ShoppingCart  
        ),  
        TabItem(  
            title = "Account",  
            unselectedIcon = Icons.Outlined.AccountCircle,  
            selectedIcon = Icons.Filled.AccountCircle  
        ),  
    )  
  
    M3SwipeableTabRowTheme {  
        Surface(  
            modifier = Modifier.fillMaxSize(),  
            color = MaterialTheme.colorScheme.background  
        ) {  
            var selectedTabIndex by remember {  
                mutableIntStateOf(0)  
            }  
            val pagerState = rememberPagerState {  
                tabItems.size  
            }  
            LaunchedEffect(selectedTabIndex) {  
                pagerState.animateScrollToPage(selectedTabIndex)  
            }  
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {  
                if (!pagerState.isScrollInProgress) {  
                    selectedTabIndex = pagerState.currentPage  
                }  
            }  
  
            Row(modifier = Modifier.fillMaxSize()) {  
                // Sidebar Tabs (Left Column)  
                Column(  
                    modifier = Modifier  
                        .fillMaxHeight()  
                        .width(100.dp)  
                        .background(MaterialTheme.colorScheme.surfaceVariant),  
                    horizontalAlignment = Alignment.CenterHorizontally,  
                    verticalArrangement = Arrangement.Top  
                ) {  
                    tabItems.forEachIndexed { index, item ->  
                        Column(  
                            modifier = Modifier  
                                .fillMaxWidth()  
                                .clickable { selectedTabIndex = index }  
                                .padding(vertical = 16.dp),  
                            horizontalAlignment = Alignment.CenterHorizontally  
                        ) {  
                            Icon(  
                                imageVector = if (index == selectedTabIndex) {  
                                    item.selectedIcon  
                                } else item.unselectedIcon,  
                                contentDescription = item.title,  
                                tint = if (index == selectedTabIndex) MaterialTheme.colorScheme.primary  
                                else MaterialTheme.colorScheme.onSurface  
                            )  
                            Text(  
                                text = item.title,  
                                color = if (index == selectedTabIndex) MaterialTheme.colorScheme.primary  
                                else MaterialTheme.colorScheme.onSurface,  
                                style = MaterialTheme.typography.labelMedium  
                            )  
                        }  
                    }                }  
                // Content Area (Right Side)  
                HorizontalPager( 














                    modifier = Modifier  
                        .fillMaxSize()  
                        .weight(1f)  
                ) { index ->  
                    Box(  
                        modifier = Modifier.fillMaxSize(),  
                        contentAlignment = Alignment.Center  
                    ) {  
                        Text(text = tabItems[index].title)  
                    }  
                }            }        }    }}
```