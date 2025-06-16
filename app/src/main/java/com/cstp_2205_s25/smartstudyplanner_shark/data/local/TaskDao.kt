package com.cstp_2205_s25.smartstudyplanner_shark.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM study_tasks ORDER BY dueDate ASC")
    fun getAllTasksSortedByDueDate(): Flow<List<StudyTask>>
}
