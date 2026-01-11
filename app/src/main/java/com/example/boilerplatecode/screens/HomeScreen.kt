package com.example.boilerplatecode.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStreamReader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.boilerplatecode.ui.theme.BoilerplateCodeTheme
import com.example.boilerplatecode.ui.theme.M3SwipeableTabRowTheme

@Composable
fun HomeScreen(
    toAccordionScreen: () -> Unit,
    toAppBarsScreen: () -> Unit,
    toBackScreen: () -> Unit,
    toButtonsScreen: () -> Unit,
    toCameraScreen: () -> Unit,
    toChipsScreen: () -> Unit,
    toDatePickersScreen: () -> Unit,
    toInformationalComponentsScreen: () -> Unit,
    toLocalDatabaseScreen: () -> Unit,
    toNavigationDrawerScreen: () -> Unit,
    toScaffoldScreen: () -> Unit,
    toSensorsScreen: () -> Unit,
    toSlidersScreen: () -> Unit,
    toTabsScreen: () -> Unit,
    toTimePickersScreen: () -> Unit,
    toVideoScreen: () -> Unit,
){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toAccordionScreen()}) { Text("Accordion Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toAppBarsScreen()}) { Text("App Bars Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toBackScreen()}) { Text("Back Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toButtonsScreen()}) { Text("Buttons Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toCameraScreen()}) { Text("Camera Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toChipsScreen()}) { Text("Chips Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toDatePickersScreen()}) { Text("Date Pickers Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toInformationalComponentsScreen()}) { Text("Informational Components Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toLocalDatabaseScreen()}) { Text("Local Database Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toNavigationDrawerScreen()}) { Text("Navigation Drawer Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toScaffoldScreen()}) { Text("Scaffold Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toSensorsScreen()}) { Text("Sensors Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toSlidersScreen()}) { Text("Sliders Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toTabsScreen()}) { Text("Tabs Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toTimePickersScreen()}) { Text("Time Pickers Screen") }
        FilledTonalButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {toVideoScreen()}) { Text("Videos Screen") }
    }
}

// 1. Memorise the content
// 2. Take note of points on notebook
// 3. Test the individual code
// 4. Repeat cycle until the entire folder has been memorised
// 5. Re-test myself or something





