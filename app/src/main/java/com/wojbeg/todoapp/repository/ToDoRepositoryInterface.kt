package com.wojbeg.todoapp.repository

import androidx.lifecycle.LiveData
import com.wojbeg.todoapp.model.Task

interface ToDoRepositoryInterface {

    suspend fun insert(task: Task)

    suspend fun update(task: Task): Int

    suspend fun deleteTask(task: Task)

    fun getSavedTasks(): LiveData<List<Task>>

    fun getSavedTasksByStatus(): LiveData<List<Task>>

    fun getSavedTasksByName(): LiveData<List<Task>>

    fun getSavedTasksByType(): LiveData<List<Task>>

    fun getSavedTasksByDate(): LiveData<List<Task>>

    fun getSavedTasksByPriority(): LiveData<List<Task>>

    fun getNumberOfTasks(): LiveData<Int>

    /*
    var status: Boolean?,
    var name: String?,
    var type: String?,
    var date: OffsetDateTime?,
    var priority: Priority?
     */


}