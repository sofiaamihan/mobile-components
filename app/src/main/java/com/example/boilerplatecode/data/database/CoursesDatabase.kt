package com.example.boilerplatecode.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.boilerplatecode.data.dao.CourseDao
import com.example.boilerplatecode.data.entity.Course

@Database(
    entities = [Course::class],
    version = 1,
    exportSchema = false
)
abstract class CoursesDatabase : RoomDatabase() {
    abstract val courseDao: CourseDao

    companion object {
        @Volatile
        private var INSTANCE: CoursesDatabase? = null

        fun getDatabase(context: Context): CoursesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoursesDatabase::class.java,
                    "courses_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}