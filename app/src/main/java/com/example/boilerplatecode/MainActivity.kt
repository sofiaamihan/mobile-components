package com.example.boilerplatecode
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.boilerplatecode.ui.theme.BoilerplateCodeTheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.boilerplatecode.data.CoursesViewModel
import com.example.boilerplatecode.data.CoursesViewModelFactory
import com.example.boilerplatecode.graphs.RootNavigationGraph
import com.example.boilerplatecode.viewmodels.SensorViewModel

class MainActivity : ComponentActivity() {
    private lateinit var sensorViewModel: SensorViewModel
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorViewModel = ViewModelProvider(this)[SensorViewModel::class.java]
        sensorViewModel.startListening()
        val viewModelFactory = CoursesViewModelFactory(application)
        val viewModel: CoursesViewModel = ViewModelProvider(this, viewModelFactory)[CoursesViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            BoilerplateCodeTheme {
                RootNavigationGraph(
                    navController = rememberNavController(),
                    sensorViewModel = sensorViewModel,
                    coursesViewModel = viewModel
                )
            }
        }
    }
}
