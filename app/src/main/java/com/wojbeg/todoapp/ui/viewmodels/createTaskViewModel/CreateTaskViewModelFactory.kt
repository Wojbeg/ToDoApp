package com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wojbeg.todoapp.repository.ToDoRepository

@Suppress("UNCHECKED_CAST")
class CreateTaskViewModelFactory(
    private val repository: ToDoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateTaskViewModel(repository) as T
    }
}