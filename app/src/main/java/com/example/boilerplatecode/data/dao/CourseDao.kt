package com.example.boilerplatecode.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.boilerplatecode.data.entity.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM course_table")
    fun getCourses(): LiveData<List<Course>>

    @Upsert
    suspend fun insertCourse(courses: Course)

    @Update
    suspend fun updateCourse(courses: Course)

    @Query("DELETE FROM course_table WHERE id = :courseId")
    suspend fun deleteCourse(courseId: Int)
}
