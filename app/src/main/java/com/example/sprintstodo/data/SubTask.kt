package com.example.sprintstodo.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sub_tasks",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["taskId"],
            childColumns = ["parentTaskId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    val subTaskId: Int = 0,
    val parentTaskId: Int, // ID بتاع الـ Task الأب
    val title: String,
    val isDone: Boolean = false
)
