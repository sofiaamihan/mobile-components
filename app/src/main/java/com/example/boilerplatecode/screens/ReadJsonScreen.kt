package com.example.boilerplatecode.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.boilerplatecode.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

data class Event (
    val eventID: String,
    val name: String,
    val description: String,
    val date: String,
    val location: String
)

fun readNorthEvents(context: Context): List<Event> {
    val inputStream = context.resources.openRawResource(R.raw.event_informations)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val jsonString = bufferedReader.use { it.readText() }

    val jsonObject = JSONObject(jsonString)
    val eventsArray = jsonObject.getJSONArray("North") // Modify Accordingly

    val eventList = mutableListOf<Event>()

    for (i in 0 until eventsArray.length()) {
        val item = eventsArray.getJSONObject(i)
        val event = Event(
            eventID = item.getString("Event_ID"),
            name = item.getString("Name"),
            description = item.getString("Description"),
            date = item.getString("Date and Time"),
            location = item.getString("Location")
        )
        eventList.add(event)
    }

    return eventList
}

@Composable
fun ReadJsonScreen(){
    val northEvents = readNorthEvents(LocalContext.current)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(northEvents.size){ index ->
            val event = northEvents[index]
            Text(event.name)
        }
    }
}