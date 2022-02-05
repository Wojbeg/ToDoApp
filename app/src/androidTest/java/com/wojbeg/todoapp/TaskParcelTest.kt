package com.wojbeg.todoapp

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.Priority
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime


@RunWith(AndroidJUnit4::class)
class TaskParcelTest {

    private lateinit var task: Task
    private lateinit var date: OffsetDateTime

    @Before
    fun createTestTask(){
        date = OffsetDateTime.now()

        task = Task(
            1, false, "test", "Work", date, Priority.Normal
        )

    }

    @Test
    fun writeTaskToParcelAndRead(){
        val parcel = Parcel.obtain()

        task.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val recoveredTask = Task.create(parcel)

        assertEquals(task, recoveredTask)
    }

}