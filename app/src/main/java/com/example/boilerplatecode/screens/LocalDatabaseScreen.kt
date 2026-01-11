package com.example.boilerplatecode.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.boilerplatecode.data.CoursesViewModel
import com.example.boilerplatecode.data.entity.Course

@Composable
fun LocalDatabaseScreen(viewModel: CoursesViewModel) {
    val courseListState = viewModel.coursesList.observeAsState(emptyList())
    val courseList = courseListState.value
    var showModal = remember { mutableStateOf(false) }
    var showUpdateModal = remember { mutableStateOf(false) }
    var courseToUpdate = remember { mutableStateOf<Course?>(null) }
    val name = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val teacher = remember { mutableStateOf("") }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    name.value = ""
                    subject.value = ""
                    teacher.value = ""
                    showModal.value = true
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Course")
            }
        }    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(courseList.size) { index ->
                var course = courseList[index]
                CourseItem (
                    course,
                    onDeleteCourse = { courseToDelete ->
                        viewModel.deleteCourse(courseToDelete)
                    },
                    onUpdateCourse = { updatedCourse ->
                        courseToUpdate.value = updatedCourse
                        showUpdateModal.value = true
                    }
                )
            }
        }
        if (showModal.value) {
            NoteInputDialog(
                onDismiss = { showModal.value = false },
                onAddNote = { newCourse ->
                    viewModel.addCourse(newCourse)
                    showModal.value = false
                },
                name = name.value,
                onNameChange = { name.value = it },
                subject = subject.value,
                onSubjectChange = { subject.value = it },
                teacher = teacher.value,
                onTeacherChange = { teacher.value = it }
            )
        }

        if (showUpdateModal.value && courseToUpdate.value != null) {
            ShowUpdateDialog(
                course = courseToUpdate.value!!,
                onDismiss = { showUpdateModal.value = false },
                onUpdateCourse = { updatedCourse ->
                    viewModel.updateCourse(updatedCourse)
                    showUpdateModal.value = false
                }
            )
        }
    }
}

@Composable
fun NoteInputDialog(
    onDismiss: () -> Unit,
    onAddNote: (Course) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    subject: String,
    onSubjectChange: (String) -> Unit,
    teacher: String,
    onTeacherChange: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Course") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Course") }
                )
                TextField(
                    value = subject,
                    onValueChange = onSubjectChange,
                    label = { Text("Subject") }
                )
                TextField(
                    value = teacher,
                    onValueChange = onTeacherChange,
                    label = { Text("Teacher") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddNote(Course(name = name, subject = subject, teacher = teacher))
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
    course: Course,
    onDismiss: () -> Unit,
    onUpdateCourse: (Course) -> Unit
) {
    val name = remember { mutableStateOf(course.name) }
    val subject = remember { mutableStateOf(course.subject) }
    val teacher = remember { mutableStateOf(course.teacher) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Course") },
        text = {
            Column {
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Course") }
                )
                TextField(
                    value = subject.value,
                    onValueChange = { subject.value = it },
                    label = { Text("Subject") }
                )
                TextField(
                    value = teacher.value,
                    onValueChange = { teacher.value = it },
                    label = { Text("Teacher") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdateCourse(Course(
                        id = course.id,
                        name = name.value,
                        subject = subject.value,
                        teacher = teacher.value
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
        }
    )
}

@Composable
fun CourseItem(
    course: Course,
    onUpdateCourse: (Course) -> Unit,
    onDeleteCourse: (Course) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Row {
                    Text(text = "Course: ", fontWeight = FontWeight.Bold)
                    Text(text = course.name)
                }
                Row {
                    Text(text = "Subject: ", fontWeight = FontWeight.SemiBold)
                    Text(text = course.subject)
                }
                Row {
                    Text(text = "Lecturer: ", fontWeight = FontWeight.Medium)
                    Text(text = course.teacher)
                }
            }
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row{
                    IconButton(onClick = {onUpdateCourse(course)}) { Icon(Icons.Filled.Edit, contentDescription = "Edit") }
                    IconButton(onClick = {onDeleteCourse(course)}) { Icon(Icons.Filled.Delete, contentDescription = "Delete") }
                }
            }
        }
    }
}