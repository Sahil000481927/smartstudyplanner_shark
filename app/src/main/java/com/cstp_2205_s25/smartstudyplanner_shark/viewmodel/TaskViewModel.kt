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

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val taskList: StateFlow<List<StudyTask>> = repository
        .allTasksChronological
        .map { it.sortedBy { task -> task.dueDate } } // redundant if DAO already sorts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
