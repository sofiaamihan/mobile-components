package com.example.boilerplatecode.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.boilerplatecode.data.CoursesViewModel
import com.example.boilerplatecode.screens.AccordionScreen
import com.example.boilerplatecode.screens.AppBarsScreen
import com.example.boilerplatecode.screens.BackScreen
import com.example.boilerplatecode.screens.ButtonsScreen
import com.example.boilerplatecode.screens.CameraScreen
import com.example.boilerplatecode.screens.ChipsScreen
import com.example.boilerplatecode.screens.DatePickersScreen
import com.example.boilerplatecode.screens.HomeScreen
import com.example.boilerplatecode.screens.InformationalComponentsScreen
import com.example.boilerplatecode.screens.LocalDatabaseScreen
import com.example.boilerplatecode.screens.NavigationDrawer
import com.example.boilerplatecode.screens.NavigationDrawerScreen
import com.example.boilerplatecode.screens.ScaffoldScreen
import com.example.boilerplatecode.screens.SensorsScreen
import com.example.boilerplatecode.screens.SlidersScreen
import com.example.boilerplatecode.screens.TabScreen
import com.example.boilerplatecode.screens.TimePickerScreen
import com.example.boilerplatecode.screens.VideoScreen
import com.example.boilerplatecode.viewmodels.SensorViewModel

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    sensorViewModel: SensorViewModel,
    coursesViewModel: CoursesViewModel
){
    NavHost(
        navController = navController,
        startDestination = "HOME"
    ) {
        composable("HOME"){
            HomeScreen(
                toAccordionScreen = {
                    navController.navigate("ACCORDION")
                },
                toAppBarsScreen = {
                    navController.navigate("APP_BAR")
                },
                toBackScreen = {
                    navController.navigate("BACK")
                },
                toButtonsScreen = {
                    navController.navigate("BUTTONS")
                },
                toCameraScreen = {
                    navController.navigate("CAMERA")
                },
                toChipsScreen = {
                    navController.navigate("CHIPS")
                },
                toDatePickersScreen = {
                    navController.navigate("DATE_PICKERS")
                },
                toInformationalComponentsScreen = {
                    navController.navigate("INFORMATIONAL")
                },
                toLocalDatabaseScreen = {
                    navController.navigate("LOCAL")
                },
                toNavigationDrawerScreen = {
                    navController.navigate("DRAWER")
                },
                toScaffoldScreen = {
                    navController.navigate("SCAFFOLD")
                },
                toSensorsScreen = {
                    navController.navigate("SENSORS")
                },
                toSlidersScreen = {
                    navController.navigate("SLIDERS")
                },
                toTabsScreen = {
                    navController.navigate("TAB")
                },
                toTimePickersScreen = {
                    navController.navigate("TIME")
                },
                toVideoScreen = {
                    navController.navigate("VIDEO")
                },

            )
        }
        composable("ACCORDION"){
            AccordionScreen()
        }
        composable("APP_BAR"){
            AppBarsScreen()
        }
        composable("BACK"){
            BackScreen()
        }
        composable("BUTTONS"){
            ButtonsScreen()
        }
        composable("CAMERA"){
            CameraScreen()
        }
        composable("CHIPS"){
            ChipsScreen()
        }
        composable("DATE_PICKERS"){
            DatePickersScreen()
        }
        composable("INFORMATIONAL"){
            InformationalComponentsScreen()
        }
        composable("LOCAL"){
            LocalDatabaseScreen(coursesViewModel)
        }
        composable("DRAWER"){
            NavigationDrawerScreen()
        }
        composable("SCAFFOLD"){
            ScaffoldScreen()
        }
        composable("SENSORS"){
            SensorsScreen(sensorViewModel)
        }
        composable("SLIDERS"){
            SlidersScreen()
        }
        composable("TAB"){
            TabScreen()
        }
        composable("TIME"){
            TimePickerScreen()
        }
        composable("VIDEO"){
            VideoScreen()
        }
    }
}

