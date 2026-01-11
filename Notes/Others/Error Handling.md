# Syntax Errors
Syntax errors happen when the compiler detects an issue with Kotlin's syntax rules. These errors prevent your app from running.
**Examples & Fixes:**

| Error                             | Cause                                       | Solution                                                                                                     |
| --------------------------------- | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| `Expecting ')'`                   | Missing closing parenthesis                 | Ensure all parentheses are closed properly.                                                                  |
| `Unresolved reference: TextField` | Jetpack Compose component is not recognised | Import necessary libraries (`import androidx.compose.material3.*`).                                          |
| `Type mismatch`                   | Trying to assign an incorrect data type     | Ensure the expected data type matches the given value (`val age: Int = "25"` should be `val age: Int = 25`). |
| `Expecting ','`                   | Missing comma in a function argument        | Ensure each parameter is separated correctly.                                                                |
| `Modifier is not a function`      | Using `Modifier.padding()` without `()`     | Write `Modifier.padding(16.dp)` instead of `Modifier.padding`.                                               |

# Logic Errors
Logic errors occur when the code runs without crashing but does not behave as expected.
 **Examples & Fixes:**

| Issue                                     | Cause                                                            | Solution                                                                            |
| ----------------------------------------- | ---------------------------------------------------------------- | ----------------------------------------------------------------------------------- |
| **TextField not updating UI**             | The state is not being updated properly                          | Use `remember { mutableStateOf("") }` and ensure `onValueChange` updates the state. |
| **UI not recomposing after state change** | State is not remembered or mutable                               | Use `remember` and `mutableStateOf()`.                                              |
| **Composable function not executing**     | It is wrapped incorrectly or missing `@Composable`               | Ensure functions meant for UI are annotated with `@Composable`.                     |
| **Infinite recomposition**                | Directly modifying state inside `@Composable` without `remember` | Store state properly with `remember`.                                               |

# Run-Time Errors
These errors appear in **Logcat** when the app crashes during execution.
**Examples & Fixes:**

| Error                                                          | Cause                                                             | Solution                                                                                               |
| -------------------------------------------------------------- | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| `java.lang.NullPointerException`                               | Accessing a null value                                            | Ensure objects are initialized before use or use **safe calls (`?.`)** and **Elvis operators (`?:`)**. |
| `java.lang.IllegalStateException: Not in a Composable context` | Calling a composable function outside of a `@Composable` function | Wrap the call inside a `@Composable` function.                                                         |
| `java.lang.OutOfMemoryError`                                   | Loading large images without optimization                         | Use **Coil** for image loading (`AsyncImage`).                                                         |
| `java.lang.ClassCastException`                                 | Incorrect type conversion                                         | Use `as?` instead of `as` to prevent crashes.                                                          |
| `java.lang.IndexOutOfBoundsException`                          | Accessing an index that doesn’t exist in a list                   | Ensure the list has enough elements before accessing.                                                  |

# Time Limit Exceeded Errors
Occurs when your app takes too long to execute an operation.
 **Examples & Fixes:**

|Issue|Cause|Solution|
|---|---|---|
|**App freezes when fetching data**|API request runs on the main thread|Move the network call to a coroutine using `viewModelScope.launch(Dispatchers.IO)`.|
|**Slow database queries**|Running Room database queries on the main thread|Use `suspend` functions with Room for background execution.|
|**Large UI elements slowing rendering**|Too many recompositions|Use `remember` and optimize `LazyColumn` instead of `Column` for lists.|

# Threading and Coroutine Errors
Errors when making network calls or handling background tasks incorrectly.
 **Examples & Fixes:**

| Error                          | Cause                                    | Solution                                                                    |
| ------------------------------ | ---------------------------------------- | --------------------------------------------------------------------------- |
| `NetworkOnMainThreadException` | Running network calls on the main thread | Use `Dispatchers.IO` with `viewModelScope.launch {}`.                       |
| `JobCancellationException`     | Cancelling a coroutine incorrectly       | Use `try { } catch (e: CancellationException) { }` to handle cancellations. |
# HTTP Response Status Codes
[Full Documentation](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
## 200 OK
## 400 Bad Request
The server cannot or will not process the request due to something that is perceived to be a client error (Invalid request message framing, etc)
## 401 Unauthorised
The client must authenticate itself to get the requested response
## 403 Forbidden 
The client does not have access rights to the content (The client's identity is known to the server in this case)
## 404 Not Found
The server cannot find the requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of `403 Forbidden` to hide the existence of a resource from an unauthorised client. This response code is probably the most well known due to its frequent occurrence on the web.
## 408 Request Timeout 
This response is sent on an idle connection by some servers, even without any previous request by the client. It means that the server would like to shut down this unused connection. This response is used much more since some browsers use HTTP pre-connection mechanisms to speed up browsing. Some servers may shut down a connection without sending this message.
**Examples & Fixes:**

|Status Code|Cause|Solution|
|---|---|---|
|**200 OK**|The request was successful|No issue. Handle the response properly.|
|**400 Bad Request**|Incorrect request format (e.g., missing parameters)|Validate inputs before sending the request.|
|**401 Unauthorized**|Authentication token is missing or incorrect|Ensure the API request includes the correct token.|
|**403 Forbidden**|The client does not have permission to access the resource|Check API permissions or use the correct credentials.|
|**404 Not Found**|The requested resource does not exist|Ensure the correct API endpoint is used.|
|**408 Request Timeout**|The server took too long to respond|Implement retry logic with **exponential backoff** using `CoroutineScope` and `delay()`.|
- use `?` for safe calls
- use `!!` when you are 100% it will be non-null