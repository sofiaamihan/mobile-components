# DiscoverScreen -> BlogScreen
``` Kotlin
composable(route = BottomNavigationScreen.Discover.route){  
    DiscoverScreen(  
        tokenDataStore = tokenDataStore,  
        toProfile = {  
            navController.navigate(DrawerScreen.Profile.route)  
        },  
        toHealthLogs = {  
            navController.navigate(DrawerScreen.HealthLogs.route)  
        },  
        toHealthReport = {  
            navController.navigate(DrawerScreen.HealthReport.route)  
        },  
        toHome = {  
            navController.navigate(BottomNavigationScreen.Home.route)  
        },  
        viewModelFactory = discoverServiceViewModelFactory,  
        toDiscoverScreen = {  
            navController.navigate(BottomNavigationScreen.Discover.route)  
        }  
    ){ id, title, summary, description, contentCategoryId, role->  
        navController.navigate("${DiscoverScreen.Blog.route}/$id/$title/$summary/$description/$contentCategoryId/$role")  
    }  
}  
composable(route = "${DiscoverScreen.Blog.route}/{id}/{title}/{summary}/{description}/{contentCategoryId}/{role}"){ backStackEntry ->  
    val id: String = backStackEntry.arguments?.getString("id") ?: "0"  
    val title: String = backStackEntry.arguments?.getString("title") ?: ""  
    val summary: String = backStackEntry.arguments?.getString("summary") ?: ""  
    val description: String = backStackEntry.arguments?.getString("description") ?: ""  
    val contentCategoryId: String = backStackEntry.arguments?.getString("contentCategoryId") ?: "0"  
    val role: String = backStackEntry.arguments?.getString("role") ?: ""  
    BlogScreen(  
        id = id.toInt(),  
        title = title,  
        summary = summary,  
        description = description,  
        contentCategoryId = contentCategoryId.toInt(),  
        viewModelFactory = discoverServiceViewModelFactory,  
        toDiscoverScreen = {  
            navController.navigate(BottomNavigationScreen.Discover.route)  
        },  
        role = role  
    )  
}

// ----- APPLICATION -----
toBlogScreen: (Int, String, String, String, Int, String) -> Unit,
```

1. `navController.popBackStack()` - removes the current destination from the stack (you can specify tho) + USE THIS TO NAVIGATE BACKWARDS !! 
2. `backStackEntry` - getting arguments from the current destination in the stack
3. `navController.navigate("home") {popUpTo("login") { inclusive = true }}` - `popUpTo` controls how many destinations to remove from the back stack
4. `launchSingleTop = true` - prevents multiple instances of the same destination on the back stack

# Sign Out
``` Kotlin
coroutineScope.launch {  
    tokenDataStore.clearSession()  
    navController.navigate(Graph.AUTHENTICATION) {  
        popUpTo(Graph.ROOT) { inclusive = true }  
    }
}
```
