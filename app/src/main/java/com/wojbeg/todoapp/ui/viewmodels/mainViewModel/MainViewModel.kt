package com.wojbeg.todoapp.ui.viewmodels.mainViewModel

import androidx.lifecycle.*
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.repository.ToDoRepository
import com.wojbeg.todoapp.repository.ToDoRepositoryInterface
import com.wojbeg.todoapp.utils.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ToDoRepositoryInterface
) : ViewModel() {

    //return liveData<List<Task>>
    private val tasksSortedById = repository.getSavedTasks()
    private val tasksSortedByStatus = repository.getSavedTasksByStatus()
    private val tasksSortedByName = repository.getSavedTasksByName()
    private val tasksSortedByType = repository.getSavedTasksByType()
    private val tasksSortedByDate = repository.getSavedTasksByDate()
    private val tasksSortedByPriority = repository.getSavedTasksByPriority()

    val tasks = MediatorLiveData<List<Task>>()

    var sortType = SortType.ID

    init {
        tasks.addSource(tasksSortedById){ result ->
            if(sortType == SortType.ID){
                result?.let {
                    tasks.value = it
                }
            }
        }

        tasks.addSource(tasksSortedByStatus){ result ->
            if(sortType == SortType.STATUS){
                result?.let {
                    tasks.value = it
                }
            }
        }

        tasks.addSource(tasksSortedByName){ result ->
            if(sortType == SortType.NAME){
                result?.let {
                    tasks.value = it
                }
            }
        }

        tasks.addSource(tasksSortedByType){ result ->
            if(sortType == SortType.TYPE){
                result?.let {
                    tasks.value = it
                }
            }
        }

        tasks.addSource(tasksSortedByDate){ result ->
            if(sortType == SortType.DATE){
                result?.let {
                    tasks.value = it
                }
            }
        }

        tasks.addSource(tasksSortedByPriority){ result ->
            if(sortType == SortType.PRIORITY){
                result?.let {
                    tasks.value = it
                }
            }
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun getNumberOfTasks() = repository.getNumberOfTasks()

    fun deleteTask(task: Task) = viewModelScope.launch {
        task?.let {
            repository.deleteTask(it) }
    }

    fun saveTask(task: Task) = viewModelScope.launch {
        task?.let {
            repository.insert(it)
        }
    }

    fun sortTasks(sortType: SortType) = when(sortType){
        SortType.ID -> tasksSortedById.value?.let { tasks.value = it }
        SortType.STATUS -> tasksSortedByStatus.value?.let { tasks.value = it }
        SortType.NAME -> tasksSortedByName.value?.let { tasks.value = it }
        SortType.TYPE -> tasksSortedByType.value?.let { tasks.value = it }
        SortType.DATE -> tasksSortedByDate.value?.let { tasks.value = it }
        SortType.PRIORITY -> tasksSortedByPriority.value?.let { tasks.value = it }
    }.also {
        this.sortType = sortType
    }

}