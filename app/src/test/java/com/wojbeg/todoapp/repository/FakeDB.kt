package com.wojbeg.todoapp.repository

import androidx.lifecycle.MutableLiveData
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.TasksConstants

open class FakeDB {

    protected var tasks: MutableList<Task> = TasksConstants.tasks

    private var sortedById: List<Task> = tasks.sortedBy { it.id }
    private var sortedByStatus: List<Task> = tasks.sortedBy { it.status }
    private var sortedByName: List<Task> = tasks.sortedBy { it.name }
    private var sortedByDate: List<Task> = tasks.sortedBy { it.date }
    private var sortedByPriority: List<Task> = tasks.sortedBy { it.priority }
    private var sortedByType: List<Task> = tasks.sortedBy { it.type }

    protected var sortedIdLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedById)
    protected var sortedStatusLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedByStatus)
    protected var sortedNameLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedByName)
    protected var sortedDateLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedByDate)
    protected var sortedPriorityLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedByPriority)
    protected var sortedTypeLiveData: MutableLiveData<List<Task>> = MutableLiveData(sortedByType)

    protected var numOfTasksLiveData: MutableLiveData<Int> = MutableLiveData(tasks.size)


}