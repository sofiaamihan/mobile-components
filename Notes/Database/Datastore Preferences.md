```Kotlin
class TokenDataStore (private val context: Context) {  
  
    companion object {  
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("SessionToken")  
        val SESSION_TOKEN_KEY = stringPreferencesKey("session_token")  
        val USER_ID =  intPreferencesKey("user_id") 
    }  
  
    val getToken: Flow<String?> = context.dataStore.data  
        .map { preferences ->  
            preferences[SESSION_TOKEN_KEY] ?: ""  
        }   
  
    val getId: Flow<Int?> = context.dataStore.data  
        .map { preferences ->  
            preferences[USER_ID] ?: 0  
        }  
  
    suspend fun saveToken(name: String) {  fun
        context.dataStore.edit { preferences ->  
            preferences[SESSION_TOKEN_KEY] = name  
        }  
    }   
  
    suspend fun saveId(id: Int) {  
        context.dataStore.edit { preferences ->  
            preferences[USER_ID] = id  
        }  
    }  
  
    suspend fun editUserProfile(  
        userId: Int,  
    ){  
        context.dataStore.edit { preferences ->  
            preferences[USER_ID] = userId  
        }  
    }  
  
    suspend fun clearSession() {  
        context.dataStore.edit { preferences ->  
            preferences.remove(SESSION_TOKEN_KEY)  
            preferences.remove(USER_ID)  
        }  
    }
}
```