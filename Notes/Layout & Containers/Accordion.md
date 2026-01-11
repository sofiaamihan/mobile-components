`data class Row` - i would change Row into RowModel or something different from the Compose Row, especially if everything is in the same file - take not of this but otherwise the code should work

https://medium.com/@eozsahin1993/accordion-menu-in-jetpack-compose-32151adf6d80
1. Data Class - Accordion Model - Hold the information that will be displayed by the UI
2. Function - Accordion Row - List of rows that will display once the Accordion is expanded
3. Function - Accordion Header - Contains the content that shows whether it is expanded or collapsed
4. Component - Accordion - Holds the Header AND the Rows + Animation
5. View - Accordion Group - Shows multiple accordions together
6. Application

`AccordionModel`
```Kotlin
data class AccordionModel(  
    val header: String,  
    val rows: List<Row>  
){  
    data class Row (  
        val foodItem: String,  
        val price: String  
    )  
}
```

`AccordionRow`
```Kotlin
@Composable  
@Preview  
fun AccordionRow(  
    model: AccordionModel.Row = AccordionModel.Row("Chicken", "$10")  
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
```

`AccordionHeader`
```Kotlin
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
```

`Accordion`
```Kotlin
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
```

`AccordionGroup`
```Kotlin
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
```

`Application`
```Kotlin
val accordionData = listOf(  
    AccordionModel(  
        header = "Aburi-En Causeway Point",  
        rows = listOf(  
            Row(foodItem = "Pie", price = "$150"),  
            Row(foodItem = "Meat", price = "$2800"),  
            Row(foodItem = "Microsoft", price = "$300")  
        )  
    ),  
    AccordionModel(  
        header = "Swee Choon Express (AMK)",  
        rows = listOf(  
            Row(foodItem = "Pie", price = "$150"),  
            Row(foodItem = "Meat", price = "$2800"),  
            Row(foodItem = "Microsoft", price = "$300")  
        )  
    )  
)
AccordionGroup(group = accordionData)
```
