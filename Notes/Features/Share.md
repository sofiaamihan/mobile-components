https://medium.com/@jpmtech/jetpack-compose-add-a-share-button-to-your-app-5f26b7554e94
``` Kotlin
import android.content.Intent

@Composable  
fun Share(text: String, context: Context) {  
    val sendIntent = Intent(Intent.ACTION_SEND).apply {  
        putExtra(Intent.EXTRA_TEXT, text)  
        type = "text/plain"  
    }  
    val shareIntent = Intent.createChooser(sendIntent, null)  
  
  
    Button (onClick = {  
        startActivity(context, shareIntent, null)  
    }) {  
        Icon(imageVector = Icons.Default.Share, contentDescription = null)  
        Text("Share", modifier = Modifier.padding(start = 8.dp))  
    }  
}
```
- `startActivity` is deprecated but it still works

# Future Consideration
- Sending over more complicated data such as the image photo or something details from the card, etc