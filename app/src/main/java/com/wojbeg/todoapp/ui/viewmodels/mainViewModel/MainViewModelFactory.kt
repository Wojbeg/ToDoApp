package com.wojbeg.todoapp.ui.viewmodels.mainViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wojbeg.todoapp.repository.ToDoRepository


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (
    private val repository: ToDoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}