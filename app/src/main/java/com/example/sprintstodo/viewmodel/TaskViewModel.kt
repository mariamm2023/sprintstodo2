package com.example.sprintstodo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprintstodo.data.Task
import com.example.sprintstodo.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks = repository.allTasks

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.insert(Task(title = title))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}
