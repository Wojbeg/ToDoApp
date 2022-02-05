package com.wojbeg.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.Constants

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    fun update(task: Task): Int

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME}")
    fun getSavedTasks(): LiveData<List<Task>>

    @Query("SELECT COUNT(*) FROM ${Constants.TASKS_TABLE_NAME}")
    fun getNumberOfTasks(): LiveData<Int>

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} ORDER BY status ASC")
    fun getSavedTasksByStatus(): LiveData<List<Task>>

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} ORDER BY UPPER(name) ASC")
    fun getSavedTasksByName(): LiveData<List<Task>>

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} ORDER BY UPPER(type) ASC")
    fun getSavedTasksByType(): LiveData<List<Task>>

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} ORDER BY date ASC")
    fun getSavedTasksByDate(): LiveData<List<Task>>

    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} ORDER BY priority ASC")
    fun getSavedTasksByPriority(): LiveData<List<Task>>


}