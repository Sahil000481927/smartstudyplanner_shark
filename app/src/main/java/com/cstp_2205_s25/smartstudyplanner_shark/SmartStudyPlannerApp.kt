package com.cstp_2205_s25.smartstudyplanner_shark

import android.app.Application
import androidx.room.Room
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.AppDatabase

class SmartStudyPlannerApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "study_tasks.db"
            ).fallbackToDestructiveMigration(false)
            .build()
    }
}
