The `Chip` component is a compact, interactive UI element. It represents complex entities like a contact or tag, often with an icon and label. It can be checkable, dismissible, or clickable.

The four types of chips and where you might use them are as follows:

- **Assist**: Guides the user during a task. Often appears as a temporary UI element in response to user input.
- **Filter**: Allows users to refine content from a set of options. They can be selected or deselected, and may include a checkmark icon when selected.
- **Input**: Represents user-provided information, such as selections in a menu. They can contain an icon and text, and provide an 'X' for removal.
- **Suggestion**: Provides recommendations to the user based on their recent activity or input. Typically appear beneath an input field to prompt user actions.
# Chips Model
``` Kotlin
data class ChipsModel(  
    val name: String,  
    val subList: List<String>? = null,  
    val textExpanded: String? = null,  
    val leadingIcon: ImageVector? = null,  
    val trailingIcon: ImageVector? = null,  
)
```

# Assist Chips
- A row of two chips
- Only one of each row can be selected to ensure that only one assisted action is carried out
```Kotlin
@Composable  
fun AssistChipExample() {  
    AssistChip(  
        onClick = { Log.d("Assist chip", "hello world") },  
        label = { Text("Assist chip") },  
        leadingIcon = {  
            Icon(  
                Icons.Filled.Settings,  
                contentDescription = "Localized description",  
                Modifier.size(AssistChipDefaults.IconSize)  
            )  
        }  
    )  
}
```

``` Kotlin
@Composable  
fun TutorialAssistChip() {  
    val assistList = listOf(  
        ChipsModel(  
            name = "Call to my friend",  
            leadingIcon = Icons.Default.Face  
        ),  
        ChipsModel(  
            name = "Share the item",  
            leadingIcon = Icons.Default.Share  
        )  
    )  
  
    var selectedItem by remember { mutableStateOf("") }  
    var isSelected by remember { mutableStateOf(false) }  
  
    LazyRow {  
        items(assistList.size) { index ->  
            val item = assistList[index]  
            isSelected = selectedItem == item.name  
            Spacer(modifier = Modifier.padding(5.dp))  
            AssistChip(  
                onClick = {  
                    selectedItem = if (selectedItem != item.name) item.name else ""  
                },  
                label = {  
                    Text(text = item.name)  
                },  
                leadingIcon = {  
                    if (item.leadingIcon != null)  
                        Icon(item.leadingIcon, contentDescription = "")  
                },  
                colors = AssistChipDefaults.assistChipColors(  
                    containerColor = if (isSelected)  
                        MaterialTheme.colorScheme.primaryContainer else Color.Transparent,  
                    labelColor = Color.Black,  
                    leadingIconContentColor = if (isSelected)  
                        MaterialTheme.colorScheme.onPrimaryContainer else Color.LightGray,  
                )  
            )  
        }  
    }}
```

# Filter Chips
- You can select multiple chips in a row, acts as a filter so you can have multiple selected
- Acts as a toggle button
- Different Appearances as the Chip is toggled and un-toggled
```Kotlin
@Composable  
fun FilterChipExample() {  
    var selected by remember { mutableStateOf(false) }  
  
    FilterChip(  
        onClick = { selected = !selected },  
        label = {  
            Text("Filter chip")  
        },  
        selected = selected,  
        leadingIcon = if (selected) {  
            {  
                Icon(  
                    imageVector = Icons.Filled.Done,  
                    contentDescription = "Done icon",  
                    modifier = Modifier.size(FilterChipDefaults.IconSize)  
                )  
            }  
        } else {  
            null  
        },  
    )  
}
```

``` Kotlin
@Composable  
fun TutorialFilterChip() {  
    val filterList = listOf(  
        ChipsModel(  
            name = "Cart",  
            leadingIcon = Icons.Default.ShoppingCart,  
        ),  
        ChipsModel(  
            name = "Phone",  
            subList = listOf("Asus", "Pixel", "Apple"),  
            trailingIcon = Icons.Default.ArrowDropDown,  
            leadingIcon = Icons.Default.Check  
        ),  
        ChipsModel(  
            name = "Tablet",  
            leadingIcon = Icons.Default.Check  
        ),  
        ChipsModel(  
            name = "Dog",  
            trailingIcon = Icons.Default.Close  
        )  
    )  
  
    val selectedItems = remember { mutableStateListOf<String>() }  
    var isSelected by remember { mutableStateOf(false) }  
  
    LazyRow {  
        items(filterList.size) { index ->  
            val item = filterList[index]  
            isSelected = selectedItems.contains(item.name)  
            Spacer(modifier = Modifier.padding(5.dp))  
            if (item.subList != null) {  
                ChipWithSubItems(chipLabel = item.name, chipItems = item.subList)  
            } else {  
                FilterChip(  
                    selected = isSelected,  
                    onClick = {  
                        when (selectedItems.contains(item.name)) {  
                            true -> selectedItems.remove(item.name)  
                            false -> selectedItems.add(item.name)  
                        }  
                    },  
                    label = { Text(text = item.name) },  
                    leadingIcon = {  
                        val isCheckIcon = item.leadingIcon == Icons.Default.Check  
                        if (item.leadingIcon != null && isCheckIcon && isSelected) {  
                            Icon(item.leadingIcon, contentDescription = item.name)  
                        }  
                        if (item.leadingIcon != null && !isCheckIcon) {  
                            Icon(item.leadingIcon, contentDescription = item.name)  
                        }  
                    },  
                    trailingIcon = {  
                        if (item.trailingIcon != null && isSelected)  
                            Icon(item.trailingIcon, contentDescription = item.name)  
                    }  
                )  
            }  
        }  
    }}
```
## Chip with Dropdown
``` Kotlin
@OptIn(ExperimentalMaterial3Api::class)  
@Composable  
fun ChipWithSubItems(chipLabel: String, chipItems: List<String>) {  
    var isSelected by remember { mutableStateOf(false) }  
    var showSubList by remember { mutableStateOf(false) }  
    var filterName by remember { mutableStateOf("") }  
  
    ExposedDropdownMenuBox(  
        expanded = showSubList,  
        onExpandedChange = { showSubList = !showSubList }  
    ) {  
        FilterChip(  
            modifier = Modifier.menuAnchor(),  
            selected = isSelected,  
            onClick = {  
                isSelected = true  
            },  
            label = { Text(text = filterName.ifEmpty { chipLabel }) },  
            trailingIcon = {  
                Icon(  
                    modifier = Modifier.rotate(if (showSubList) 180f else 0f),  
                    imageVector = Icons.Default.ArrowDropDown,  
                    contentDescription = "List"  
                )  
            }  
        )  
        ExposedDropdownMenu(  
            expanded = showSubList,  
            onDismissRequest = { showSubList = false },  
        ) {  
            chipItems.forEach { subListItem ->  
                TextButton(  
                    modifier = Modifier.fillMaxWidth(),  
                    onClick = {  
                        filterName = subListItem  
                        showSubList = false  
                    },  
                    colors = ButtonDefaults.textButtonColors(  
                        containerColor = if (subListItem == filterName || subListItem == chipLabel) {  
                            MaterialTheme.colorScheme.primaryContainer  
                        } else { Color.Transparent }  
                    )  
                ) {  
                    Text(text = subListItem, color = Color.Black)  
                }  
            }        }    }}
```

