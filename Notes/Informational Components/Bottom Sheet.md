``` Kotlin
val sheetState = rememberModalBottomSheetState()  
val scope6 = rememberCoroutineScope()  
var showBottomSheet by remember { mutableStateOf(false) }  
Button ( onClick = {  
        showBottomSheet = true  
}){ Text("Show Bottom Sheet") }  
if (showBottomSheet) {  
    ModalBottomSheet(  
        onDismissRequest = {  
            showBottomSheet = false  
        },  
        sheetState = sheetState  
    ) {  
        // Sheet content  
        Button(onClick = {  
            scope6.launch{sheetState.hide()}
        }) {  
            Text("Hide bottom sheet")  
        }  
    }
}
```
- The button to hide bottom sheet + scope isn't necessary