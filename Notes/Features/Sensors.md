# Sensors
## Motion
- Accelerometer
- Gyroscope
## Environment
- Ambient Temperature
- Light
- Pressure
- Relative Humidity

# Android Manifest
``` XML
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />  
<uses-permission android:name="android.permission.HIGH_SAMPLING_RATE_SENSORS"  
    tools:ignore="HighSamplingRate" />  
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"  
    tools:ignore="CoarseFineLocation" />  
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

# SensorViewModel
``` Kotlin
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
```

# Screen
``` Kotlin
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
```

# Main Activity
``` Kotlin
class MainActivity : ComponentActivity() {  

    private lateinit var sensorViewModel: SensorViewModel  // UNO
    
    @OptIn(ExperimentalMaterial3Api::class)  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        
        sensorViewModel = ViewModelProvider(this)[SensorViewModel::class.java]  // DOS
        sensorViewModel.startListening()  // TRES

        enableEdgeToEdge()  
        setContent {  
            BoilerplateCodeTheme {  
                SensorsScreen(sensorViewModel)
            }
        }
    }
}
```

# TILT - Type Rotation Vector
- tilt is apparently [1], y values if you only want to use the gyroscope but theres also type rotation vector:
``` Kotlin
private val rotationVector: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)  
  
private val _rotationVectorData = MutableStateFlow(listOf(0f, 0f, 0f, 0f)) 
val rotationVectorData: StateFlow<List<Float>> = _rotationVectorData

fun startListening() {
    rotationVector?.let {  
	    sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)  
	}
}

override fun onSensorChanged(event: SensorEvent?) {
    viewModelScope.launch {
        when (event?.sensor?.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {  
			    val rotationMatrix = FloatArray(9)  
			    val orientationAngles = FloatArray(3)  
			    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)  
			    SensorManager.getOrientation(rotationMatrix, orientationAngles)  
			  
			    val roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()  
			    val pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()  
			    val yaw = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()  
			  
			    _rotationVectorData.value = listOf(roll, pitch, yaw)  
			}
        }
    }
}
```

``` Kotlin
@Composable
fun SensorsScreen(
    sensorViewModel: SensorViewModel
) {
    val rotation by sensorViewModel.rotationVectorData.collectAsState()  
	val roll = rotation[0]  
	val backgroundColor = getColokrFromTilt(roll)
	// Display content however you wish
}


fun getColorFromTilt(tilt: Float): Color {  
    val normalizedTilt = (tilt + 180) / 360  
    val clampedTilt = normalizedTilt.coerceIn(0f, 1f)  
  
    val red = (1 - clampedTilt) * 255  
    val green = clampedTilt * 255  
    return Color(red.toInt(), green.toInt(), 0)  
}
```