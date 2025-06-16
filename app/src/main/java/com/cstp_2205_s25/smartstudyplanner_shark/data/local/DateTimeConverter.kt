package com.cstp_2205_s25.smartstudyplanner_shark.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    fun fromString(value: String): LocalDateTime =
        LocalDateTime.parse(value, formatter)

    @TypeConverter
    fun toString(date: LocalDateTime): String =
        date.format(formatter)
}
