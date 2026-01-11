package com.example.boilerplatecode.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class AccordionModel(
    val header: String,
    val rows: List<RowModel>
){
    data class RowModel (
        val foodItem: String,
        val price: String
    )
}

@Composable
@Preview
fun AccordionRow(
    model: AccordionModel.RowModel = AccordionModel.RowModel("Chicken", "$10")
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ){
        Text(model.foodItem, Modifier.weight(1f))
        Surface {
            Text(
                text = model.price,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            )
        }
    }
}

@Composable
@Preview
fun AccordionHeader(
    title: String = "Arburi-En Causeway Point",
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {}
){
    val degrees = if (isExpanded) 180f else 0f

    Surface(
        color = White,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp
    ){
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, Modifier.weight(1f))
            Surface {
                Icon(
                    Icons.Outlined.ArrowDropDown,
                    contentDescription = "Expand",
                    modifier = Modifier.rotate(degrees)
                )
            }
        }
    }
}

@Composable
fun Accordion(
    modifier: Modifier = Modifier, model: AccordionModel
){
    var expanded = remember { mutableStateOf(false) }

    Column (
        modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        AccordionHeader(
            title = model.header,
            isExpanded = expanded.value
        ){
            expanded.value = !expanded.value
        }
        AnimatedVisibility(visible = expanded.value) {
            Surface(
                color = White,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ){
                Column {
                    model.rows.forEach { row ->
                        AccordionRow(row)
                    }
                }
            }
        }
    }
}

@Composable
fun AccordionGroup(
    modifier: Modifier = Modifier,
    group: List<AccordionModel>
){
    Column (modifier = modifier){
        group.forEach {
            Accordion(model = it)
        }
    }
}

@Composable
fun AccordionScreen(){
    val accordionData = listOf(
        AccordionModel(
            header = "Aburi-En Causeway Point",
            rows = listOf(
                AccordionModel.RowModel(foodItem = "Pie", price = "$150"),
                AccordionModel.RowModel(foodItem = "Meat", price = "$2800"),
                AccordionModel.RowModel(foodItem = "Microsoft", price = "$300")
            )
        ),
        AccordionModel(
            header = "Swee Choon Express (AMK)",
            rows = listOf(
                AccordionModel.RowModel(foodItem = "Pie", price = "$150"),
                AccordionModel.RowModel(foodItem = "Meat", price = "$2800"),
                AccordionModel.RowModel(foodItem = "Microsoft", price = "$300")
            )
        )
    )
    AccordionGroup(group = accordionData)
}