# Auto-Scrolling with Dot Indicators
Dependencies:
```Kotlin
implementation(libs.accompanist.pager)  
implementation(libs.accompanist.pager.indicators)
```
Card:
```Kotlin
@OptIn(ExperimentalPagerApi::class)  
@Composable  
fun MerchantCard(  
    banners: List<Painter>,  
    merchant: String,  
    address: String,  
    pageCount: Int = banners.size,  
    pagerState: PagerState = rememberPagerState(pageCount = {pageCount}),  
    autoScrollDuration: Long = 5000L  
){  
    val defaultImage = painterResource(R.drawable.hungryyy)  
  
    Card(  
        onClick = {},  
        shape = RoundedCornerShape(0.dp),  
    ){  
        Box (  
            modifier = Modifier  
                .height(200.dp)  
                .fillMaxWidth()  
                .background(MaterialTheme.colorScheme.background)  
        ){  
            Column (  
                modifier = Modifier  
                    .fillMaxSize()  
            ){  
                val isDragged = pagerState.interactionSource.collectIsDraggedAsState()  
                if (banners.isNotEmpty()) {  
                    LaunchedEffect(Unit) {  
                        while (true) {  
                            delay(autoScrollDuration)  
                            if (!isDragged.value) {  
                                val nextPage = (pagerState.currentPage + 1) % pageCount  
                                pagerState.animateScrollToPage(nextPage)  
                            }  
                        }  
                    }  
                }  
                Box {  
                    if (banners.isNotEmpty()) {  
                        HorizontalPager(  
                            state = pagerState,  
                            modifier = Modifier.fillMaxWidth(),  
                        ) { page ->  
                            Image(  
                                painter = banners[page],  
                                contentDescription = merchant,  
                                modifier = Modifier.fillMaxWidth()  
                            )  
                        }  
                        Row(  
                            modifier = Modifier.align(Alignment.BottomCenter)  
                        ) {  
                            repeat(pageCount) { iteration ->  
                                val color = if (pagerState.currentPage == iteration) Color(0xFF362F27) else Color(0xFF685D50)  
                                Box(  
                                    modifier = Modifier  
                                        .padding(4.dp)  
                                        .size(8.dp)  
                                        .clip(CircleShape)  
                                        .background(color = color)  
                                )  
                            }  
                        }                    } else {  
                            Image(  
                                painter = defaultImage,  
                                contentDescription = merchant,  
                                modifier = Modifier  
                                    .fillMaxWidth()  
                                    .aspectRatio(24f / 9f),  
                            )  
                    }  
                }  
                Text(  
                    text = merchant,  
                    fontSize = 11.sp,  
                    textAlign = TextAlign.Left,  
                    fontWeight = FontWeight.Bold,  
                    lineHeight = 12.sp,  
                    modifier = Modifier  
                        .padding(top = 10.dp, start = 10.dp)  
                )  
                Row {  
                    Icon(Icons.Filled.LocationOn, contentDescription = "Address")  
                    Text(  
                        text = address,  
                        fontSize = 10.sp,  
                        textAlign = TextAlign.Left,  
                        lineHeight = 12.sp,  
                        modifier = Modifier  
                            .padding(top = 10.dp, start = 10.dp)  
                    )  
                }  
            }        
        }    
    }
}
```

# Onboarding with Dot Indicators
```Kotlin
val titles = listOf(  
    "Find Food You Love",  
    "Fast Delivery",  
    "Live Tracking"  
)  
val descriptions = listOf(  
    "Discover the best foods from over 1000 restaurants and fast delivery to your doorstep.",  
    "Fast food delivery to your home, office, wherever you are.",  
    "Real time tracking of your food on the app once you place your order."  
)  
  
class OnBoardingItems (  
    val image: Int,  
    val title: String,  
    val desc: String,  
){  
    companion object{  
        fun getData(): List<OnBoardingItems>{  
            return listOf(  
                OnBoardingItems(R.drawable.onboarding01, titles[0], descriptions[0]),  
                OnBoardingItems(R.drawable.onboarding02, titles[1], descriptions[1]),  
                OnBoardingItems(R.drawable.onboarding03, titles[2], descriptions[2])  
            )  
        }  
    }  
  
}
```

