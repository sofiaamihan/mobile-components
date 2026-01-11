1. **Entity**: Defines the database table
2. **DAO** (Data Access Object): Contains the database queries
3. **Database**: Provides a clean API for data access
4. **Repository**: Single Source of truth
5. **ViewModel**: Connects the data to the UI and handles business logic
6. **ViewModel Factory**: Provides the ViewModel instances
7. **UI** (Jetpack Compose Composables): Displays data from the VM

# Setting Up
## Overview
1. Setting Up KSP
2. Setting Up Respective Dependencies
3. Important Imports

## `build.gradle.kts` - Project Level
```Kotlin
id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
```

## `libs.version.toml`
```TOML
[versions]
roomRuntime = "2.6.1"  
runtimeLivedata = "1.7.7"

[libraries]
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "roomRuntime" }  
androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "roomRuntime" }  
androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "roomRuntime" }  
androidx-runtime-livedata = { module = "androidx.compose.runtime:runtime-livedata", version.ref = "runtimeLivedata" }
```

## `build.gradle.kts` - Module Level
```Kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(libs.androidx.room.runtime)  
	ksp(libs.androidx.room.compiler)  
	implementation(libs.androidx.runtime.livedata)  
	implementation(libs.androidx.room.ktx)
}
```

## Imports
```Kotlin
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState

// If you want to use 'by' in your view model
import androidx.compose.runtime.getValue  
import androidx.compose.runtime.setValue
```

# Entity (Data Class)
```Kotlin
@Entity(tableName = "notes_table")  
data class Note(  
    @PrimaryKey(autoGenerate = true)  
    val id: Int = 0,  
    val title: String,  
    val category: String,  
    val description: String  
)
```

# DAO (Data Access Object)
```Kotlin
@Dao  
interface NoteDao {  
  
    @Query("SELECT * FROM notes_table")  
    fun getNotes(): LiveData<List<Note>>  
  
    @Upsert  
    suspend fun insertNotes(notes: Note)  
  
    @Update  
    suspend fun updateNotes(notes: Note)  
  
    @Query("DELETE FROM notes_table WHERE id = :noteId")  
    suspend fun clearNotes(noteId: Int)  
  
}
```

# Database
```Kotlin
@Database(  
    entities = [Note::class],  
    version = 1,  
    exportSchema = false  
)  
abstract class NotesDatabase : RoomDatabase() {  
    abstract val noteDao: NoteDao  
  
    companion object {  
        @Volatile  
        private var INSTANCE: NotesDatabase? = null  
  
        fun getDatabase(context: Context): NotesDatabase {  
            return INSTANCE ?: synchronized(this) {  
                val instance = Room.databaseBuilder(  
                    context.applicationContext,  
                    NotesDatabase::class.java,  
                    "notes_database"  
                ).fallbackToDestructiveMigration().build()  
                INSTANCE = instance  
                instance  
            }  
        }  
    }  
}
```

# Repository
```Kotlin
class NotesRepository(private val noteDao: NoteDao) {  
  
    fun getNotes(): LiveData<List<Note>> {  
        return noteDao.getNotes()  
    }  
  
    suspend fun insertNotes(note: Note){  
        noteDao.insertNotes(note)  
    }  
  
    suspend fun updateNotes(note: Note) {  
        noteDao.updateNotes(note)  
    }  
  
    suspend fun deleteNotes(note: Note){  
        noteDao.clearNotes(note.id)  
    }  
}
```

# ViewModel
```Kotlin
class NotesViewModel(application: Application): AndroidViewModel(application) {  
  
    private val repository: NotesRepository  
  
    val noteList: LiveData<List<Note>>  
  
    init {  
        val database = NotesDatabase.getDatabase(application)  
        val noteDao = database.noteDao  
        repository = NotesRepository(noteDao)  
        noteList = repository.getNotes()  
    }  
  
    fun addNote(note: Note) {  
        viewModelScope.launch {  
            repository.insertNotes(note)  
        }  
    }  
  
    fun updateNote(note: Note) {  
        viewModelScope.launch {  
            repository.updateNotes(note)  
        }  
    }  
  
    fun clearNote(note: Note) {  
        viewModelScope.launch{  
            repository.deleteNotes(note)  
        }  
    }  
      
}
```

# ViewModelFactory
```Kotlin
class NotesViewModelFactory(  
    private val application: Application  
): ViewModelProvider.Factory{  
  
    override fun <T : ViewModel> create(modelClass: Class<T>): T {  
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {  
            @Suppress("UNCHECKED_CAST")  
            return NotesViewModel(application) as T  
        }  
        throw IllegalArgumentException("Unable to construct ViewModel")  
    }  
}
```

