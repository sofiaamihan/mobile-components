# Initialising Project
- `compileSdk = 35`
# Material Theming
https://material-foundation.github.io/material-theme-builder/ 
# Obtain IP Address 
## Mac
```
ipconfig getifaddr en0
```
## Windows
```
ipconfig
```
# Connecting the SQL Database
- Change Password: Apple ID Password
- Change IP Address

# Troubleshooting
```SQL
%% When authentication method is invalid and the sql library has been updated %%
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'current_password';

%% When they do not let you update due to safe mode %%
SET SQL_SAFE_UPDATES = 0;
```

# Clean Architecture
```
|--data
	|--dao
	|--entities
	|--repository
	|--database
    |--preferences
        |--TokenDataStore.kt
    |--viewmodel
        |--ViewModel.kt
    |--factory
        |--ViewModelFactory.kt
|--ui
    |--theme
        |--Color.kt
        |--Theme.kt
        |--Type.kt
    |--components
    |--screens
|--MainActivity.kt
```

