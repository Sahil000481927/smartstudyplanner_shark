package com.cstp_2205_s25.smartstudyplanner_shark.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.components.TaskItemCard
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TaskViewModel, modifier: Modifier) {
    val tasks = viewModel.taskList.collectAsState().value

    val incompleteTasks = tasks.filter { !it.isCompleted }
    val completedTasks = tasks.filter { it.isCompleted }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("All Tasks") }
            )
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text("No tasks available.")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(padding)
            ) {
                if (incompleteTasks.isNotEmpty()) {
                    item {
                        Text(
                            text = "Upcoming Tasks",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(incompleteTasks) { task ->
                        TaskItemCard(task = task, viewModel = viewModel)
                    }
                }
                if (completedTasks.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Completed Tasks",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(completedTasks) { task ->
                        TaskItemCard(task = task, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
