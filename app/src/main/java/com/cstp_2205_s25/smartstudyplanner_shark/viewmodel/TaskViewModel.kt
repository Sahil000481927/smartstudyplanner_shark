package com.cstp_2205_s25.smartstudyplanner_shark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val taskList: StateFlow<List<StudyTask>> = repository
        .allTasksChronological
        .map { it.sortedBy { task -> task.dueDate } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNewTask(
        title: String,
        subject: String,
        description: String? = null,
        dueDate: LocalDateTime,
        priority: Int
    ) {
        val task = StudyTask(
            title = title,
            subject = subject,
            description = description,
            dueDate = dueDate,
            priority = priority,
            isCompleted = false,
            createdAt = LocalDateTime.now()
        )

        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun toggleTaskCompletion(task: StudyTask) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
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
