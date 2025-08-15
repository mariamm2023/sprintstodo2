package com.example.sprintstodo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Tasks
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    // SubTasks
    @Query("SELECT * FROM sub_tasks WHERE subTaskId = :taskId")
    fun getSubTasksForTask(taskId: Int): Flow<List<SubTask>>

    @Insert
    suspend fun insertSubTask(subTask: SubTask)

    @Delete
    suspend fun deleteSubTask(subTask: SubTask)
}
