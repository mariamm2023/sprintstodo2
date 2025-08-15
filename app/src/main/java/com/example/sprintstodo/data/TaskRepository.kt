package com.example.sprintstodo.data

import com.example.sprintstodo.TaskDao


class TaskRepository(private val dao: TaskDao) {

    val allTasks = dao.getAllTasks()

    suspend fun insert(task: Task) {
        dao.insertTask(task)
    }

    suspend fun delete(task: Task) {
        dao.deleteTask(task)
    }
}
