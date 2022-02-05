package com.wojbeg.todoapp.viewModel.Create

import android.widget.AdapterView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wojbeg.todoapp.repository.FakeTaskRepository
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.CreateTaskViewModel
import com.wojbeg.todoapp.utils.MainCoroutineRule
import com.wojbeg.todoapp.utils.Priority
import com.wojbeg.todoapp.utils.TasksConstants
import com.wojbeg.todoapp.utils.TasksConstants.TEST_TASK_EMPTY_TITLE_ERROR
import com.wojbeg.todoapp.utils.TasksConstants.TEST_TASK_TO_ADD_AND_DELETE
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CreateTaskViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var createViewModel: CreateTaskViewModel
    private val repository = FakeTaskRepository()
    private val parentType: AdapterView<*> = Mockito.mock(AdapterView::class.java)
    private val parentPriority: AdapterView<*> = Mockito.mock(AdapterView::class.java)

    @Before
    fun setUp(){
        Mockito.doReturn("Education").`when`(parentType).selectedItem
        Mockito.doReturn(Priority.High).`when`(parentPriority).selectedItem

        createViewModel = CreateTaskViewModel(repository)

    }

    @Test
    fun `date changer test, compare date, day, month and year`(){

        createViewModel.changeDate(TasksConstants.date)

        Assert.assertEquals(TasksConstants.date, createViewModel.date)

        Assert.assertEquals(TasksConstants.date.dayOfMonth, createViewModel.date.dayOfMonth)
        Assert.assertEquals(TasksConstants.date.month, createViewModel.date.month)
        Assert.assertEquals(TasksConstants.date.year, createViewModel.date.year)
    }

    @Test
    fun `insert task test, get new number of tasks, if 13 then true`() = runBlockingTest {

        createViewModel.recoverTask(TEST_TASK_TO_ADD_AND_DELETE)
        createViewModel.saveTask()
        Assert.assertEquals(TasksConstants.NUMBER_OF_TASKS_FOR_TEST + 1, repository.getNumberOfTasks().value)

        createViewModel.deleteTask()
    }

    @Test
    fun `delete task test, get number of tasks, if is 12 return true`() = runBlockingTest {

        createViewModel.recoverTask(TEST_TASK_TO_ADD_AND_DELETE)
        createViewModel.saveTask()
        createViewModel.deleteTask()

        Assert.assertEquals(TasksConstants.NUMBER_OF_TASKS_FOR_TEST, repository.getNumberOfTasks().value)
    }

    @Test
    fun `validate task test, empty name, return false`(){
        createViewModel.recoverTask(TEST_TASK_EMPTY_TITLE_ERROR)
        Assert.assertEquals(false, createViewModel.onSaveBtnClick())
    }

    @Test
    fun `validate task test, correct, return true`(){
        createViewModel.recoverTask(TEST_TASK_TO_ADD_AND_DELETE)
        Assert.assertEquals(true, createViewModel.onSaveBtnClick())
    }

    @Test
    fun `change priority, High(3) if tasks contains, return true`(){
        val task = TEST_TASK_TO_ADD_AND_DELETE
        createViewModel.recoverTask(task)
        createViewModel.onSelectItemPriority(parentPriority, null, 0, 0L)
        createViewModel.saveTask()
        task.priority = Priority.High
        Assert.assertEquals(true, repository.getSavedTasks().value?.contains(task))
    }

    @Test
    fun `change type, "Education" if tasks contains, return true`(){
        val task = TEST_TASK_TO_ADD_AND_DELETE
        createViewModel.recoverTask(task)
        createViewModel.onSelectItem(parentType, null, 0, 0L)
        createViewModel.saveTask()
        task.type = "Education"
        Assert.assertEquals(true, repository.getSavedTasks().value?.contains(task))
    }
}