package com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel

import android.os.Parcel
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.repository.ToDoRepository
import com.wojbeg.todoapp.repository.ToDoRepositoryInterface
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.dataBinding.DataBindingListener
import com.wojbeg.todoapp.utils.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.OffsetDateTime

class CreateTaskViewModel(
    private val repository: ToDoRepositoryInterface
) : ViewModel() {

    var id: Int? = null
    var title :String = ""
    var status: Boolean = false
    var day :String? = null
    var month :String? = null
    var year :String? = null

    //Task
    private var task: Task? = null

    var date: OffsetDateTime

    private var type: String? = null
    private var priority: Priority = Priority.Normal

    //Listener
    var bindingListener: DataBindingListener? = null


    init {
        date = OffsetDateTime.now()
        day = date.dayOfMonth.toString()
        month = date.monthValue.toString()
        year = date.year.toString()

    }

    fun onSelectItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long){

        type = parent?.selectedItem.toString()
    }

    fun onSelectItemPriority(parent: AdapterView<*>?, view: View?, position: Int, id: Long){

        priority = Priority.valueOf(parent?.selectedItem.toString())
    }


    fun onSaveBtnClick(): Boolean{

        if(validateData()){
            createTask()

            saveTask()

            return true
        }

        return false
    }

    private fun createTask(){
        task = Task(id, status, title, type, date, priority)
    }

    fun onCloseBtnClick(view: View){
        bindingListener?.onCloseBtn()
    }

    fun onChangeDateBtn(view: View){
        bindingListener?.onChangeDateBtn()
    }

    private fun validateData(): Boolean {
        return title.trim().isNotBlank()
    }

    fun deleteTask() = viewModelScope.launch {
        task?.let {
            repository.deleteTask(it)
        }
    }

    fun saveTask() = viewModelScope.launch {
        task?.let {
            repository.insert(it)
        }
    }

    fun recoverTask(recoveredTask: Task){
        task = recoveredTask
        id = task!!.id
        status = task!!.status!!
        task!!.date?.let { changeDate(it) }
        title = task!!.name.toString()
        type = task!!.type
        priority = task!!.priority!!
    }


    fun changeDate(dateTime: OffsetDateTime){
        date = dateTime
        day = date.dayOfMonth.toString()
        month = date.monthValue.toString()
        year = date.year.toString()
    }

}