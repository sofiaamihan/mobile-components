package com.example.boilerplatecode.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.boilerplatecode.data.database.CoursesDatabase
import com.example.boilerplatecode.data.entity.Course
import com.example.boilerplatecode.data.repository.CoursesRepository
import kotlinx.coroutines.launch

class CoursesViewModel(application: Application): AndroidViewModel(application) {

    private val repository: CoursesRepository

    val coursesList: LiveData<List<Course>>

    init {
        val database = CoursesDatabase.getDatabase(application)
        val courseDao = database.courseDao
        repository = CoursesRepository(courseDao)
        coursesList = repository.getCourses()
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            repository.insertCourse(course)
        }
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch {
            repository.updateCourse(course)
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch{
            repository.deleteCourse(course)
        }
    }

}