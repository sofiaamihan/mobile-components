`LinearProgressIndicator`
`CircularProgressIndicator`

# Time Control
``` Kotlin
suspend fun loadProgress(updateProgress: (Float) -> Unit) {  
    for (i in 1..50) {  
        updateProgress(i.toFloat() / 50)  
        delay(50)  
    }  
}
```

# Determinate
``` Kotlin
var currentProgress = remember { mutableFloatStateOf(0f) }  
var loading = remember { mutableStateOf(false) }  
val scope = rememberCoroutineScope()  
Box{  
    if (loading.value) {  
        CircularProgressIndicator(  
            progress = { currentProgress.floatValue },  // This is essentially the only difference
            modifier = Modifier.fillMaxWidth(0.1f),  
        )  
    } else {  
        Button(onClick = {  
            loading.value = true  
            scope.launch {  
                loadProgress { progress ->  
                    currentProgress.floatValue = progress  
                }  
                loading.value = false  
            }  
        }, enabled = !loading.value) {  
            Text("Show Determinate Circular Progress Indicator")  
        }
    }
}
```

# Indeterminate
`CircularProgressIndicator()` - Should Suffice
``` Kotlin
var currentProgress3 = remember { mutableFloatStateOf(0f) }  
var loading3 = remember { mutableStateOf(false) }  
val scope3 = rememberCoroutineScope()  
Box{  
    if (loading3.value) {  
        CircularProgressIndicator(  
            modifier = Modifier.fillMaxWidth(0.1f),  
        )  
    } else {  
        Button(onClick = {  
            loading3.value = true  
            scope3.launch {  
                loadProgress { progress ->  
                    currentProgress3.floatValue = progress  
                }  
                loading3.value = false  
            }  
        }, enabled = !loading3.value) {  
            Text("Show Determinate Circular Progress Indicator")  
        }  
    }  
}
```

# Application - Field Inputs and Snack Bars
``` Kotlin
var currentProgress = remember { mutableFloatStateOf(0f) }  
var loading = remember { mutableStateOf(false) }  
val scope = rememberCoroutineScope()  
val snackbarHostState = remember { SnackbarHostState() }  
fun isAllFieldsFilled(): Boolean {  
    return fullName.value.isNotEmpty() &&  
            gender.value.isNotEmpty() &&  
            email.value.isNotEmpty() &&  
            birthDate.value.isNotEmpty() &&  
            password.value.isNotEmpty() &&  
            confirmPassword.value.isNotEmpty()  
}


Box(){
	if (loading.value) {  
	    CircularProgressIndicator(  
	        progress = { currentProgress.floatValue },  
	        modifier = Modifier.fillMaxWidth(0.1f),  
	    )  
	} else {  
	    Button(onClick = {  
	        if (isAllFieldsFilled()) {  
	            loading.value = true  
	            scope.launch {  
	                loadProgress { progress ->  
	                    currentProgress.floatValue = progress  
	                }  
	                loading.value = false  
	                onClick()  
	            }  
	        } else {  
	                scope.launch {  
	                    snackbarHostState.showSnackbar("Please Fill in all fields")  
	            }  
	  
	        }  
	    }, enabled = !loading.value) {  
	        Text("Sign Up")  
	    }  
	}
	SnackbarHost(hostState = snackbarHostState) { snackbarData ->  
	    Snackbar(snackbarData)  
	}
}
```