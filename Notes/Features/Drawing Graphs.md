1. Bar Graph
2. Line Graph (Straight)
3. Line Graph (Curve) - **MAYBE**
4. Pie Chart
5. Scatter Plot - **MAYBE**
Anything else is if I have time I suppose


# Bar Graph
``` Kotlin
@Composable  
fun StepCounterBarGraph(stepCounts: List<Int>, days: List<String>) {  
    val maxSteps = stepCounts.maxOrNull() ?: 1  
    val barWidth = 40.dp  
    Row(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(16.dp),  
        horizontalArrangement = Arrangement.SpaceBetween  
    ) {  
        days.forEachIndexed { index, day ->  
            Column(  
                horizontalAlignment = Alignment.CenterHorizontally,  
                modifier = Modifier.width(barWidth)  
            ) {  
                Canvas(modifier = Modifier  
                    .fillMaxHeight(0.3f)  
                    .width(barWidth)) {  
                    val barHeight = (stepCounts[index] / maxSteps.toFloat()) * size.height  
                    drawRect(  
                        color = Color(0xFF586249),  
                        topLeft = Offset(0f, size.height - barHeight),  
                        size = androidx.compose.ui.geometry.Size(size.width, barHeight)  
                    )  
                }  
                Text(text = stepCounts[index].toString(), fontSize = 12.sp)  
                Text(text = day, fontSize = 12.sp)  
            }  
        }    
    }
}
```

# Simple Line Graph
``` Kotlin
@Composable  
fun StepCounterLineGraph(stepCounts: List<Int>, days: List<String>) {  
    val maxSteps = stepCounts.maxOrNull() ?: 1  
    val lineColor = Color(0xFF586249)  
    val pointRadius = 5.dp  
  
    Canvas(  
        modifier = Modifier  
            .fillMaxWidth()  
            .height(300.dp)  
            .padding(16.dp)  
    ) {  
        val graphWidth = size.width  
        val spacing = graphWidth / (stepCounts.size - 1).coerceAtLeast(1) // Ensuring valid spacing  
        val graphHeight = size.height  
  
        // Create a path for the line graph  
        val path = Path().apply {  
            moveTo(0f, (1 - (stepCounts[0] / maxSteps.toFloat())) * graphHeight)  
            for (i in 1 until stepCounts.size) {  
                val x = i * spacing  
                val y = (1 - (stepCounts[i] / maxSteps.toFloat())) * graphHeight  
                lineTo(x, y)  
            }  
        }  
  
        // Draw the line  
        drawPath(path, lineColor, style = Stroke(width = 4f))  
  
        // Draw points on the line  
        stepCounts.forEachIndexed { index, count ->  
            val x = index * spacing  
            val y = (1 - (count / maxSteps.toFloat())) * graphHeight  
            drawCircle(color = lineColor, radius = pointRadius.toPx(), center = Offset(x, y))  
        }  
    }  
    // Draw the days below the graph  
    Row(  
        modifier = Modifier.fillMaxWidth(),  
        horizontalArrangement = Arrangement.SpaceEvenly // Ensure even spacing of labels  
    ) {  
        days.forEach { day ->  
            Text(text = day, fontSize = 12.sp, textAlign = TextAlign.Center)  
        }  
    }
}
```

