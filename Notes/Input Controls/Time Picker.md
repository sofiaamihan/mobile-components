- I feel like the bottom code can be shortened
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
@Preview  
fun TimePickerScreen() {  
    var showMenu by remember { mutableStateOf(true) }  
    var showAdvancedExample by remember { mutableStateOf(false) }  
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }  
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }  
  
    Box(  
        Modifier.fillMaxSize(),  
        contentAlignment = Alignment.Center  
    ) {  
        if (showMenu) {  
            Column(  
                modifier = Modifier  
                    .fillMaxSize()  
                    .padding(32.dp),  
                horizontalAlignment = Alignment.CenterHorizontally,  
                verticalArrangement = Arrangement.Center,  
            ) {  
                Button(onClick = {  
                    showAdvancedExample = true  
                    showMenu = false  
                }) {  
                    Text("Time picker dialog")  
                }  
                if (selectedTime != null) {  
                    val cal = Calendar.getInstance()  
                    cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)  
                    cal.set(Calendar.MINUTE, selectedTime!!.minute)  
                    cal.isLenient = false  
                    Text("Selected time = ${formatter.format(cal.time)}")  
                } else {  
                    Text("No time selected.")  
                }  
            }  
        }  
  
        when {  
            showAdvancedExample -> AdvancedTimePickerExample(  
                onDismiss = {  
                    showAdvancedExample = false  
                    showMenu = true  
                },  
                onConfirm = {  
                        time ->  
                    selectedTime = time  
                    showAdvancedExample = false  
                    showMenu = true  
                },  
            )  
        }  
    }  
}  
  
  
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun AdvancedTimePickerExample(  
    onConfirm: (TimePickerState) -> Unit,  
    onDismiss: () -> Unit,  
) {  
  
    val currentTime = Calendar.getInstance()  
    val timePickerState = rememberTimePickerState(  
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),  
        initialMinute = currentTime.get(Calendar.MINUTE),  
        is24Hour = true,  
    )  
  
    /** Determines whether the time picker is dial or input */  
    var showDial by remember { mutableStateOf(true) }  
    /** The icon used for the icon button that switches from dial to input */  
    val toggleIcon = if (showDial) {  
        Icons.Filled.EditCalendar  
    } else {  
        Icons.Filled.AccessTime  
    }  
  
    AdvancedTimePickerDialog(  
        onDismiss = { onDismiss() },  
        onConfirm = { onConfirm(timePickerState) },  
        toggle = {  
            IconButton(onClick = { showDial = !showDial }) {  
                Icon(  
                    imageVector = toggleIcon,  
                    contentDescription = "Time picker type toggle",  
                )  
            }  
        },  
    ) {  
        if (showDial) {  
            TimePicker(  
                state = timePickerState,  
            )  
        } else {  
            TimeInput(  
                state = timePickerState,  
            )  
        }  
    }  
}  
  
@Composable  
fun AdvancedTimePickerDialog(  
    title: String = "Select Time",  
    onDismiss: () -> Unit,  
    onConfirm: () -> Unit,  
    toggle: @Composable () -> Unit = {},  
    content: @Composable () -> Unit,  
) {  
    Dialog(  
        onDismissRequest = onDismiss,  
        properties = DialogProperties(usePlatformDefaultWidth = false),  
    ) {  
        Surface(  
            shape = MaterialTheme.shapes.extraLarge,  
            tonalElevation = 6.dp,  
            modifier =  
            Modifier  
                .width(IntrinsicSize.Min)  
                .height(IntrinsicSize.Min)  
                .background(  
                    shape = MaterialTheme.shapes.extraLarge,  
                    color = MaterialTheme.colorScheme.surface  
                ),  
        ) {  
            Column(  
                modifier = Modifier.padding(24.dp),  
                horizontalAlignment = Alignment.CenterHorizontally  
            ) {  
                Text(  
                    modifier = Modifier  
                        .fillMaxWidth()  
                        .padding(bottom = 20.dp),  
                    text = title,  
                    style = MaterialTheme.typography.labelMedium  
                )  
                content()  
                Row(  
                    modifier = Modifier  
                        .height(40.dp)  
                        .fillMaxWidth()  
                ) {  
                    toggle()  
                    Spacer(modifier = Modifier.weight(1f))  
                    TextButton(onClick = onDismiss) { Text("Cancel") }  
                    TextButton(onClick = onConfirm) { Text("OK") }  
                }            
            }        
        }    
    }
}
```