# Input Chip
- Becomes text embedded into the screen once selected
- You're supposed to type or click on suggestions to fill in certain inputs
``` Kotlin 
@Composable  
fun InputChipExample(  
    text: String,  
    onDismiss: () -> Unit,  
) {  
    var enabled by remember { mutableStateOf(true) }  
    if (!enabled) return  
  
    InputChip(  
        onClick = {  
            onDismiss()  
            enabled = !enabled  
        },  
        label = { Text(text) },  
        selected = enabled,  
        avatar = {  
            Icon(  
                Icons.Filled.Person,  
                contentDescription = "Localized description",  
                Modifier.size(InputChipDefaults.AvatarSize)  
            )  
        },  
        trailingIcon = {  
            Icon(  
                Icons.Default.Close,  
                contentDescription = "Localized description",  
                Modifier.size(InputChipDefaults.AvatarSize)  
            )  
        },  
    )  
}
```

``` Kotlin
@Composable  
fun TutorialInputChip() {  
    val inputList = listOf(  
        ChipsModel(  
            name = "Crowley Junior",  
            textExpanded = "crowleyjunir@gmail.com",  
            leadingIcon = Icons.Default.Person,  
            trailingIcon = Icons.Default.Close  
        ),  
        ChipsModel(  
            name = "John Dee",  
            textExpanded = "johndee@gmail.com",  
            leadingIcon = Icons.Default.Add,  
            trailingIcon = Icons.Default.Close  
        )  
    )  
  
    val selectedItems = remember { mutableStateListOf<String>() }  
    var isTextExpanded by remember { mutableStateOf(false) }  
  
    LazyRow {  
        items(inputList.size) { index ->  
            val item = inputList[index]  
            isTextExpanded = selectedItems.contains(item.name)  
            Spacer(modifier = Modifier.padding(5.dp))  
            if (isTextExpanded && item.textExpanded != null) {  
                Text(text = item.textExpanded)  
            } else {  
                InputChip(  
                    selected = true,  
                    onClick = { selectedItems.add(item.name) },  
                    label = { Text(text = item.name) },  
                    avatar = {  
                        if (item.leadingIcon != null)  
                            Icon(item.leadingIcon, contentDescription = item.name)  
                    },  
                    trailingIcon = {  
                        if (item.trailingIcon != null)  
                            Icon(item.trailingIcon, contentDescription = item.name)  
                    }  
                )  
            }  
        }  
    }}
```

# Suggestion Chip
- You can make snackbars or toasts appear with suggestive inputs when clicked or log items into logcat
``` Kotlin
@Composable  
fun SuggestionChipExample() {  
    SuggestionChip(  
        onClick = { Log.d("Suggestion chip", "hello world") },  
        label = { Text("Suggestion chip") }  
    )  
}
```

``` Kotlin
@Composable  
fun TutorialSuggestionChip() {  
    val suggestionList = listOf(  
        ChipsModel(  
            name = "Country Name",  
            leadingIcon = Icons.Outlined.Person  
        ),  
        ChipsModel(  
            name = "Art Name",  
            leadingIcon = Icons.Outlined.PlayArrow  
        )  
    )  
  
    LazyRow {  
        items(suggestionList.size) { index ->  
            val item = suggestionList[index]  
            Spacer(modifier = Modifier.padding(5.dp))  
            SuggestionChip(  
                enabled = suggestionList[0].name == item.name,  
                onClick = { /*TODO*/ },  
                label = { Text(text = item.name) },  
                icon = {  
                    if (item.leadingIcon != null)  
                        Icon(item.leadingIcon, contentDescription = item.name)  
                }  
            )  
        }  
    }}
```