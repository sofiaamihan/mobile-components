# Basic
So basically this is a static search bar where the items (buttons) are in a list present in the frontend.
``` Kotlin
@Composable
fun Screen(){
    var search = remember { mutableStateOf("") }  
	val buttons = listOf(  
	    Triple("Physical", R.drawable.physical, toPhysical),  
	    Triple("Mobility", R.drawable.mobility, toMobility),  
	    Triple("Medicine", R.drawable.medicine, toMedication)  
	)  
	  
	// Filter buttons based on the search input  
	val filteredButtons = buttons.filter {  
	    it.first.contains(search.value, ignoreCase = true)  // Ignores upper/lower casing and uses CONTAINS instead of perfect match
	}
	
	Column (  
	    modifier = Modifier  
	        .padding(top = 320.dp)  
	        .fillMaxWidth(),  
	    horizontalAlignment = Alignment.CenterHorizontally,  
	    verticalArrangement = Arrangement.SpaceAround  
	){  
	    filteredButtons.forEachIndexed { index, (label, icon, action) ->   
			Row(modifier = Modifier.padding(bottom = 10.dp)) {  
				BigButton(label, icon, toSensorScreen = { action() })  
			}  
		}    
	}
}
```

# Advanced 
When you are searching for items that have to be fetched from the backend.
1. Watch the search bar input field and after every 3 key strokes (to reduce request load),
2. Send a request to the backend
3. Queries the database
4. Sends back the query result to the front end