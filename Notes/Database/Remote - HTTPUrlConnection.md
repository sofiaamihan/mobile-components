(WITH TOKEN) 
(WITHOUT TOKEN)

**Ensure that internet access has been granted**

- Headers (Authorisation is a custom header, without the bearer)
- Parameters
- Body

# Use Cases
1. With Token
2. Headers
3. Parameters 
4. Body

# Repository Syntax

## Request
``` Kotlin
sealed class Result<out R> {  
    data class Success<out T>(val data: T) : Result<T>()  
    data class Error(val exception: Exception) : Result<Nothing>()  
}
```

``` Kotlin
class DiscoverServiceRepository(private val tokenDataStore: TokenDataStore) {  
  
    private val baseUrl = "https://f5fqqafe6e.execute-api.us-east-1.amazonaws.com"  

    suspend fun sampleGETFunction(): Result<List<ResponseDataClass>> {}
    
    suspend fun sampleGETFunction2(): Result<ResponseDataClass> {}

    suspend fun samplePOSTFunction(): Any? () // This is when you don't need to return a response
}
```

``` Kotlin
suspend fun getAllContent(): Result<List<ContentResponse>> {  
    return withContext(Dispatchers.IO) {  
        try {  
            val url = URL("$baseUrl/content")  
            val connection = url.openConnection() as HttpURLConnection  
            
            // Specify the API Type
            connection.requestMethod = "GET" 
  
            // Include Token Header if applicable
  
            val responseCode = connection.responseCode  
            if (responseCode == HttpURLConnection.HTTP_OK) {  
                val response = connection.inputStream.bufferedReader().use { it.readText() }  
            
                val jsonArray = JSONArray(response)  // -- IF RETURNS ARRAY
                val jsonResponse = JSONObject(response) // -- IF RETURNS ONE
  
                return@withContext Result.Success(contentList)  // Returns a variable that is datatype of the Response Item
                
            } else {  
                Log.e("Repository", "Failed to get: $responseCode")  
                return@withContext Result.Error(Exception("Failed: $responseCode"))  
            }  
        } catch (e: Exception) {  
            Log.e("DiscoverServiceRepository", "Error: ${e.localizedMessage}", e)  
            return@withContext Result.Error(e)  
        }  
    }  
}
```

## Response
``` Kotlin
data class UpdateResponse(  
    val email: String,  
    val fullName: String  
)
```
- Any time there is a body response that you need, create a data class

## Token
``` Kotlin
val token = tokenDataStore.getToken.first()  
if (token != null) {  
    connection.setRequestProperty("Authorization", token)  // Authorization Headers
    Log.d("Token Here", token)  
} else {  
    Log.e("Repository", "Token is null")  
    return@withContext Result.Error(Exception("Token is null"))  
}
```

## Headers
``` Kotlin
connection.setRequestProperty("Authorization", "Bearer YOUR_TOKEN_HERE")
connection.setRequestProperty("Custom-Header", "HeaderValue")
```

## Parameters
``` Kotlin
val url = URL("$baseUrl/info/$nric") // Take in nric as a function parameter
```

## Body
``` Kotlin
connection.setRequestProperty("Content-Type", "application/json")

// Enable Output for Body (if sending a body payload)
connection.doOutput = true

val jsonRequest = JSONObject().apply {  
    put("nric", nric)  
    put("role", role)  
    put("email", email)  
    put("fullname", fullname)  
    put("password", password)  
}  
  
OutputStreamWriter(connection.outputStream).use { writer ->  
    writer.write(jsonRequest.toString())  
    writer.flush()  
}
```
- "nric" - this must match the attribute name of the remote table
- nric - this is the parameter inside of the function

