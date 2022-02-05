package com.wojbeg.todoapp

import android.app.Application
import com.wojbeg.todoapp.adapters.toDoListAdapter
import com.wojbeg.todoapp.db.TasksDatabase
import com.wojbeg.todoapp.repository.ToDoRepository
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.CreateTaskViewModelFactory
import com.wojbeg.todoapp.ui.viewmodels.mainViewModel.MainViewModelFactory
import com.wojbeg.todoapp.utils.Constants
import com.wojbeg.todoapp.utils.Constants.ADAPTER_TAG

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ToDoApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ToDoApplication))

        bind() from singleton { TasksDatabase(instance()) }
        bind() from singleton { ToDoRepository(instance()) }
        bind(ADAPTER_TAG) from singleton { toDoListAdapter() }

        bind() from provider { CreateTaskViewModelFactory(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }

}