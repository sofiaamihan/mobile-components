# Lazy Column/Row
``` Kotlin
LazyColumn {
    // Add a single item
    item {
        Text(text = "First item")
    }

    // Add 5 items
    items(5) { index ->
        Text(text = "Item: $index")
    }
}
```

# Lazy Vertical/Horizontal Grid
``` Kotlin
LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 128.dp)
) {
    items(photos) { photo ->
        PhotoItem(photo)
    }
}
```
# Lazy Vertical/Horizontal Staggered Grid
Allows you to have elements of different sizes
``` Kotlin
val images = listOf(  
    R.drawable.uno,  
    R.drawable.dos,  
    R.drawable.tres  
)  
LazyVerticalStaggeredGrid (  
    columns = StaggeredGridCells.Fixed(2),  
    verticalItemSpacing = 4.dp,  
    horizontalArrangement = Arrangement.spacedBy(4.dp),  
    content = {  
        items(images.size){ index ->  
            Card {  
                Column {  
                    Image(painter = painterResource(images[index]), "image")  
                    Text("hey")  
                }  
            }  
        }    
    }
)
```

- `.Adaptive` - means each column has to have a minimum width of 128.dp
- `.Fixed` - means there will be a fixed number of columns or rows