# Simple Application
```Kotlin
class MainActivity : ComponentActivity() {  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
  
        val viewModelFactory = NotesViewModelFactory(application)  
        val viewModel: NotesViewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]  
  
        enableEdgeToEdge()  
        setContent {  
            NotesApplicationTheme {  
                NotesListScreen(viewModel)  
            }  
        }    }  
}  
  
@Composable  
fun NotesListScreen(viewModel: NotesViewModel) {  
    val noteListState = viewModel.noteList.observeAsState(emptyList())  
    val noteList = noteListState.value  
    var showModal = remember { mutableStateOf(false) }  
    var showUpdateModal = remember { mutableStateOf(false) }  
    var noteToUpdate = remember { mutableStateOf<Note?>(null) }  
    val title = remember { mutableStateOf("") }  
    val category = remember { mutableStateOf("") }  
    val description = remember { mutableStateOf("") }  
  
    Scaffold(  
        floatingActionButton = {  
            FloatingActionButton(  
                onClick = {  
                    title.value = ""  
                    category.value = ""  
                    description.value = ""  
                    showModal.value = true  
                },  
                modifier = Modifier.padding(16.dp)  
            ) {  
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")  
            }  
        }    ) { paddingValues ->  
        LazyColumn(  
            modifier = Modifier.padding(paddingValues)  
        ) {  
            items(noteList.size) { index ->  
                var note = noteList[index]
                NoteItem(  
                    note,  
                    onDeleteNote = { noteToDelete ->  
                        viewModel.clearNote(noteToDelete)  
                    },  
                    onUpdateNote = { updatedNote ->  
                        noteToUpdate.value = updatedNote  
                        showUpdateModal.value = true  
                    }  
                )  
            }  
        }  
        if (showModal.value) {  
            NoteInputDialog(  
                onDismiss = { showModal.value = false },  
                onAddNote = { newNote ->  
                    viewModel.addNote(newNote)  
                    showModal.value = false  
                },  
                title = title.value,  
                onTitleChange = { title.value = it },  
                category = category.value,  
                onCategoryChange = { category.value = it },  
                description = description.value,  
                onDescriptionChange = { description.value = it }  
            )  
        }  
  
        if (showUpdateModal.value && noteToUpdate.value != null) {  
            ShowUpdateDialog(  
                note = noteToUpdate.value!!,  
                onDismiss = { showUpdateModal.value = false },  
                onUpdateNote = { updatedNote ->  
                    viewModel.updateNote(updatedNote)  
                    showUpdateModal.value = false  
                }  
            )  
        }  
    }  
}  
  
@Composable  
fun NoteInputDialog(  
    onDismiss: () -> Unit,  
    onAddNote: (Note) -> Unit,  
    title: String,  
    onTitleChange: (String) -> Unit,  
    category: String,  
    onCategoryChange: (String) -> Unit,  
    description: String,  
    onDescriptionChange: (String) -> Unit  
) {  
    AlertDialog(  
        onDismissRequest = onDismiss,  
        title = { Text("Add Note") },  
        text = {  
            Column {  
                TextField(  
                    value = title,  
                    onValueChange = onTitleChange,  
                    label = { Text("Title") }  
                )  
                TextField(  
                    value = category,  
                    onValueChange = onCategoryChange,  
                    label = { Text("Category") }  
                )  
                TextField(  
                    value = description,  
                    onValueChange = onDescriptionChange,  
                    label = { Text("Description") }  
                )  
            }  
        },  
        confirmButton = {  
            TextButton(  
                onClick = {  
                    onAddNote(Note(title = title, category = category, description = description))  
                }  
            ) {  
                Text("Add")  
            }  
        },  
        dismissButton = {  
            TextButton(onClick = onDismiss) {  
                Text("Cancel")  
            }  
        }    )  
}  
  
@Composable  
fun ShowUpdateDialog(  
    note: Note,  
    onDismiss: () -> Unit,  
    onUpdateNote: (Note) -> Unit  
) {  
    val title = remember { mutableStateOf(note.title) }  
    val category = remember { mutableStateOf(note.category) }  
    val description = remember { mutableStateOf(note.description) }  
  
    AlertDialog(  
        onDismissRequest = onDismiss,  
        title = { Text("Update Note") },  
        text = {  
            Column {  
                TextField(  
                    value = title.value,  
                    onValueChange = { title.value = it },  
                    label = { Text("Title") }  
                )  
                TextField(  
                    value = category.value,  
                    onValueChange = { category.value = it },  
                    label = { Text("Category") }  
                )  
                TextField(  
                    value = description.value,  
                    onValueChange = { description.value = it },  
                    label = { Text("Description") }  
                )  
            }  
        },  
        confirmButton = {  
            TextButton(  
                onClick = {  
                    onUpdateNote(Note(  
                        id = note.id,  
                        title = title.value,  
                        category = category.value,  
                        description = description.value  
                    ))  
                    onDismiss()  
                }  
            ) {  
                Text("Update")  
            }  
        },  
        dismissButton = {  
            TextButton(onClick = onDismiss) {  
                Text("Cancel")  
            }  
        }    )  
}  
  
@Composable  
fun NoteItem(  
    note: Note,  
    onUpdateNote: (Note) -> Unit,  
    onDeleteNote: (Note) -> Unit,  
) {  
    Card(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(8.dp),  
    ) {  
        Column(modifier = Modifier.padding(16.dp)) {  
            Row {  
                Text(text = "Title: ", fontWeight = FontWeight.Bold)  
                Text(text = note.title)  
            }  
            Row {  
                Text(text = "Category: ", fontWeight = FontWeight.Bold)  
                Text(text = note.category)  
            }  
            Row {  
                Text(text = "Description: ", fontWeight = FontWeight.Bold)  
                Text(text = note.description)  
            }  
            Row(  
                modifier = Modifier.fillMaxWidth(),  
                verticalAlignment = Alignment.CenterVertically,  
                horizontalArrangement = Arrangement.Center  
            ) {  
                TextButton(  
                    onClick = {  
                        onUpdateNote(note)  
                    },  
                ) {  
                    Text("Edit")  
                }  
                TextButton(  
                    onClick = {  
                        onDeleteNote(note)  
                    },  
                ) {  
                    Text("Delete")  
                }  
            }        
        }    
    }
}
```

- changed is the way lazy column is used