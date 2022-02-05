package com.wojbeg.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.TasksConstants
import com.wojbeg.todoapp.utils.TasksConstants.tasks

class FakeTaskRepository: ToDoRepositoryInterface, FakeDB() {

    override suspend fun insert(task: Task) {
        if(!tasks.contains(task)){
            tasks.add(task)
        }
    }

    fun contains(task: Task): Boolean{
        return tasks.contains(task)
    }

    override suspend fun update(task: Task): Int {
        if(tasks.isNotEmpty()) {
            var index = -1
            for ((i, x) in tasks.withIndex()){
                if(x.id == task.id){
                    index = i
                }
            }

            if(index != -1) {
                tasks[index] = task
                return 1
            }

        }

        return 0
    }

    override suspend fun deleteTask(task: Task) {
        tasks.remove(task)
        sortedIdLiveData.postValue(tasks)
    }

    override fun getSavedTasks(): LiveData<List<Task>> {
        sortedIdLiveData.postValue(tasks)
        return sortedIdLiveData
    }

    override fun getSavedTasksByStatus(): LiveData<List<Task>> {
        return sortedStatusLiveData
    }

    override fun getSavedTasksByName(): LiveData<List<Task>> {
        return sortedNameLiveData
    }

    override fun getSavedTasksByType(): LiveData<List<Task>> {
        return sortedTypeLiveData
    }

    override fun getSavedTasksByDate(): LiveData<List<Task>> {
        return sortedDateLiveData
    }

    override fun getSavedTasksByPriority(): LiveData<List<Task>> {
        return sortedPriorityLiveData
    }

    override fun getNumberOfTasks(): LiveData<Int> {
        numOfTasksLiveData.postValue(tasks.size)
        return numOfTasksLiveData
    }
}