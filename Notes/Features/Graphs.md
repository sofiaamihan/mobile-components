# Bar Graph
``` Kotlin
@Composable  
fun BarGraph(data: List<Int>, labels: List<String>){  
    var max = data.maxOrNull() ?: 1  
    var barWidth = 40.dp  
  
    Row (  
        modifier = Modifier.fillMaxWidth(0.8f),  
        horizontalArrangement = Arrangement.SpaceEvenly  
    ){  
        labels.forEachIndexed { index, label ->  
            Column (  
                modifier = Modifier  
                    .fillMaxHeight(0.3f)  
                    .width(barWidth)  
            ){  
                Canvas(  
                    modifier = Modifier.width(barWidth)  
                ){  
                    val barHeight = (data[index] / max.toFloat()) * size.height  
                    drawRect(  
                        color = Color(0xFF7D5260),  
                        size = androidx.compose.ui.geometry.Size(size.width, barHeight),  
                        topLeft = Offset(0f, size.height - barHeight)  
                    )  
                }  
  
            }        
        }    
    }
}
```

# Line Graph
``` Kotlin
@Composable  
fun SimpleLineGraph(data: List<Int>, labels: List<String>){  
    var max = data.maxOrNull() ?: 1  
    var color =  Color(0xFF7D5260)  
    var pointerRadius = 5.dp  
  
    Canvas(  
        modifier = Modifier  
            .fillMaxWidth()  
            .height(300.dp)  
            .padding(16.dp)  
    ) {  
        val graphWidth = size.width  
        val graphHeight = size.height  
        val spacing = graphWidth / (data.size - 1).coerceAtLeast(1)  
  
        // Creating the path  
        val path = Path().apply {  
            moveTo(0f, (1-(data[0]/max.toFloat())) * graphHeight) // Starting Coordinate: First Value  
            for (i in 1 until data.size){  
                val x = i*spacing  
                val y = (1-(data[i]/max.toFloat())) * graphHeight  
                lineTo(x, y)  
            }  
        }  
  
        // Drawing the path  
        drawPath(path, color, style = Stroke(width = 4f))  
  
        // Drawing the points  
        data.forEachIndexed { index, point ->  
            val x = index*spacing  
            val y = (1- point/max.toFloat()) * graphHeight  
            drawCircle(color = color, radius = pointerRadius.toPx(), center = Offset(x, y))  
        }  
    }  
    // Adding the Labels  
    Row (  
        modifier = Modifier.fillMaxWidth(),  
        horizontalArrangement = Arrangement.SpaceEvenly  
    ){  
        labels.forEach{ label ->  
            Text(label)  
        }  
    }
}
```

# Pie Chart
``` Kotlin
@Composable  
fun PieChart(data: List<Int>, labels: List<String>){  
    val total = data.sum().toFloat()  
    val colors = listOf(  
        Color(0xFF77574E),  
        Color(0xFFFFDBD1),  
        Color(0xFF5D4037),  
        Color(0xFF6C5D2F),  
        Color(0xFFFFFFFF),  
        Color(0xFFF5E1A7)  
    )  
  
    Column (  
        modifier = Modifier.fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ){  
        Canvas(  
            modifier = Modifier.size(300.dp).padding(16.dp)  
        ) {  
            var startAngle = 0f  
  
            data.forEachIndexed { index, item ->  
                val sweepAngle = (data[index]/total) * 360f  
                drawArc(  
                    startAngle = startAngle,  
                    sweepAngle = sweepAngle,  
                    color = colors[index],  
                    useCenter = true  
                )  
                startAngle += sweepAngle  
            }  
        }        Column {  
            labels.forEachIndexed { index, item ->  
                Row {  
                    Box(modifier = Modifier.size(20.dp).background(colors[index]))  
                    Text(item)  
                }  
            }        
        }   
    }
}
```