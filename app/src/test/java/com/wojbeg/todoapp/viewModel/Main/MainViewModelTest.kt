package com.wojbeg.todoapp.viewModel.Main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wojbeg.todoapp.repository.FakeTaskRepository
import com.wojbeg.todoapp.utils.MainCoroutineRule
import com.wojbeg.todoapp.utils.TasksConstants
import com.wojbeg.todoapp.utils.TasksConstants.NUMBER_OF_TASKS_FOR_TEST
import com.wojbeg.todoapp.ui.viewmodels.mainViewModel.MainViewModel
import com.wojbeg.todoapp.utils.SortType
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        mainViewModel = MainViewModel(FakeTaskRepository())
    }

    @Test
    fun `get number of tasks, if 12 then ture`(){
       Assert.assertEquals(mainViewModel.getNumberOfTasks().value, NUMBER_OF_TASKS_FOR_TEST)
    }

    @Test
    fun `insert task test, get new number of tasks, if 13 then true`() = runBlockingTest {

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(mainViewModel.getNumberOfTasks().value, NUMBER_OF_TASKS_FOR_TEST+1)

        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)
    }

    @Test
    fun `delete task test, get number of tasks, if is 12 return true`() = runBlockingTest {

        mainViewModel.saveTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)
        mainViewModel.deleteTask(TasksConstants.TEST_TASK_TO_ADD_AND_DELETE)

        Assert.assertEquals(mainViewModel.getNumberOfTasks().value, NUMBER_OF_TASKS_FOR_TEST)
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