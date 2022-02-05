package com.wojbeg.todoapp.repository

import androidx.lifecycle.LiveData
import com.wojbeg.todoapp.db.TaskDao
import com.wojbeg.todoapp.db.TasksDatabase
import com.wojbeg.todoapp.model.Task

class ToDoRepository(
    private val db: TasksDatabase
): ToDoRepositoryInterface {

    private val tasksDao: TaskDao = db.getTaskDao()

    override suspend fun insert(task: Task){
      return tasksDao.insert(task)
    }

    override suspend fun update(task: Task): Int{
        return tasksDao.update(task)
    }

    override suspend fun deleteTask(task: Task) {
        return tasksDao.deleteTask(task)
    }

    override fun getSavedTasks(): LiveData<List<Task>> {
        return tasksDao.getSavedTasks()
    }

    override fun getSavedTasksByStatus(): LiveData<List<Task>> {
        return tasksDao.getSavedTasksByStatus()
    }

    override fun getSavedTasksByName(): LiveData<List<Task>> {
        return tasksDao.getSavedTasksByName()
    }

    override fun getSavedTasksByType(): LiveData<List<Task>> {
        return tasksDao.getSavedTasksByType()
    }

    override fun getSavedTasksByDate(): LiveData<List<Task>> {
        return tasksDao.getSavedTasksByDate()
    }

    override fun getSavedTasksByPriority(): LiveData<List<Task>> {
        return tasksDao.getSavedTasksByPriority()
    }

    override fun getNumberOfTasks(): LiveData<Int> {
        return tasksDao.getNumberOfTasks()
    }


}