# Entity
```kotlin
@Entity(tableName = "activity")
data class Activity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "time_taken")
    val timeTaken: String, 
    @ColumnInfo(name = "calories_burnt")
    val caloriesBurnt: Double,
    @ColumnInfo(name = "step_count")
    val stepCount: Double,
    @ColumnInfo(name = "distance")
    val distance: Double,
    @ColumnInfo(name = "walking_speed")
    val walkingSpeed: Double,
    @ColumnInfo(name = "walking_steadiness")
    val walkingSteadiness: Double,
)
```

# DAO
```kotlin
@Dao
interface ActivityDao {
    @Query ("SELECT * FROM activity WHERE user_id= :userId AND DATE(time_taken)= :date")
    fun getAllActivities(userId: Int, date: String): List<Activity>

    @Upsert
    suspend fun addActivity(activity: Activity)

    @Query("DELETE FROM activity WHERE id = :id")
    suspend fun deleteActivity(id: Int)
}
```

# Database
```kotlin
@Database(
    entities = [User::class, Activity::class, Category::class, Medication::class, Time::class],
    version = 7,
    exportSchema = false
)
abstract class HealthServiceDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val activityDao: ActivityDao 
    abstract val medicationDao: MedicationDao
    abstract val categoryDao: CategoryDao
    abstract val timeDao: TimeDao

    companion object {
        @Volatile
        private var INSTANCE: HealthServiceDatabase? = null

        fun getDatabase(context: Context): HealthServiceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthServiceDatabase::class.java,
                    "health_service_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

# Repository
```kotlin
data class ActivityResponse(
    val id: Int,
    val userId: Int,
    val categoryId: Int,
    val timeTaken: String,
    val caloriesBurnt: Double,
    val stepCount: Double,
    val distance: Double,
    val walkingSpeed: Double,
    val walkingSteadiness: Double
)

class HealthServiceRepository(
    private val tokenDataStore: TokenDataStore,
    private val context: Context
){
    private val database = HealthServiceDatabase.getDatabase(context)
    private val userDao = database.userDao
    private val activityDao = database.activityDao
    private val medicationDao = database.medicationDao
    private val categoryDao = database.categoryDao
    private val timeDao = database.timeDao

    private val baseUrl = "<https://f5fqqafe6e.execute-api.us-east-1.amazonaws.com>"

    suspend fun getActivities(
        userId: Int,
        date: String
    ): Result<List<ActivityResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("$baseUrl/activity/$userId/$date")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val token = tokenDataStore.getToken.first()
                if (token != null) {
                    connection.setRequestProperty("Authorization", token)
                    Log.d("Token Here", token)
                } else {
                    Log.e("Health Service Repository", "Token is null")
                    return@withContext Result.Error(Exception("Token is null"))
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonArray = JSONArray(response)
                    val contentList = mutableListOf<ActivityResponse>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonResponse = jsonArray.getJSONObject(i)
                        val activities = ActivityResponse(
                            id = jsonResponse.getInt("id"),
                            userId = jsonResponse.getInt("user_id"),
                            categoryId = jsonResponse.getInt("category_id"),
                            timeTaken = jsonResponse.getString("time_taken"),
                            caloriesBurnt = jsonResponse.getDouble("calories_burnt"),
                            stepCount = jsonResponse.getDouble("step_count"),
                            distance = jsonResponse.getDouble("distance"),
                            walkingSpeed = jsonResponse.getDouble("walking_speed"),
                            walkingSteadiness = jsonResponse.getDouble("walking_steadiness")
                        )
                        val activity = Activity(
                            id = activities.id,
                            userId = activities.userId,
                            categoryId = activities.categoryId,
                            timeTaken = activities.timeTaken,
                            caloriesBurnt = activities.caloriesBurnt,
                            stepCount = activities.stepCount,
                            distance = activities.distance,
                            walkingSpeed = activities.walkingSpeed,
                            walkingSteadiness = activities.walkingSteadiness
                        )
                        contentList.add(activities)
                        Log.d("Got Activities !!", "$activities")
                        activityDao.addActivity(activity)
                    }
                    val cachedActivities = activityDao.getAllActivities(userId, date)
                    val activityList = mutableListOf<ActivityResponse>()
                    for (i in cachedActivities) {
                        val activity = ActivityResponse(
                            id = i.id,
                            userId = i.userId,
                            categoryId = i.categoryId,
                            timeTaken = i.timeTaken,
                            caloriesBurnt = i.caloriesBurnt,
                            stepCount = i.stepCount,
                            distance = i.distance,
                            walkingSpeed = i.walkingSpeed,
                            walkingSteadiness = i.walkingSteadiness
                        )
                        activityList.add(activity)
                    }

                    Log.d("HealthServiceRepo", "Successfully cached: $activityList")
                    return@withContext Result.Success(activityList)
                } else {
                    return@withContext Result.Error(Exception("Activities not found in cache"))
                }

            } catch (e: Exception) {
                Log.e(
                    "HealthServiceRepository",
                    "Error getting categories: ${e.localizedMessage}",
                    e
                )
                return@withContext Result.Error(e)
            }
        }
    }

    suspend fun getAllActivities(
        userId: Int,
        date: String
    ): List<Activity> {
        return withContext(Dispatchers.IO) {
            return@withContext activityDao.getAllActivities(userId, date)
        }
    }

}
```

# View Model
```kotlin
class GetActivitiesViewModel(
    private val healthServiceRepository: HealthServiceRepository,
): ViewModel() {
    var state by mutableStateOf(HealthResultState())
    fun getActivities(
        userId: Int,
        date: String
    ) {
        viewModelScope.launch {
            state = state.copy(loadingState = true)

            when (val response = healthServiceRepository.getActivities(userId, date)) {
                is Result.Success -> {
                    state = state.copy(
                        successState = true,
                        activityList = response.data
                    )
                }
                is Result.Error -> {
                    state = state.copy(
                        errorState = true,
                        errorMessage = "Get Activities failed: ${response.exception.localizedMessage}"
                    )
                }
            }

            state = state.copy(loadingState = false)
        }
    }
}

