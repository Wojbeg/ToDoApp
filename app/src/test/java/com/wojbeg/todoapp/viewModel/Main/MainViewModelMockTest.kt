package com.wojbeg.todoapp.viewModel.Main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wojbeg.todoapp.repository.FakeDB
import com.wojbeg.todoapp.repository.ToDoRepositoryInterface
import com.wojbeg.todoapp.ui.viewmodels.mainViewModel.MainViewModel
import com.wojbeg.todoapp.utils.MainCoroutineRule
import com.wojbeg.todoapp.utils.SortType
import com.wojbeg.todoapp.utils.TasksConstants
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelMockTest : FakeDB() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

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

        mainViewModel = MainViewModel(fakeRepository)

    }

    //if need to test insertion and deletion of other types of life
    //data, just add in these two places

    @Test
    fun `get number of tasks, if 12 then ture`(){
        Assert.assertEquals(mainViewModel.getNumberOfTasks().value, TasksConstants.NUMBER_OF_TASKS_FOR_TEST)
    }

    @Test
    fun `insert task test, get new number of tasks, if 13 then true`() = runBlockingTest {

        Mockito.doReturn(
            tasks.add(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
            }
        ).`when`(fakeRepository).insert(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(mainViewModel.getNumberOfTasks().value, TasksConstants.NUMBER_OF_TASKS_FOR_TEST +1)

        Mockito.doReturn(
            tasks.remove(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
            }
        ).`when`(fakeRepository).deleteTask(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)
    }

    @Test
    fun `delete task test, get number of tasks, if is 12 return true`() = runBlockingTest {

        Mockito.doReturn(
            tasks.add(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
            }
        ).`when`(fakeRepository).insert(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Mockito.doReturn(
            tasks.remove(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
            }
        ).`when`(fakeRepository).deleteTask(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(mainViewModel.getNumberOfTasks().value, TasksConstants.NUMBER_OF_TASKS_FOR_TEST)
    }


    @Test
    fun `insert task test, sortedIdLiveData contains new added task, return true`() = runBlockingTest{

        Mockito.doReturn(
            tasks.add(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
                println(tasks)
                println(tasks.size)
            }
        ).`when`(fakeRepository).insert(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(sortedIdLiveData.value?.contains(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE), true)

        Mockito.doReturn(
            tasks.remove(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
                println(tasks)
                println(tasks.size)
            }
        ).`when`(fakeRepository).deleteTask(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)
    }

    @Test
    fun `delete task test, sortedIdLiveData contains new added task, if false then return true`() = runBlockingTest{

        Mockito.doReturn(
            tasks.add(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
            }
        ).`when`(fakeRepository).insert(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Mockito.doReturn(
            tasks.remove(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE).also {
                numOfTasksLiveData.postValue(tasks.size)
                sortedIdLiveData.postValue(tasks)
                println(tasks)
                println(tasks.size)
            }
        ).`when`(fakeRepository).deleteTask(
            TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
        )

        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)
        sortedIdLiveData.postValue(tasks)

        Assert.assertEquals(sortedIdLiveData.value?.contains(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE), false)

    }



    @Test
    fun `get live data sorted by ID, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.ID)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedById)
    }

    @Test
    fun `get live data sorted by TYPE, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.TYPE)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedByType)
    }

    @Test
    fun `get live data sorted by STATUS, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.STATUS)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedByStatus)
    }

    @Test
    fun `get live data sorted by PRIORITY, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.PRIORITY)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedByPriority)
    }

    @Test
    fun `get live data sorted by DATE, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.DATE)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedByDate)
    }

    @Test
    fun `get live data sorted by NAME, if equal to TasksConstants return true`() {

        mainViewModel.sortTasks(SortType.NAME)

        Assert.assertEquals(mainViewModel.tasks.value, TasksConstants.sortedByName)
    }


}