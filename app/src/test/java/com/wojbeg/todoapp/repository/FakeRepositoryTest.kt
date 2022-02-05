package com.wojbeg.todoapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wojbeg.todoapp.utils.TasksConstants
import com.wojbeg.todoapp.utils.TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
import com.wojbeg.todoapp.utils.TasksConstants.task1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FakeRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeTaskRepository

    @Before
    fun setUp(){
        repository = FakeTaskRepository()
    }

    @Test
    fun `get number of tasks liveData, if 12 returns true`(){
        Assert.assertEquals(repository.getNumberOfTasks().value, TasksConstants.NUMBER_OF_TASKS_FOR_TEST)
    }

    @Test
    fun `get saved tasks sorted by id, returns true`(){
        Assert.assertEquals(repository.getSavedTasks().value, TasksConstants.sortedById)
    }

    @Test
    fun `get saved tasks sorted by status, returns true`(){
        Assert.assertEquals(repository.getSavedTasksByStatus().value, TasksConstants.sortedByStatus)
    }

    @Test
    fun `get saved tasks sorted by name, returns true`(){
        Assert.assertEquals(repository.getSavedTasksByName().value, TasksConstants.sortedByName)
    }

    @Test
    fun `get saved tasks sorted by type, returns true`(){
        Assert.assertEquals(repository.getSavedTasksByType().value, TasksConstants.sortedByType)
    }

    @Test
    fun `get saved tasks sorted by date, returns true`(){
        Assert.assertEquals(repository.getSavedTasksByDate().value, TasksConstants.sortedByDate)
    }

    @Test
    fun `get saved tasks sorted by priority, returns true`(){
        Assert.assertEquals(repository.getSavedTasksByPriority().value, TasksConstants.sortedByPriority)
    }

    @Test
    fun `contains task test, return true`(){
        //task1 from TasksConstants for tests
        Assert.assertEquals(repository.contains(task1), true)
    }

    @Test
    fun `insert task test, tasks contains new added task, return true`() = runBlockingTest {

        repository.insert(TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(repository.contains(TEST_TASK_TO_ADD_AND_DELETE), true)
        repository.deleteTask(TEST_TASK_TO_ADD_AND_DELETE)
    }

    @Test
    fun `delete task test, tasks contains remove added task, return false`() = runBlockingTest {

        repository.insert(TEST_TASK_TO_ADD_AND_DELETE)
        repository.deleteTask(TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(repository.contains(TEST_TASK_TO_ADD_AND_DELETE), false)
    }



}