# Common Date Formats
- 27 June 2025 - `dd MMMM yyyy`
- 06-27-2025 - `MM-dd-yyyy`
- 27/06/2025 - `MM/dd/yyyy`
- Friday, June 27 2025 - `EEEE, MMMM dd yyyy`
``` Kotlin
fun convertMillisToDate(millis: Long): String {  
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())  
    return formatter.format(Date(millis))  
}
```
# Common Time Formats
- 14:30 - `HH:mm`
- 02:30 PM - `hh:mm a`
- 14:30:45 - `HH:mm:ss`
- 14:30:45 UTC - `HH:mm:ss z`
- 14:30:45 GMT+08:00 - `HH:mm:ss Z`
- Friday, 27 June 2025, 14:30:45 - `EEEE, dd MMMM yyyy, HH:mm:ss
``` Kotlin
fun convertMillisToTime(millis: Long): String {  
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())  
    return formatter.format(Date(millis))  
}
```

# (skipped) Docked Date Picker
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun DatePickerDocked() {  
    var showDatePicker by remember { mutableStateOf(false) }  
    val datePickerState = rememberDatePickerState()  
    val selectedDate = datePickerState.selectedDateMillis?.let {  
        convertMillisToDate(it)  
    } ?: ""  
  
    LaunchedEffect(selectedDate) {  
        Log.d("Selected Date", selectedDate.toString())  
    }  
  
    Box(  
        modifier = Modifier.fillMaxWidth(0.8f)  
    ) {  
        OutlinedTextField(  
            value = selectedDate,  
            onValueChange = { },  
            label = { Text("Docked Date Picker") },  
            readOnly = true,  
            trailingIcon = {  
                IconButton(onClick = { showDatePicker = !showDatePicker }) {  
                    Icon(  
                        Icons.Default.CalendarMonth,  
                        contentDescription = "Select date"  
                    )  
                }  
            },  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(64.dp)  
        )  
  
        if (showDatePicker) {  
            Popup(  
                onDismissRequest = { showDatePicker = false },  
                alignment = Alignment.TopStart  
            ) {  
                Box(  
                    modifier = Modifier  
                        .fillMaxWidth()  
                        .offset(y = 64.dp)  
                        .shadow(elevation = 4.dp)  
                        .background(MaterialTheme.colorScheme.surface)  
                        .padding(16.dp)  
                ) {  
                    DatePicker(  
                        state = datePickerState,  
                        showModeToggle = false  
                    )  
                }  
            }        }  
    }  
}
```

# Date Picker Modal
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun DatePickerModal(  
    onDateSelected: (Long?) -> Unit,  
    onDismiss: () -> Unit  
) {  
    val datePickerState = rememberDatePickerState()  
    val today = remember { System.currentTimeMillis() }
  
    DatePickerDialog(  
        onDismissRequest = onDismiss,  
        confirmButton = {  
            TextButton(onClick = {  
                onDateSelected(datePickerState.selectedDateMillis)  
                onDismiss()  
            }) {  
                Text("OK")  
            }  
        },  
        dismissButton = {  
            TextButton(onClick = onDismiss) {  
                Text("Cancel")  
            }  
        }    
        ) 
        {  
        DatePicker(
	        state = datePickerState,
        )  
    }  
}

@Composable  
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {  
    var selectedDate by remember { mutableStateOf<Long?>(null) }  
    var showModal by remember { mutableStateOf(false) }  
  
    OutlinedTextField(  
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",  
        onValueChange = { },  
        label = { Text("DOB") },  
        placeholder = { Text("MM/DD/YYYY") },  
        trailingIcon = {  
            Icon(Icons.Default.DateRange, contentDescription = "Select date")  
        },  
        modifier = modifier  
            .fillMaxWidth()  
            .pointerInput(selectedDate) {  
                awaitEachGesture {  
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput  
                    // in the Initial pass to observe events before the text field consumes them in the Main pass.                    
                    awaitFirstDown(pass = PointerEventPass.Initial)  
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)  
                    if (upEvent != null) {  
                        showModal = true  
                    }  
                }  
            }    
    )  
  
    if (showModal) {  
        DatePickerModal(  
            onDateSelected = { selectedDate = it },  
            onDismiss = { showModal = false }  
        )  
    }  
}
```
## Validation
``` Kotlin
enabled = datePickerState.selectedDateMillis?.let { it <= today.absoluteValue } == true
```
# (skipped) Ranged Date Picker
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun DateRangePickerModal(  
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,  
    onDismiss: () -> Unit  
) {  
    val dateRangePickerState = rememberDateRangePickerState()  
  
    DatePickerDialog(  
        onDismissRequest = onDismiss,  
        confirmButton = {  
            TextButton(  
                onClick = {  
                    onDateRangeSelected(  
                        Pair(  
                            dateRangePickerState.selectedStartDateMillis,  
                            dateRangePickerState.selectedEndDateMillis  
                        )  
                    )  
                    onDismiss()  
                }  
            ) {  
                Text("OK")  
            }  
        },  
        dismissButton = {  
            TextButton(onClick = onDismiss) {  
                Text("Cancel")  
            }  
        }    ) {  
        DateRangePicker(  
            state = dateRangePickerState,  
            title = {  
                Text(  
                    text = "Select date range"  
                )  
            },  
            showModeToggle = false,  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(500.dp)  
                .padding(16.dp)  
        )  
    }  
}
```
## Application
``` Kotlin
@Composable  
fun RangedDatePickerScreen() {  
    var showRangeModal by remember { mutableStateOf(false) }  
    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }  
  
    Column(  
        modifier = Modifier  
            .fillMaxSize()  
            .padding(10.dp),  
        horizontalAlignment = Alignment.CenterHorizontally,  
        verticalArrangement = Arrangement.Center  
    ) {    
        Text("Ranged Date Picker:")  
        Button(onClick = { showRangeModal = true }) { Text("Show Modal Date Range Picker") }  
        if (selectedDateRange.first != null && selectedDateRange.second != null) {  
            val startDate = Date(selectedDateRange.first!!)  
            val endDate = Date(selectedDateRange.second!!)  
            val formattedStartDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(startDate)  
            val formattedEndDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(endDate)  
            Text("Selected date range: $formattedStartDate - $formattedEndDate")  
        } else {  
            Text("No date range selected")  
        }  
    }  
  
    if (showRangeModal) {  
        DateRangePickerModal(  
            onDateRangeSelected = {  
                selectedDateRange = it  
                showRangeModal = false  
            },  
            onDismiss = { showRangeModal = false }  
        )  
    }  
}
```