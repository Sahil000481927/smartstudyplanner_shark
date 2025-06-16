package com.cstp_2205_s25.smartstudyplanner_shark.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime

@Entity(tableName = "study_tasks")
@TypeConverters(DateTimeConverter::class)
data class StudyTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subject: String,
    val description: String? = null,
    val dueDate: LocalDateTime,
    val priority: Int, // 1 = Low, 2 = Medium, 3 = High
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
