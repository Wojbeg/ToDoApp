package com.wojbeg.todoapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.TasksConstants
import com.wojbeg.todoapp.utils.TasksConstants.NUMBER_OF_TASKS_FOR_TEST
import com.wojbeg.todoapp.utils.TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.Mockito.doReturn

@RunWith(MockitoJUnitRunner::class)
class RepositoryMockTest : FakeDB(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var initRule:MockitoRule = MockitoJUnit.rule()

    private val fakeRepository: ToDoRepositoryInterface = Mockito.mock(ToDoRepositoryInterface::class.java)


    @Before
    fun setUp(){
        Mockito.doReturn(numOfTasksLiveData).`when`(fakeRepository).getNumberOfTasks()

        Mockito.doReturn(sortedIdLiveData).`when`(fakeRepository).getSavedTasks()
        Mockito.doReturn(sortedStatusLiveData).`when`(fakeRepository).getSavedTasksByStatus()
        Mockito.doReturn(sortedNameLiveData).`when`(fakeRepository).getSavedTasksByName()
        Mockito.doReturn(sortedDateLiveData).`when`(fakeRepository).getSavedTasksByDate()
        Mockito.doReturn(sortedPriorityLiveData).`when`(fakeRepository).getSavedTasksByPriority()
        Mockito.doReturn(sortedTypeLiveData).`when`(fakeRepository).getSavedTasksByType()

    }

    @Test
    fun `get number of tasks mockito, if 12 return true`(){
        Assert.assertEquals(fakeRepository.getNumberOfTasks().value, NUMBER_OF_TASKS_FOR_TEST)
    }

    @Test
    fun `get tasks by ID mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasks().value, TasksConstants.sortedById)
    }

    @Test
    fun `get tasks by STATUS mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasksByStatus().value, TasksConstants.sortedByStatus)
    }

    @Test
    fun `get tasks by NAME mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasksByName().value, TasksConstants.sortedByName)
    }

    @Test
    fun `get tasks by DATE mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasksByDate().value, TasksConstants.sortedByDate)
    }

    @Test
    fun `get tasks by PRIORITY mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasksByPriority().value, TasksConstants.sortedByPriority)
    }

    @Test
    fun `get tasks by TYPE mockito, if equal to TasksConstants return true`(){
        Assert.assertEquals(fakeRepository.getSavedTasksByType().value, TasksConstants.sortedByType)
    }


    @Test
    fun `insert task test, sortedIdLiveData contains new added task, return true`() = runBlockingTest{

        doReturn(tasks.add(TEST_TASK_TO_ADD_AND_DELETE)).`when`(fakeRepository).insert(TEST_TASK_TO_ADD_AND_DELETE)

        fakeRepository.insert(TEST_TASK_TO_ADD_AND_DELETE)
        sortedIdLiveData.postValue(tasks)

        Assert.assertEquals(sortedIdLiveData.value?.contains(TEST_TASK_TO_ADD_AND_DELETE), true)

        tasks.remove(TEST_TASK_TO_ADD_AND_DELETE)
        sortedIdLiveData.postValue(tasks)
    }

    @Test
    fun `delete task test, sortedIdLiveData contains new added task, if false then return true`() = runBlockingTest{

        doReturn(tasks.add(TEST_TASK_TO_ADD_AND_DELETE)).`when`(fakeRepository).insert(TEST_TASK_TO_ADD_AND_DELETE)

        fakeRepository.insert(TEST_TASK_TO_ADD_AND_DELETE)
        tasks.remove(TEST_TASK_TO_ADD_AND_DELETE)
        sortedIdLiveData.postValue(tasks)

        Assert.assertEquals(sortedIdLiveData.value?.contains(TEST_TASK_TO_ADD_AND_DELETE), false)

    }

}