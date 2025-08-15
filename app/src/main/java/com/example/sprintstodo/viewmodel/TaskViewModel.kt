package com.example.sprintstodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sprintstodo.data.Task
import com.example.sprintstodo.data.SubTask
import com.example.sprintstodo.data.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks = repository.allTasks

    fun addTask(title: String) {
        viewModelScope.launch { repository.insertTask(Task(title = title)) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun addSubTask(taskId: Int, title: String) {
        viewModelScope.launch {
            repository.insertSubTask(
                SubTask(parentTaskId = taskId, title = title)
            )
        }
    }

    fun deleteSubTask(subTask: SubTask) {
        viewModelScope.launch { repository.deleteSubTask(subTask) }
    }

    fun getSubTasks(taskId: Int) = repository.getSubTasksForTask(taskId)
}


