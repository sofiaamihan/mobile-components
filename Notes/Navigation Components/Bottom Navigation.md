# Initialise Screen Class
``` Kotlin
sealed class BottomNavigationScreens(  
    val route: String,  
    val title: String,  
    val icon: ImageVector  
){  
    object Attractions: BottomNavigationScreens("ATTRACTIONS", "ATTRACTIONS", Icons.Default.LocationOn)  
    object Home: BottomNavigationScreens("HOME", "HOME", Icons.Default.Home)  
    object Profile: BottomNavigationScreens("PROFILE", "PROFILE", Icons.Default.Person)  
}
```

# Create Navigation Graph
``` kotlin
@Composable  
fun HomeNavGraph(navController: NavHostController){  
    NavHost(  
        navController = navController,  
        startDestination = BottomNavigationScreens.Home.route  
    ) {  
        composable(BottomNavigationScreens.Home.route){HomeScreen()}  
        composable(BottomNavigationScreens.Attractions.route){Attractions()}  
        composable(BottomNavigationScreens.Profile.route){ProfileScreen()}  
    }}
```

# Main Content
``` Kotlin
@Composable  
fun MainContent(navController: NavHostController = rememberNavController()){  
    Scaffold (  
        bottomBar = {BottomBar(navController)}  
    ){  
        var padding = it  
        HomeNavGraph(navController)  
    }  
}
```

# Bottom Bar
``` Kotlin
@Composable  
fun BottomBar(navController: NavHostController){  
    val screens = listOf(  
        BottomNavigationScreens.Attractions,  
        BottomNavigationScreens.Home,  
        BottomNavigationScreens.Profile  
    )  
  
    val navBackStackEntry by navController.currentBackStackEntryAsState()  
    val currentDestination = navBackStackEntry?.destination  
    var bottomNavigationDestination = screens.any { it.route == currentDestination?.route }  
    if (bottomNavigationDestination){  
        NavigationBar {  
            screens.forEach{ screen ->  
                NavigationBarItem(  
                    selected = currentDestination?.route == screen.route,  
                    label = {Text(screen.title)},  
                    icon = {Icon(screen.icon, "Icon")},  
                    onClick = {  
                        navController.navigate(screen.route){  
                            popUpTo(navController.graph.startDestinationId)  
                            launchSingleTop = true  
                        }  
                    }                )  
            }  
        }    }  
}
```