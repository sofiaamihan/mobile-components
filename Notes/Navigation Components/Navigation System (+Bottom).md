#  Navigation Flow
1. **screen**: Create the base layout of all the screens 
	- Add parameters for those using onClick() buttons to navigate to different pages (only button)
2. **class**: BottomNavigationScreen
3. **function**: RootNavGraph
4. **function**: Corresponding nested nav graphs
5. **screen**: Main content, where the bottom navigation will reside
6. **code**: Main activity
Remove `route =` if there are no other graphs

##  `BottomNavigationScreen`
```Kotlin
sealed class BottomNavigationScreen(  
    val route: String,  
    val title: String,  
    val icon: ImageVector  
){  
    object Orders: BottomNavigationScreen(  
        route = "ORDERS",  
        title = "ORDERS",  
        icon = Icons.Filled.ShoppingCart  
    )  
  
    object Home: BottomNavigationScreen(  
        route = "HOME",  
        title = "HOME",  
        icon = Icons.Filled.Home  
    )  
  
    object Profile: BottomNavigationScreen(  
        route = "PROFILE",  
        title = "PROFILE",  
        icon = Icons.Filled.Person  
    )  
}
```

##   `RootNavGraph`
```Kotlin
@Composable  
fun RootNavigationGraph(navController: NavHostController){  
    NavHost(  
        navController = navController,  
        route = Graph.ROOT,  
        startDestination = Graph.AUTHENTICATION  
    ){  
        authNavGraph(navController = navController)  
        composable(route = Graph.HOME){  
            MainContent()  
        }  
    }}  
  
object Graph {  
    const val ROOT = "root_graph"  
    const val AUTHENTICATION = "auth_graph"  
    const val HOME = "home_graph"  
    const val DETAILS = "details_graph"  
}
```

##   `AuthNavGraph`
```Kotlin
fun NavGraphBuilder.authNavGraph(navController: NavHostController){  
    navigation(  
        route = Graph.AUTHENTICATION,  
        startDestination = AuthScreen.Login.route  
    ){  
        composable(route = AuthScreen.Login.route){  
            LoginScreen(  
                onClick = {  
                    navController.popBackStack()  
                    navController.navigate(Graph.HOME)  
                },  
                onSignUpClick = {  
                    navController.navigate(AuthScreen.SignUp.route)  
                }  
            )  
        }  
        composable(route = AuthScreen.SignUp.route){  
            RegisterScreen(  
                onClick = {  
                    navController.popBackStack()  
                    navController.navigate(Graph.HOME)  
                },  
                onLoginClick = {  
                    navController.navigate(AuthScreen.Login.route)  
                }  
  
            )  
        }  
    }}  
  
sealed class AuthScreen(val route: String){  
    object Login: AuthScreen(route = "LOGIN")  
    object SignUp: AuthScreen(route = "SIGN_UP")  
}
```

##   `HomeNavGraph`
```Kotlin
@Composable  
fun HomeNavGraph(navController: NavHostController){  
    NavHost(  
        navController = navController,  
        route = Graph.HOME,  
        startDestination = BottomNavigationScreen.Home.route  
    ){  
        composable(route = BottomNavigationScreen.Orders.route){  
            OrdersScreen()  
        }  
        composable(route = BottomNavigationScreen.Home.route){  
            HomeScreen()  
        }  
        composable(route = BottomNavigationScreen.Profile.route){  
            UpdateProfileScreen()  
        }  
        detailsNavGraph(navController = navController)  
    }  
}  
  
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController){  
    navigation(  
        route = Graph.DETAILS,  
        startDestination = DetailsScreen.Merchant.route  
    ){  
        composable(route = DetailsScreen.Merchant.route){  
            MerchantScreen()  
        }  
    }}  
  
sealed class DetailsScreen(val route: String){  
    object Merchant: DetailsScreen(route = "MERCHANT")  
}
```

##   `MainContent` (Creating the BottomBar)
```Kotlin
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")  
@Composable  
@Preview(showSystemUi = true)  
fun MainContent(navController: NavHostController = rememberNavController()){  
    var search = remember { mutableStateOf("") }  
    Scaffold(  
        bottomBar = { BottomBar(navController = navController)}  
    ) {  
        HomeNavGraph(navController = navController)  
    }  
}  
  
@Composable  
fun BottomBar(navController: NavHostController) {  
    val screens = listOf(  
        BottomNavigationScreen.Orders,  
        BottomNavigationScreen.Home,  
        BottomNavigationScreen.Profile,  
    )  
    val navBackStackEntry by navController.currentBackStackEntryAsState()  
    val currentDestination = navBackStackEntry?.destination  
  
    val bottomBarDestination = screens.any { it.route == currentDestination?.route  }  
    if(bottomBarDestination){  
        NavigationBar {  
            screens.forEach{ screen ->  
                AddItem (  
                    screen = screen,  
                    currentDestination = currentDestination,  
                    navController = navController  
                )  
  
            }  
  
        }  
    }  
}  
  
@Composable  
fun RowScope.AddItem(  
    screen: BottomNavigationScreen,  
    currentDestination: NavDestination?,  
    navController: NavHostController  
){  
    NavigationBarItem(  
        label = {  
            Text(text = screen.title)  
        },  
        icon = {  
            Icon(  
                imageVector = screen.icon,  
                contentDescription = "Navigation Icon"  
            )  
        },  
        selected = currentDestination?.hierarchy?.any {  
            it.route == screen.route  
        } == true,  
        onClick = {  
            navController.navigate(screen.route) {  
                popUpTo(navController.graph.findStartDestination().id)  
                launchSingleTop = true  
            }  
        }  
    )  
}
```

##   MainActivity
```Kotlin
RootNavigationGraph(navController = rememberNavController())
```
