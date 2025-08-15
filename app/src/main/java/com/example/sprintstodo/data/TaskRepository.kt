package com.example.sprintstodo.data


class TaskRepository(private val dao: TaskDao) {

    val allTasks = dao.getAllTasks()

    // Tasks
    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    // SubTasks
    suspend fun insertSubTask(subTask: SubTask) {
        dao.insertSubTask(subTask)
    }

    suspend fun deleteSubTask(subTask: SubTask) {
        dao.deleteSubTask(subTask)
    }

    fun getSubTasksForTask(taskId: Int) =
        dao.getSubTasksForTask(taskId)
}