```Kotlin
@ExperimentalAnimationApi  
@ExperimentalPagerApi  
@Preview  
@Composable  
fun OnBoarding() {  
    val items = OnBoardingItems.getData()  
    val scope = rememberCoroutineScope()  
    val pageState = rememberPagerState(pageCount = 3)  
  
    Column(  
        modifier = Modifier.fillMaxSize(),  
        verticalArrangement = Arrangement.Center,  
        horizontalAlignment = Alignment.CenterHorizontally  
    ) {  
        TopSection(  
            onSkipClick = {  
                if (pageState.currentPage + 1 < items.size) scope.launch {  
                    pageState.scrollToPage(items.size - 1)  
                }  
            }        )  
        HorizontalPager(  
            state = pageState,  
            modifier = Modifier  
                .fillMaxHeight(0.6f)  
                .fillMaxWidth()  
        ) { page ->  
            OnBoardingItem(items = items[page])  
        }  
        Box(  
        ){  
            Indicators(size = items.size, index = pageState.currentPage)  
        }  
        Button(  
            onClick = {  
                if (pageState.currentPage + 1 < items.size) scope.launch {  
                    pageState.scrollToPage(pageState.currentPage + 1)  
                }  
            },  
            modifier = Modifier  
                .padding(top = 12.dp)  
                .fillMaxWidth(0.3f)  
        ) {  
            Text(text = "Next")  
        }  
    }}  
  
@ExperimentalPagerApi  
@Composable  
fun TopSection(onSkipClick: () -> Unit = {}) {  
    Box(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(12.dp)  
    ) {  
        TextButton(  
            onClick = onSkipClick,  
            modifier = Modifier.align(Alignment.CenterEnd),  
            contentPadding = PaddingValues(0.dp)  
        ) {  
            Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)  
        }  
    }}  
  
@Composable  
fun BoxScope.Indicators(size: Int, index: Int) {  
    Row(  
        verticalAlignment = Alignment.CenterVertically,  
        horizontalArrangement = Arrangement.spacedBy(12.dp),  
        modifier = Modifier.align(Alignment.CenterStart)  
    ) {  
        repeat(size) {  
            Indicator(isSelected = it == index)  
        }  
    }}  
  
@Composable  
fun Indicator(isSelected: Boolean) {  
    val width = animateDpAsState(  
        targetValue = if (isSelected) 25.dp else 10.dp,  
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)  
    )  
    Box(  
        modifier = Modifier  
            .height(10.dp)  
            .width(width.value)  
            .clip(CircleShape)  
            .background(  
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0XFFF8E2E7)  
            )  
    ) {  
    }
}  
  
@Composable  
fun OnBoardingItem(items: OnBoardingItems) {  
    Column(  
        horizontalAlignment = Alignment.CenterHorizontally,  
        verticalArrangement = Arrangement.Center,  
        modifier = Modifier.fillMaxSize()  
    ) {  
        Image(  
            painter = painterResource(id = items.image),  
            contentDescription = "Image1",  
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)  
        )  
        Spacer(modifier = Modifier.height(25.dp))  
        Text(  
            text = items.title,  
            style = MaterialTheme.typography.headlineMedium,  
            color = MaterialTheme.colorScheme.onBackground,  
            fontWeight = FontWeight.Bold,  
            textAlign = TextAlign.Center,  
            letterSpacing = 1.sp,  
        )  
        Spacer(modifier = Modifier.height(8.dp))  
        Text(  
            text = items.desc,  
            style = MaterialTheme.typography.bodyLarge,  
            color = MaterialTheme.colorScheme.onBackground,  
            fontWeight = FontWeight.Light,  
            textAlign = TextAlign.Center,  
            modifier = Modifier.padding(10.dp),  
            letterSpacing = 1.sp,  
        )  
    }
}
```