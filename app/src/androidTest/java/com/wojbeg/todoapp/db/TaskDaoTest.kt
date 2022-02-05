package com.wojbeg.todoapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.wojbeg.todoapp.getOrAwaitValue
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.Priority
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class TaskDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TasksDatabase
    private lateinit var dao: TaskDao
    private lateinit var date: OffsetDateTime

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getTaskDao()
        date = OffsetDateTime.now()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertTaskToDatabaseGetSavedTasks() = runBlockingTest {
       val task = Task(1, false, "test", "Work", date,Priority.Normal)
        dao.insert(task)

        val articles = dao.getSavedTasks().getOrAwaitValue()

        assertThat(articles).contains(task)
    }

    @Test
    fun insertTaskToDatabaseGetNumberOfTasks() = runBlockingTest {
        val task = Task(1, false, "test", "Work", date,Priority.Normal)
        dao.insert(task)

        val articles = dao.getNumberOfTasks().getOrAwaitValue()

        assertThat(articles).isEqualTo(1)
    }

    @Test
    fun deleteTaskFromDatabase() = runBlockingTest {
        val task = Task(1, false, "test", "Work", date,Priority.Normal)

        dao.insert(task)
        dao.deleteTask(task)

        val articles = dao.getSavedTasks().getOrAwaitValue()

        assertThat(articles).doesNotContain(task)
    }

    @Test
    fun updateTask() = runBlockingTest {
        val task = Task(1, false, "test", "Work", date,Priority.Normal)
        val updatedTask = Task(1, true, "Updated test", "Work", date,Priority.High)

        dao.insert(task)
        dao.update(updatedTask)

        val articles = dao.getSavedTasks().getOrAwaitValue()

        assertThat(articles).contains(updatedTask)
    }

    @Test
    fun tasksSavedByStatus() = runBlockingTest {
        val task1 = Task(1, true, "test", "Work", date,Priority.Normal)
        val task2 = Task(2, false, "test", "Work", date,Priority.Normal)
        val task3 = Task(3, false, "test", "Work", date,Priority.Normal)

        val correctOrder = listOf(task2, task3, task1)
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)

        val articles = dao.getSavedTasksByStatus().getOrAwaitValue()

        assertThat(articles).isEqualTo(correctOrder)
    }

    @Test
    fun tasksSavedByName() = runBlockingTest {
        val task1 = Task(1, true, "Z", "Work", date,Priority.Normal)
        val task2 = Task(2, false, "B", "Work", date,Priority.Normal)
        val task3 = Task(3, false, "A", "Work", date,Priority.Normal)
        val task4 = Task(4, false, "a", "Work", date,Priority.Normal)

        val correctOrder = listOf(task3, task4, task2, task1)
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)
        dao.insert(task4)

        val articles = dao.getSavedTasksByName().getOrAwaitValue()

        assertThat(articles).isEqualTo(correctOrder)
    }

    @Test
    fun tasksSavedByType() = runBlockingTest {
        val task1 = Task(1, false, "a", "Work", date,Priority.Normal)
        val task2 = Task(2, false, "a", "Shopping", date,Priority.Normal)
        val task3 = Task(3, false, "a", "Education", date,Priority.Normal)
        val task4 = Task(4, false, "a", "Sports", date,Priority.Normal)
        val task5 = Task(5, false, "a", "Entertainment", date,Priority.Normal)
        val task6 = Task(6, false, "a", "Other", date,Priority.Normal)

        val correctOrder = listOf(task3, task5, task6, task2, task4, task1)
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)
        dao.insert(task4)
        dao.insert(task5)
        dao.insert(task6)

        val articles = dao.getSavedTasksByType().getOrAwaitValue()

        assertThat(articles).isEqualTo(correctOrder)
    }

    @Test
    fun tasksSavedByDate() = runBlockingTest {
        val task1 = Task(1, false, "a", "Work", date.plusDays(10) ,Priority.Normal)
        val task2 = Task(2, false, "a", "Shopping", date.minusDays(20),Priority.Normal)
        val task3 = Task(3, false, "a", "Education", date.plusHours(5),Priority.Normal)
        val task4 = Task(4, false, "a", "Education", date,Priority.Normal)

        val correctOrder = listOf(task2, task4, task3, task1)
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)
        dao.insert(task4)

        val articles = dao.getSavedTasksByDate().getOrAwaitValue()

        assertThat(articles).isEqualTo(correctOrder)
    }

    @Test
    fun tasksSavedByPriority() = runBlockingTest {
        val task1 = Task(1, false, "a", "Education", date, Priority.Low)
        val task2 = Task(2, false, "a", "Education", date, Priority.Medium)
        val task3 = Task(3, false, "a", "Education", date, Priority.Normal)
        val task4 = Task(4, false, "a", "Education", date, Priority.Low)
        val task5 = Task(5, false, "a", "Education", date, Priority.Extreme)
        val task6 = Task(6, false, "a", "Education", date, Priority.High)

        val correctOrder = listOf(task5, task6, task2, task3, task1, task4)
        dao.insert(task1)
        dao.insert(task2)
        dao.insert(task3)
        dao.insert(task4)
        dao.insert(task5)
        dao.insert(task6)

        val articles = dao.getSavedTasksByPriority().getOrAwaitValue()

        assertThat(articles).isEqualTo(correctOrder)
    }

}