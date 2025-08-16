package com.example.sprintstodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sprintstodo.data.Task
import com.example.sprintstodo.data.TaskDatabase
import com.example.sprintstodo.data.TaskRepository
import com.example.sprintstodo.ui.theme.SprintstodoTheme
import com.example.sprintstodo.viewmodel.TaskViewModel
import com.example.sprintstodo.viewmodel.TaskViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SprintstodoTheme {
                val repository = TaskRepository(TaskDatabase.getDatabase(this).taskDao())
                val viewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(repository)
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun ToDoScreen(viewModel: TaskViewModel) {
    var taskText by remember { mutableStateOf("") }
    val taskList by viewModel.tasks.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = taskText,
                onValueChange = { taskText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("add your project") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (taskText.isNotBlank()) {
                        viewModel.addTask(taskText)
                        taskText = ""
                    }
                }
            ) {
                Text("submit")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(taskList) { task ->
                TaskItem(task, viewModel)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, viewModel: TaskViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var subTaskTitle by remember { mutableStateOf("") }
    val subTasks by viewModel.getSubTasks(task.taskId).collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = { viewModel.deleteTask(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task"
                )
            }
        }

        Button(onClick = { showDialog = true }) {
            Text("Add SubTask")
        }


        subTasks.forEach { subTask ->
            Text(" - ${subTask.title}")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add SubTask") },
                text = {
                    OutlinedTextField(
                        value = subTaskTitle,
                        onValueChange = { subTaskTitle = it },
                        label = { Text("SubTask Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (subTaskTitle.isNotBlank()) {
                                viewModel.addSubTask(task.taskId, subTaskTitle)
                                subTaskTitle = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            subTaskTitle = ""
                            showDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
