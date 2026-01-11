package com.example.boilerplatecode.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.boilerplatecode.viewmodels.SensorViewModel

@Composable
fun SensorsScreen(
    sensorViewModel: SensorViewModel
){
    val accValues by sensorViewModel.accelerometerData.collectAsState()
    val gyroValues by sensorViewModel.gyroscopeData.collectAsState()
    val tempValues by sensorViewModel.ambientTemperatureData.collectAsState()
    val lightValues by sensorViewModel.lightData.collectAsState()
    val pressureValues by sensorViewModel.pressureData.collectAsState()
    val humidityValues by sensorViewModel.relativeHumidityData.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Accelerometer", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("X:" + accValues[0].toString())
        Text("Y:" + accValues[1].toString())
        Text("Z:" + accValues[2].toString())
        Text(text = "Gyroscope", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("X:" + gyroValues[0].toString())
        Text("Y:" + gyroValues[1].toString())
        Text("Z:" + gyroValues[2].toString())
        Text(text = "Ambient Temperature", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("$tempValues °C")
        Text(text = "Light", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("$lightValues lx")
        Text(text = "Pressure", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("$pressureValues hPa")
        Text(text = "Relative Humidity", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("$humidityValues %")
    }
}