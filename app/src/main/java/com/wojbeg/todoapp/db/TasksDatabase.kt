package com.wojbeg.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wojbeg.todoapp.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TasksDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object{
        @Volatile
        private var instance: TasksDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TasksDatabase::class.java,
                "tasksDatabase"
            ).build()

    }

}