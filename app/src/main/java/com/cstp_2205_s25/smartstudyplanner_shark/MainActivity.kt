package com.cstp_2205_s25.smartstudyplanner_shark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.data.repository.TaskRepository
import com.cstp_2205_s25.smartstudyplanner_shark.data.repository.TaskViewModelFactory
import com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.TaskListScreen
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import com.example.compose.Smartstudyplanner_sharkTheme
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
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
