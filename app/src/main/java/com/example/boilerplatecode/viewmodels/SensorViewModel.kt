package com.example.boilerplatecode.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensorViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {
    private val sensorManager: SensorManager =
        application.getSystemService(SensorManager::class.java)

    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private val ambientTemperature: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    private val light: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    private val pressure: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    private val relativeHumidity: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

    private val _accelerometerData = MutableStateFlow(listOf(0f, 0f, 0f))
    val accelerometerData: StateFlow<List<Float>> = _accelerometerData

    private val _gyroscopeData = MutableStateFlow(listOf(0f, 0f, 0f))
    val gyroscopeData: StateFlow<List<Float>> = _gyroscopeData

    private val _ambientTemperatureData = MutableStateFlow(0f)
    val ambientTemperatureData: StateFlow<Float> = _ambientTemperatureData

    private val _lightData = MutableStateFlow(0f)
    val lightData: StateFlow<Float> = _lightData

    private val _pressureData = MutableStateFlow(0f)
    val pressureData: StateFlow<Float> = _pressureData

    private val _relativeHumidityData = MutableStateFlow(0f)
    val relativeHumidityData: StateFlow<Float> = _relativeHumidityData

    fun startListening() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        ambientTemperature?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        light?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        pressure?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
        relativeHumidity?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        viewModelScope.launch {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    _accelerometerData.value = listOf(event.values[0], event.values[1], event.values[2])
                }
                Sensor.TYPE_GYROSCOPE -> {
                    _gyroscopeData.value = listOf(event.values[0], event.values[1], event.values[2])
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    _ambientTemperatureData.value = event.values[0]
                }
                Sensor.TYPE_LIGHT -> {
                    _lightData.value = event.values[0]
                }
                Sensor.TYPE_PRESSURE -> {
                    _pressureData.value = event.values[0]
                }
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    _relativeHumidityData.value = event.values[0]
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}