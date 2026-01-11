# Scrollable Column 
``` Kotlin
Column (  
    modifier = Modifier  
        .verticalScroll(rememberScrollState())
){}
```

# Nested Scrolling
```Kotlin
@Composable  
fun NestedScrolling(){  
	LazyColumn (  
	    modifier = Modifier  
	        .padding(bottom = 120.dp)  
	){  
	   item {  
	       LazyRow (  
	           horizontalArrangement = Arrangement.spacedBy(16.dp)  
	       ){  
	            items(logos.size){ index ->  
	               MerchantButton(  
	                   logo = logos[index],  
	                   merchant = merchants[index]  
	               )  
	           }  
                MerchantCardDisplay(  
			        size = bannerMerchants.size,  
			        banners = banners,  
			        merchants = bannerMerchants,  
			        addresses = addresses  
			    )
	    }
    }
}
  
fun LazyListScope.MerchantCardDisplay(  
    size: Int,  
    banners: List<List<Painter>>,  
    merchants: List<String>,  
    addresses: List<String>,  
){  
    items(size){ index ->  
        MerchantCard(  
            banners = banners[index],  
            merchant = merchants[index],  
            address = addresses[index]  
        )  
    }  
}
```
- If you want to nest a lazy column, specify the height. otherwise, nest a lazy row or use LazyListScope