## Result
``` Kotlin
// ----- NO BODY -----
val responseCode = connection.responseCode  
if (responseCode == HttpURLConnection.HTTP_CREATED) {  
    return@withContext "User Added"  
} else {  
    Log.e("userInfoRepository", "Sign up failed: $responseCode")  
    return@withContext null  
}

// ----- UPDATE BODY -----
val responseCode = connection.responseCode  
if (responseCode == HttpURLConnection.HTTP_OK) {  
    val response = connection.inputStream.bufferedReader().use { it.readText() }  
    val jsonResponse = JSONObject(response)  
    val updatedValues = jsonResponse.getJSONObject("updatedAttributes")  
    val updateResponse = UpdateResponse(  
        email = updatedValues.getString("email"),  
        fullName = updatedValues.getString("fullname")  // In order to return response, convert into Custom Response Data Class Type
    )  
    tokenDataStore.editUserProfile(  
        updateResponse.email,  
        updateResponse.fullName  
    )  
    Log.d("New Email", "${tokenDataStore.getEmail.first()}")  
    Log.d("New Name", "${tokenDataStore.getFullName.first()}")  
    return@withContext "User Updated"  
} else {  
    Log.e("userInfoRepository", "Update failed: $responseCode")  
    return@withContext null  
}

// ----- ARRAY BODY -----
val responseCode = connection.responseCode  
if (responseCode == HttpURLConnection.HTTP_OK) {  
    val response = connection.inputStream.bufferedReader().use { it.readText() }  
    val jsonArray = JSONArray(response)  
    val contentList = mutableListOf<ContentResponse>()  
    for (i in 0 until jsonArray.length()) {  
        val jsonResponse = jsonArray.getJSONObject(i)  
        val contentResponse = ContentResponse(  
            id = jsonResponse.getInt("id"),  
            contentCategoryId = jsonResponse.getInt("content_category_id"),  
            title = jsonResponse.getString("title"),  
            summary = jsonResponse.getString("summary"),  
            description = jsonResponse.getString("description")  
        )  
        contentList.add(contentResponse)  
        Log.d("Got Content !!", "$contentResponse")  
    }  
    return@withContext Result.Success(contentList)  
} else {  
    Log.e("DiscoverServiceRepository", "Failed to get: $responseCode")  
    return@withContext Result.Error(Exception("Failed to get content: $responseCode"))  
}

// ----- EXTRA -----
val responseCode = connection.responseCode  
if (responseCode == HttpURLConnection.HTTP_OK) {  
    val response = StringBuilder()  
    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->  
        var line: String?  
        while (reader.readLine().also { line = it } != null) {  
            response.append(line)  
        }  
    }  
    val jsonResponse = JSONObject(response.toString())  
    val success = jsonResponse.getBoolean("success")  
    val changedRows = jsonResponse.getInt("changedRows")  
    Log.d("Success?", "Activity Edited: $success, Changed Rows: $changedRows")  
    return@withContext if (success) "Activity Edited" else "Edit Failed"  
} else {  
    Log.e("HealthServiceRepository", "Failed to edit activity: $responseCode")  
    return@withContext null  
}

// ----- BETTER WAY TO READ RESPONSE ?? -----
val responseCode = connection.responseCode  
if (responseCode == HttpURLConnection.HTTP_OK) {  
    val response = StringBuilder()  
    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->  
        var line: String?  
        while (reader.readLine().also { line = it } != null) {  
            response.append(line)  
        }  
    }  
    val jsonResponse = JSONObject(response.toString())  
    val success = jsonResponse.getBoolean("success")  
    val changedRows = jsonResponse.getInt("changedRows")  
    Log.d("Success?", "Activity Edited: $success, Changed Rows: $changedRows")  
    return@withContext if (success) "Activity Edited" else "Edit Failed"  
} else {  
    Log.e("HealthServiceRepository", "Failed to edit activity: $responseCode")  
    return@withContext null  
}
```
## Troubleshooting
```Kotlin
// Get the response code
val responseCode = connection.responseCode  

// Get the response messege
val responseMessage = connection.inputStream.bufferedReader().use { it.readText() }
```
# View Model Syntax
## State
``` Kotlin
data class DiscoverResultState(  
    var loadingState: Boolean = false,  
    var errorState: Boolean = false,  
    var successState: Boolean = false,  
    var errorMessage: String? = null,  
    var contentList: List<ContentResponse> = emptyList(),  
    var categoriesList: List<ContentCategoriesResponse> = emptyList()  
)
```
- Use their `emptyList()` or prefill using `listOf()` with empty data
- Needed to access data in the frontend