class GetAllActivitiesViewModel(
    private val healthServiceRepository: HealthServiceRepository
): ViewModel() {
    var state by mutableStateOf(HealthResultState())
    fun getAllActivities(
        userId: Int,
        date: String
    ) {
        viewModelScope.launch {
            state = state.copy(loadingState = true)

            val result = healthServiceRepository.getAllActivities(userId, date)
            if(result != null){
                state = state.copy(successState = true)
                state.cachedActivityList = result
            } else {
                state = state.copy(errorState = true, errorMessage = "Get Activities Failed")
            }
            state = state.copy(loadingState = false)

        }
    }
}
```

# View Model Factory
```kotlin
@Suppress("UNCHECKED_CAST")
class HealthServiceViewModelFactory(
    private val healthServiceRepository: HealthServiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GetActivitiesViewModel::class.java) -> GetActivitiesViewModel(
                healthServiceRepository
            ) as T
            modelClass.isAssignableFrom(GetAllActivitiesViewModel::class.java) -> GetAllActivitiesViewModel(
                healthServiceRepository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
```

# Application
```kotlin
@Composable
fun SampleScreen(
    healthServiceViewModelFactory: HealthServiceViewModelFactory,
    tokenDataStore: TokenDataStore
){

    val dateKeyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateKey = LocalDateTime.now().format(dateKeyFormatter)

    val getActivitiesViewModel: GetActivitiesViewModel = viewModel(factory = healthServiceViewModelFactory)
    val getAllActivitiesViewModel: GetAllActivitiesViewModel = viewModel(factory = healthServiceViewModelFactory)
    
    val remoteAState = getActivitiesViewModel.state
    val localAState = getAllActivitiesViewModel.state
    
    LaunchedEffect(Unit){
        getActivitiesViewModel.getActivities(
            tokenDataStore.getId.first()?.toInt() ?: 0,
            dateKey.toString()
        )
        Log.d("Launch", "${remoteAState.activityList}")
    }
    LaunchedEffect(localState){
        getAllActivitiesViewModel.getAllActivities(
            tokenDataStore.getId.first()?.toInt() ?: 0,
            dateKey.toString()
        )
        Log.d("Scope", "${localAState.cachedActivityList}")
    }

}
```