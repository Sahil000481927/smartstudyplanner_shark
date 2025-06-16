package com.cstp_2205_s25.smartstudyplanner_shark.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM study_tasks ORDER BY dueDate ASC")
    fun getAllTasksSortedByDueDate(): Flow<List<StudyTask>>

    @Insert
    suspend fun insertTask(task: StudyTask)

    @Insert
    suspend fun insertTasks(tasks: List<StudyTask>)

    @Update
    suspend fun updateTask(task: StudyTask)
}
