# Validation
## Use Cases
1. Required Field
2. Email Validation
3. Password Validation
4. Phone Number Validation
## Functions
``` Kotlin
fun validateNRIC(nric: String): String? {  
    val regex = Regex("^[A-Za-z]\\d{7}[A-Za-z]$")  
    if (!regex.matches(nric)) return "Invalid NRIC format."  
    return null  
}  

fun validatePhoneNumber(phone: Int): String? {
    if (!phone.all { it.isDigit() } || phone.length !in 8..15) "Invalid phone number" else null
}
  
fun validateEmail(email: String): String? {  
    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Invalid Email Format"  
    // Maybe add database check for existing user  
    return null  
}  
  
fun validatePassword(password: String): String? {  
    if(password.length < 8) return "Password must be at least 8 characters"  
    if (!password.any{ it.isUpperCase() }) return "Password must contain at least one uppercase letter"  
    if (!password.any{ it.isLowerCase() }) return "Password must contain at least one lowercase letter"  
    if (!password.any{ it.isDigit() }) return "Password must contain at least one number"  
    if (!password.any{ !it.isLetterOrDigit() }) return "Password must contain at least one special character"  
    return null  
}
```

## Application
``` Kotlin
var nric = remember { FieldInput() }  
var password = remember { FieldInput() }

CustomTextField(  
    label = stringResource(R.string.nric),  
    fieldInput = nric,  
    validation = { validateNRIC(it) }  
)
```

# Data Class
``` Kotlin
data class FieldInput(  
    var value: MutableState<String> = mutableStateOf(""),  
    var hasInteracted: MutableState<Boolean> = mutableStateOf(false)  
)  
```

# Custom Field Inputs
``` Kotlin
@Composable  
fun CustomTextField(  
    modifier: Modifier = Modifier,  
    label: String,  
    fieldInput: FieldInput,  
    isPasswordField: Boolean = false,  
    validation: (String) -> String? = { null },  
) {  
    val passwordVisible = remember { mutableStateOf(false) }  
    val errorMessage = remember { mutableStateOf<String?>(null) }  
  
    OutlinedTextField(  
        modifier = modifier,  
        value = fieldInput.value.value,  
        onValueChange = {  
            fieldInput.value.value = it  
            fieldInput.hasInteracted.value = true  
            errorMessage.value = validation(it)  
        },  
        label = { Text(label) },  
        visualTransformation = if (isPasswordField && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,  
        supportingText = {  
            errorMessage.value?.let { Text(text = it, color = Color.Red) }  
        },  
        trailingIcon = {  
            if (isPasswordField) {  
                val icon: ImageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff  
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {  
                    Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")  
                }  
            }  
        },  
        colors = TextFieldDefaults.colors(  
            focusedTextColor = Color.White,  
            unfocusedTextColor = Color.White,  
            focusedIndicatorColor = Color.White,  
            unfocusedIndicatorColor = Color.White,  
            focusedLabelColor = Color.White,  
            unfocusedLabelColor = Color.White,  
            focusedContainerColor = Color.Transparent,  
            unfocusedContainerColor = Color.Transparent  
        )  
    )  
}
```

# Considerations
- Android has a pattern matcher where you can check if its an email instead of making it a regex - goes for many different kinds of inputs - `(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())`
- Only enable the submit button once everything has been filled
- `leadingIcon`
- `singleLine`
- `isError`
- `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)`
- `maxLines`
- `minLines`
- WORD COUNT: `supportingText = { Text("${value.length} / $maxLength") }` - then specify maxLength in the parameter

# Restrictions
## Use Cases
1. Required Fields
2. Only Numeric Inputs
3. Date Validation(Only a specific date) -  In Date Picker Code
4. Checkbox Validation
## CASE: All Filled
``` Kotlin
val isFormValid = remember(activityCategoryId){  
    title.isNotBlank() && summary.isNotBlank() && description.isNotBlank() && contentCategoryId > 0  
}
// Then only enable the button if true
```

## CASE: Int
``` Kotlin
OutlinedTextField(  
    value = age.takeIf { it != 0 }?.toString() ?: "",  
    onValueChange = { newValue ->  
        onAgeChange(newValue.toIntOrNull() ?: 0)  
    },  
    label = { Text("Age") },  
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)  
)
```

## CASE: Double
``` Kotlin
OutlinedTextField(  
    value = weight.takeIf { it != 0.0 }?.toString() ?: "",  
    onValueChange = { newValue ->  
        val parsedValue = newValue.toDoubleOrNull()  
        if (parsedValue != null) onWeightChange(parsedValue)  
    },  
    label = { Text("Weight") }  
)
```

## CASE: Checkbox
``` Kotlin
var isChecked by remember { mutableStateOf(false) }
// Set accordingly
```

## CASE: Word Count
``` Kotlin
@Composable  
fun LimitedTextField(  
    value: String,  
    onValueChange: (String) -> Unit,  
    label: String,  
    maxLength: Int,  
    modifier: Modifier = Modifier,  
    maxLines: Int = 1  
) {  
    OutlinedTextField(  
        value = value,  
        onValueChange = {  
            if (it.length <= maxLength) {  
                onValueChange(it)  
            }  
        },  
        label = { Text(label) },  
        supportingText = { Text("${value.length} / $maxLength") },  
        modifier = modifier,  
        maxLines = maxLines,  
        singleLine = maxLines == 1  
    )  
}
```