## Without Response Body
``` Kotlin
class EditUserMeasurementsViewModel(  
    private val healthServiceRepository: HealthServiceRepository  
): ViewModel() {  
    var state by mutableStateOf(HealthResultState())  
    fun editUserMeasurements(  
        id: Int,  
        nric: String,  
        role: String,  
        age: Int,  
        gender: String,  
        weight: Double,  
        height: Double  
    ){  
        viewModelScope.launch {  
            state = state.copy(loadingState = true)  
            val result = healthServiceRepository.editUserMeasurements(id, nric, role, age, gender, weight, height)  
            if(result != null){  
                state = state.copy(successState = true)  
            } else {  
                state = state.copy(errorState = true, errorMessage = "Edit Measurements Failed")  
            }  
            state = state.copy(loadingState = false)  
  
        }  
    }  
}
```

## With Response Body (GET)
``` Kotlin
class GetAllContentCategoriesViewModel(  
    private val discoverServiceRepository: DiscoverServiceRepository,  
) : ViewModel() {  
    var state by mutableStateOf(DiscoverResultState())  
  
    fun getAllContentCategories() {  
        viewModelScope.launch {  
            state = state.copy(loadingState = true)  
  
            when (val response = discoverServiceRepository.getAllContentCategories()) {  
                is Result.Success -> {  
                    state = state.copy(  
                        successState = true,  
                        categoriesList = response.data  
                    )  
                }  
                is Result.Error -> {  
                    state = state.copy(  
                        errorState = true,  
                        errorMessage = "Get Categories failed: ${response.exception.localizedMessage}"  
                    )  
                }  
            }  
  
            state = state.copy(loadingState = false)  
        }  
    }  
}

// Easier alternative ? Not sure
class GetUserMeasurementsViewModel(  
    private val healthServiceRepository: HealthServiceRepository  
): ViewModel() {  
    var state by mutableStateOf(HealthResultState())  
    fun getUserMeasurements(  
        id: Int  
    ) {  
        viewModelScope.launch {  
            state = state.copy(loadingState = true)  
  
            val result = healthServiceRepository.getUserMeasurements(id)  
            if(result != null){  
                state = state.copy(successState = true)  
                state.userMeasurements = result  
            } else {  
                state = state.copy(errorState = true, errorMessage = "Get Measurements Failed")  
            }  
            state = state.copy(loadingState = false)  
  
        }  
    }  
}
```

# View Model Factory
``` Kotlin
@Suppress("UNCHECKED_CAST")  
class UserInfoViewModelFactory(  
    private val userInfoRepository: UserInfoRepository,  
    private val healthServiceRepository: HealthServiceRepository,  
) : ViewModelProvider.Factory {  
    override fun <T: ViewModel> create(modelClass: Class<T>): T {  
        return when {  
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(  
                userInfoRepository, healthServiceRepository  
            ) as T  
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(  
                userInfoRepository  
            ) as T  
            modelClass.isAssignableFrom(UpdateProfileViewModel::class.java) -> UpdateProfileViewModel(  
                userInfoRepository  
            ) as T  
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(  
                userInfoRepository  
            ) as T  
            modelClass.isAssignableFrom(DeleteAccountViewModel::class.java) -> DeleteAccountViewModel(  
                userInfoRepository  
            ) as T  
            else -> throw IllegalArgumentException("Unknown ViewModel class")  
        }  
    }  
}
```