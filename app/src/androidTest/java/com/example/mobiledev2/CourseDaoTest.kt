package com.example.mobiledev2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobiledev2.data.local.AppDatabase
import com.example.mobiledev2.data.local.Course
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CourseDaoTest {

    @Test
    fun insertAndGetAllCourses() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val courseDao = db.courseDao()

        val course1 = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        val course2 = Course(courseCode = "CS102", courseName = "Algoritma", credits = 4, lecturerName = "Dosen B")

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val allCourses = courseDao.getAllCourses().first()
        assertEquals(2, allCourses.size)
        assertEquals("Algoritma", allCourses[0].courseName)

        db.close()
    }

    @Test
    fun getCoursesByCredits() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val courseDao = db.courseDao()

        val course1 = Course(courseCode = "CS101", courseName = "Pemrograman Mobile", credits = 3, lecturerName = "Dosen A")
        val course2 = Course(courseCode = "CS102", courseName = "Basis Data", credits = 2, lecturerName = "Dosen B")

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val filteredCourses = courseDao.getCoursesByCredits(3).first()
        assertEquals(1, filteredCourses.size)
        assertEquals("Pemrograman Mobile", filteredCourses[0].courseName)

        db.close()
    }
}