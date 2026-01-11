# Elements Appearing
``` Kotlin
val buttonVisibility = remember { mutableStateOf(List(filteredButtons.size) { false }) }

filteredButtons.indices.forEach { index ->  
    delay(200L * index)  // Adjust delay to control the appearance timing  
    buttonVisibility.value = buttonVisibility.value.toMutableList().apply {  
        this[index] = true  
    }  
}

filteredButtons.forEachIndexed { index, (label, icon, action) ->  
    AnimatedVisibility(  
        visible = buttonVisibility.value[index],  
        enter = fadeIn() + slideInVertically(initialOffsetY = { it })  
    ) {  
        Row(modifier = Modifier.padding(bottom = 10.dp)) {  
            BigButton(label, icon, toSensorScreen = { action() })  
        }  
    }
}
```

# Screen Transitions
``` Kotlin
composable(  
    route = AuthScreen.Login.route,  
    enterTransition = {  
        return@composable slideIntoContainer(  
            AnimatedContentTransitionScope.SlideDirection.End, tween(700)  
        )  
    }, exitTransition = {  
        return@composable slideOutOfContainer(  
            AnimatedContentTransitionScope.SlideDirection.Start, tween(700)  
        )  
    }, popEnterTransition = {  
        return@composable slideIntoContainer(  
            AnimatedContentTransitionScope.SlideDirection.End, tween(700)  
        )  
    }  
){
```

``` Kotlin
enterTransition = { fadeIn(tween(700)) },
exitTransition = { fadeOut(tween(700)) },
popEnterTransition = { fadeIn(tween(700)) },
popExitTransition = { fadeOut(tween(700)) }
```

``` Kotlin
enterTransition = { scaleIn(tween(700)) },
exitTransition = { scaleOut(tween(700)) },
popEnterTransition = { scaleIn(tween(700)) },
popExitTransition = { scaleOut(tween(700)) }

```