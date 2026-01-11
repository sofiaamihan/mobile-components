**This works for either static or dynamic lists.**
Filtering through items that have been fetched from the backend.
- TO INCLUDE: various adjustments in variables once the filter is clicked such as 
``` Kotlin
onClick = {
	isDropDownExpanded.value = false
	itemPosition.intValue = index
	contentCategoryId.intValue = categoryIds[index]
}
```
- Although I'm not quite sure how this would react with a large number of items, so far it has been less than 10 but in theory it should be alright.
``` Kotlin
val getAllContentViewModel: GetAllContentViewModel = viewModel(factory = viewModelFactory)
val state = getAllContentViewModel.state
val categoryIds = listOf(0, 1, 2, 3, 4, 5, 6) // Content is dynamic with a fixed number of categories
val contentCategoryId = remember { mutableIntStateOf(0) }

// Include the code for OPEN DROPDOWN and configure accordingly
val filteredContentList = if (contentCategoryId.intValue == 0) {
	 state.contentList
} else {
	 state.contentList.filter { it.contentCategoryId == contentCategoryId.intValue }
}

LazyColumn(
	 modifier = Modifier
		 .fillMaxWidth()
		 .padding(bottom = 125.dp),
	 verticalArrangement = Arrangement.Center
) {
	 items(state.contentList.size) { index ->
		 val bannerResId = when (state.contentList[index].contentCategoryId) {
	 items(filteredContentList.size) { index ->
		 val content = filteredContentList[index]
		 val bannerResId = when (content.contentCategoryId) {
			 1 -> R.drawable.diet1
			 2 -> R.drawable.exercise2
			 3 -> R.drawable.medicine3
			 4 -> R.drawable.mental_health4
			 5 -> R.drawable.environment5
			 6 -> R.drawable.disease6
			 else -> R.drawable.exercise2
		 }
	// ADD LAYOUT ON HOW YOU WANT YOUR CONTENT TO BE DISPLAYED
```
- `categoryIds` - To check the index of the dropdown against this
- `contentCategoryId` - The category id that the filter is currently selecting
- `it.contentCategoryId` - id of the selected content
- `contentCategoryId.intValue` - id of the selected filter

