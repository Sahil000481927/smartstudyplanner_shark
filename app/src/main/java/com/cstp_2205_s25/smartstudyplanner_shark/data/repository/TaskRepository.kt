package com.cstp_2205_s25.smartstudyplanner_shark.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.TaskDao
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasksChronological: Flow<List<StudyTask>> =
        taskDao.getAllTasksSortedByDueDate()

    suspend fun insertTask(task: StudyTask) = taskDao.insertTask(task)
    suspend fun insertTasks(tasks: List<StudyTask>) = taskDao.insertTasks(tasks)
    suspend fun updateTask(task: StudyTask) = taskDao.updateTask(task)
}

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
