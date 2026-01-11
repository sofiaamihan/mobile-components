package com.example.boilerplatecode.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// -------------------- Basic Syntax --------------------
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

@Composable
fun SuggestionChipExample() {
    SuggestionChip(
        onClick = { Log.d("Suggestion chip", "hello world") },
        label = { Text("Suggestion chip") }
    )
}


// -------------------- Application --------------------
data class ChipsModel(
    val name: String,
    val subList: List<String>? = null,
    val textExpanded: String? = null,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
)

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
    }
}

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
    }
}

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
            }
        }
    }
}

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
    }
}

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
    }
}

@Composable
fun ChipsScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "General Chips", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        AssistChipExample()
        FilterChipExample()
        InputChipExample("Input Chip", onDismiss = {})
        SuggestionChipExample()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = "Assist Chip", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        TutorialAssistChip()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = "Filter Chip", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        TutorialFilterChip()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = "Input Chip", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        TutorialInputChip()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Text(text = "Suggestion Chip", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        TutorialSuggestionChip()
    }
}