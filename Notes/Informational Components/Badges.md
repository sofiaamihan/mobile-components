# Basic
``` Kotlin
var cartItemCount = remember { mutableIntStateOf(0) }

fun addToCart() {  
	cartItemCount.intValue = cartItemCount.intValue + 1  
}  

Box(modifier = Modifier.wrapContentSize()) {  
	IconButton(onClick = { addToCart() }) {  
		Icon(  
			imageVector = Icons.Filled.ShoppingCart,  
			contentDescription = "My Cart"  
		)  
	}  
	
	if (cartItemCount.intValue > 0) {  
		Badge(  
			modifier = Modifier  
				.size(20.dp)  
				.align(Alignment.TopEnd),  
			content = { Text(text = cartItemCount.intValue.toString()) },  
		)  
	}  
}
```

# Hoisted
```Kotlin
fun MerchantAppBar(  
    title: String,  
    cartItemCount: MutableIntState,  
){  
    ...
                actions = {  
                    Box(modifier = Modifier.wrapContentSize()) {  
                        IconButton(onClick = { /* do something */ }) {  
                            Icon(  
                                imageVector = Icons.Filled.ShoppingCart,  
                                contentDescription = "My Cart"  
                            )  
                        }  
                        if (cartItemCount.intValue > 0) {  
                            Badge(  
                                modifier = Modifier  
                                    .size(20.dp)  
                                    .align(Alignment.TopEnd),  
                                content = { Text(text = cartItemCount.intValue.toString()) },  
                            )  
                        }  
                    }  
                    },  
            )  
        }  
    ){  
        var padding = it  
    }}
```

```Kotlin
var cartItemCount = remember { mutableIntStateOf(0) }  
fun addToCart() {  
    cartItemCount.intValue = cartItemCount.intValue + 1  
}
```
