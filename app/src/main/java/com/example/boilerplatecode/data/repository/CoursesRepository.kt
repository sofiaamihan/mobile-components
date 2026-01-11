package com.example.boilerplatecode.data.repository

import androidx.lifecycle.LiveData
import com.example.boilerplatecode.data.dao.CourseDao
import com.example.boilerplatecode.data.entity.Course

class CoursesRepository(private val courseDao: CourseDao) {

    fun getCourses(): LiveData<List<Course>> {
        return courseDao.getCourses()
    }

    suspend fun insertCourse(course: Course){
        courseDao.insertCourse(course)
    }

    suspend fun updateCourse(course: Course) {
        courseDao.updateCourse(course)
    }

    suspend fun deleteCourse(course: Course){
        courseDao.deleteCourse(course.id)
    }
}