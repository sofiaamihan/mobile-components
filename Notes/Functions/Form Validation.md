# Simple Text Field Validation
```Kotlin
var fullName = remember { mutableStateOf("") }
var hasInteracted = remember { mutableStateOf(false) }

OutlinedTextField(  
    value = fullName.value,  
    onValueChange = {  
        fullName.value = it  
        hasInteracted.value = true },  
    label = { Text(stringResource(R.string.full_name)) },  
    shape = RoundedCornerShape(32.dp),  
    isError = hasInteracted.value && fullName.value.isEmpty(),  
    supportingText = {  
        if (hasInteracted.value && fullName.value.isEmpty()) {  
            Text("Required Field.", color = Color(0xFFFFA500))  
        }  
    }  
)
```


# Test Cases
- Upper casing
- Lower casing
- White spaces
- Emojis
- Blanks
- Positive Values
- Negative Values
- 0
- Abnormally large values
- Values with many decimals
- e, log, pi
- Length that the database allows

``` Kotlin
@Composable  
fun ValidatedTextField() {  
    var input by remember { mutableStateOf("") }  
    var errorMessage by remember { mutableStateOf<String?>(null) }  
  
    fun validateInput(text: String): String? {  
        when {  
            text.isBlank() -> return "Input cannot be blank"  
            text.any { it.isUpperCase() } -> return "No uppercase letters allowed"  
            text.any { it.isLowerCase() } -> return "No lowercase letters allowed"  
            text.any { it.isWhitespace() } -> return "No white spaces allowed"  
            text.any { Character.getType(it).toByte() == Character.OTHER_SYMBOL } -> return "No emojis allowed"  
            text.toDoubleOrNull()?.let { it < 0 } == true -> return "Negative values not allowed"  
            text.toDoubleOrNull() == 0.0 -> return "Zero is not allowed"  
            text.toDoubleOrNull()?.let { it > 1_000_000 } == true -> return "Value is too large"  
            text.toDoubleOrNull()?.toString()?.contains(".") == true &&  
                    (text.toDoubleOrNull()?.toString()?.split(".")?.get(1)?.length ?: 0) > 5 ->  
                return "Too many decimal places"  
            text.contains("e", ignoreCase = true) || text.contains("log") || text.contains("pi") ->  
                return "Mathematical expressions not allowed"  
            text.length > 255 -> return "Input exceeds allowed database length (255)"  
        }  
        return null  
    }  
  
    OutlinedTextField(  
        value = input,  
        onValueChange = {  
            input = it  
            errorMessage = validateInput(it)  
        },  
        label = { Text("Enter value") },  
        isError = errorMessage != null,  
        modifier = Modifier.fillMaxWidth(),  
    )  
  
    errorMessage?.let {  
        Text(  
            text = it,  
            color = Color.Red,  
            fontSize = 12.sp,  
            modifier = Modifier.padding(top = 4.dp)  
        )  
    }  
}
```
- don't need to complex, like if you only need string just do one that makes sure the character is either an uppercase or lowercase letter (include whitespace if applicable)
- if it's numbers, convert the data types accordingly and compare