# Labelled Line Graph
``` Kotlin
@Composable  
fun StepCounterLineGraph2(stepCounts: List<Int>, days: List<String>) {  
    val maxSteps = stepCounts.maxOrNull() ?: 1  
    val minSteps = stepCounts.minOrNull() ?: 0  
    val lineColor = Color(0xFF586249)  
    val axisColor = Color.Gray  
    val pointRadius = 5.dp  
    val yLabelWidth = 50.dp // Space for Y labels  
  
    Box(  
        modifier = Modifier  
            .fillMaxWidth()  
            .height(350.dp) // Increased height to fit labels  
            .padding(16.dp)  
    ) {  
        Row(  
            modifier = Modifier  
                .fillMaxWidth()  
                .height(300.dp) // Align with graph area  
                .padding(start = yLabelWidth), // Reserve space for Y labels  
            horizontalArrangement = Arrangement.SpaceEvenly  
        ) {  
            Canvas(  
                modifier = Modifier  
                    .fillMaxWidth()  
                    .height(300.dp) // Graph area  
            ) {  
                val graphWidth = size.width  
                val graphHeight = size.height  
                val spacing = graphWidth / (stepCounts.size - 1).coerceAtLeast(1)  
  
                // Scale step values to fit graph height  
                fun scaleY(value: Int): Float {  
                    return (1 - (value / maxSteps.toFloat())) * graphHeight  
                }  
  
                // Create a path for the line graph  
                val path = Path().apply {  
                    moveTo(0f, scaleY(stepCounts[0]))  
                    for (i in 1 until stepCounts.size) {  
                        lineTo(i * spacing, scaleY(stepCounts[i]))  
                    }  
                }  
  
                // Draw the graph line  
                drawPath(path, lineColor, style = Stroke(width = 4f))  
  
                // Draw points on the line  
                stepCounts.forEachIndexed { index, count ->  
                    val x = index * spacing  
                    val y = scaleY(count)  
                    drawCircle(color = lineColor, radius = pointRadius.toPx(), center = Offset(x, y))  
                }  
  
                // Draw X and Y Axis  
                drawLine(  
                    color = axisColor,  
                    start = Offset(0f, graphHeight),  
                    end = Offset(graphWidth, graphHeight), // X-axis  
                    strokeWidth = 2f  
                )  
                drawLine(  
                    color = axisColor,  
                    start = Offset(0f, 0f),  
                    end = Offset(0f, graphHeight), // Y-axis  
                    strokeWidth = 2f  
                )  
            }  
        }  
        // Draw Y-axis labels (Now in ascending order)  
        Column(  
            modifier = Modifier  
                .height(300.dp)  
                .width(yLabelWidth),  
            verticalArrangement = Arrangement.SpaceEvenly  
        ) {  
            val stepInterval = (maxSteps - minSteps) / 5.coerceAtLeast(1) // 5 steps for readability  
            val stepValues = (0..5).map { minSteps + (stepInterval * it) }.reversed() // Reverse order for proper alignment  
  
            stepValues.forEach { stepValue ->  
                Text(  
                    text = stepValue.toString(),  
                    fontSize = 12.sp,  
                    modifier = Modifier.fillMaxWidth(),  
                    textAlign = TextAlign.End  
                )  
            }  
        }  
        // Draw the days below the graph  
        Row(  
            modifier = Modifier  
                .fillMaxWidth()  
                .align(Alignment.BottomCenter)  
                .padding(start = yLabelWidth), // Ensure labels align with graph  
            horizontalArrangement = Arrangement.SpaceEvenly  
        ) {  
            days.forEach { day ->  
                Text(text = day, fontSize = 12.sp, textAlign = TextAlign.Center)  
            }  
        }    
    }
}
```

# Pie Charts
``` Kotlin
@Composable  
fun StepCounterPieChart(stepCounts: List<Int>, days: List<String>) {  
    val totalSteps = stepCounts.sum().toFloat()  
    val colors = listOf(  
        Color(0xFFE57373), Color(0xFF81C784), Color(0xFF64B5F6),  
        Color(0xFFFFD54F), Color(0xFFBA68C8), Color(0xFFFF8A65)  
    ) // Different colors for each slice  
  
    Canvas(  
        modifier = Modifier  
            .size(300.dp)  
            .padding(16.dp)  
    ) {  
        var startAngle = 0f  
  
        stepCounts.forEachIndexed { index, steps ->  
            val sweepAngle = (steps / totalSteps) * 360f  
            drawArc(  
                color = colors[index % colors.size],  
                startAngle = startAngle,  
                sweepAngle = sweepAngle,  
                useCenter = true  
            )  
            startAngle += sweepAngle  
        }  
    }  
    // Display legends below the pie chart  
    Column(  
        modifier = Modifier.padding(top = 16.dp),  
        verticalArrangement = Arrangement.spacedBy(4.dp)  
    ) {  
        stepCounts.forEachIndexed { index, steps ->  
            Row(  
                verticalAlignment = Alignment.CenterVertically  
            ) {  
                Box(  
                    modifier = Modifier  
                        .size(16.dp)  
                        .background(colors[index % colors.size])  
                )  
                Spacer(modifier = Modifier.width(8.dp))  
                Text(text = "${days[index]}: $steps steps", fontSize = 14.sp)  
            }  
        }    
    }
}
```