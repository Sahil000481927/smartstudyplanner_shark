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
fun TaskListScreen(viewModel: TaskViewModel) {
    val tasks = viewModel.taskList.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upcoming Tasks") }
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
                items(tasks) { task ->
                    TaskItemCard(task = task)
                }
            }
        }
    }
}
