package com.example.boilerplatecode.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.boilerplatecode.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun ButtonsScreen(){
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // -------------------- General Buttons --------------------
        Button( onClick = {} ) { Text("Normal Button") }
        TextButton( onClick = {} ) { Text("Text Button") }
        ElevatedButton( onClick = {} ) { Text("Elevated Button") }
        OutlinedButton( onClick = {} ) { Text("Outlined Button") }
        FilledTonalButton( onClick = {} ) { Text("Tonal Button ")}
        Button( onClick = {}, shape = RoundedCornerShape(20)) { Text("Rounded Corner Button") }
        Button( onClick = {}, shape = RectangleShape ) { Text("Rectangle Button") }
        Button( onClick = {}, shape = CircleShape, modifier = Modifier.size(60.dp) ) { Text("C") }
        Button( onClick = {}, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)) { Text("Coloured Button") }
        Button( onClick = {} ) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favourite")
            Text("Text and Icon Button")
        }

        // -------------------- Badged Button --------------------
        Box(modifier = Modifier.padding(8.dp)) {
            Button( onClick = {} ) { Text("Badged Button") }
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
            ) { Text("3") }
        }

        // -------------------- Gradient Button --------------------
        val horizontalGradientBrush = Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.tertiary
            )
        )
        Button( onClick = {}, colors = ButtonDefaults.buttonColors(Color.Transparent) ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(horizontalGradientBrush)
            ){
                Text("Gradient Button", modifier = Modifier.padding(10.dp))
            }
        }

        // -------------------- Floating Action Button --------------------
        FloatingActionButton(
            onClick = {},
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Text("Floating Action Button", modifier = Modifier.padding(5.dp))
        }

        // -------------------- Segmented Button --------------------
        var selectedIndex = remember { mutableIntStateOf(0) }
        val options = listOf("Choice 1", "Choice 2", "Choice 3")
        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = {
                        selectedIndex.intValue = index
                        if (index == 0) {
                        } else {
                        }
                    },
                    selected = index == selectedIndex.intValue,
                    label = { Text(label) }
                )
            }
        }

        // -------------------- Icon Button --------------------
        IconButton( onClick = {} ) { Icon(Icons.Default.Home, contentDescription = "Home") }

        // -------------------- Switch Button --------------------
        var isChecked by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    uncheckedThumbColor = Color.Red
                )
            )
        }

        // -------------------- Toggle Button --------------------
        var isToggled by remember { mutableStateOf(true) }
        Button(
            onClick = { isToggled = !isToggled },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = if (!isToggled) Icons.Default.PlayArrow else Icons.Default.Close,
                contentDescription = if (isToggled) "Toggled" else "Not Toggled"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isToggled) "Not Toggled" else "Toggled")
        }

        // -------------------- Radio Button --------------------
        var selectedOption by remember { mutableStateOf("Option 1") }
        Column {
            Text(text = "Choose an option:", fontSize = 16.sp)
            listOf("Option 1", "Option 2", "Option 3").forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedOption = option }
                ) {
                    RadioButton(
                        selected = (selectedOption == option),
                        onClick = { selectedOption = option }
                    )
                    Text(text = option)
                }
            }
        }

        // -------------------- Checkbox --------------------
        var checkedState by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it }
            )
            Text(text = if (checkedState) "Checked" else "Unchecked")
        }

        // -------------------- Image Button --------------------
        val image: Painter = painterResource(id = R.drawable.logo)
        Image(
            painter = image,
            contentDescription = "Image Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clickable(onClick = {})
        )

    }
}
