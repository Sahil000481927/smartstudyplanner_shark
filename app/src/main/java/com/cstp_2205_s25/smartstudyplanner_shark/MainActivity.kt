package com.cstp_2205_s25.smartstudyplanner_shark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.data.repository.TaskRepository
import com.cstp_2205_s25.smartstudyplanner_shark.data.repository.TaskViewModelFactory
import com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.TaskEntryScreen
import com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.TaskListScreen
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import com.cstp_2205_s25.smartstudyplanner_shark.ui.theme.Smartstudyplanner_sharkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = SmartStudyPlannerApp.database
        val repository = TaskRepository(database.taskDao())
        val factory = TaskViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        // Insert placeholder tasks if database is empty
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = database.taskDao().getAllTasksSortedByDueDate()
            tasks.collect { list ->
                if (list.isEmpty()) {
                    val now = LocalDateTime.now()
                    val placeholderTasks = listOf(
                        StudyTask(title = "Math Homework", subject = "Math", description = "Complete exercises 1-10", dueDate = now.plusDays(1), priority = 2),
                        StudyTask(title = "Science Project", subject = "Science", description = "Prepare slides", dueDate = now.plusDays(3), priority = 3),
                        StudyTask(title = "Read History Chapter", subject = "History", description = "Read chapter 5", dueDate = now.plusDays(2), priority = 1)
                    )
                    database.taskDao().insertTasks(placeholderTasks)
                }
                // Only need to check once
                this.cancel()
            }
        }

        setContent {
            Smartstudyplanner_sharkTheme {
                AppNavigator(viewModel = viewModel)
            }
        }
    }
}


@Composable
fun AppNavigator(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        composable("task_list") {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("add_task") },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Task"
                        )
                    }
                }
            ) { paddingValues ->
                TaskListScreen(viewModel = viewModel, modifier = Modifier.padding(paddingValues))
            }
        }

        composable("add_task") {
            TaskEntryScreen(viewModel = viewModel)
        }
    }
}
