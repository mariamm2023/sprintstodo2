package com.example.sprintstodo



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sprintstodo.ui.theme.SprintstodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SprintstodoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoScreen()
                }
            }
        }
    }
}

@Composable
fun ToDoScreen() {
    var taskText by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // إدخال التاسك
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = taskText,
                onValueChange = { taskText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("اكتب التاسك هنا") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (taskText.isNotBlank()) {
                        taskList = taskList + taskText
                        taskText = ""
                    }
                }
            ) {
                Text("إضافة")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // عرض قائمة التاسكات
        LazyColumn {
            items(taskList) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = task,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
