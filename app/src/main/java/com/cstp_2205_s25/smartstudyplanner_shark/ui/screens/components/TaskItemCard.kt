package com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import java.time.format.DateTimeFormatter

@Composable
fun TaskItemCard(task: StudyTask) {
    val dueDateFormatted = task.dueDate.format(DateTimeFormatter.ofPattern("EEE, MMM d"))

    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Subject: ${task.subject}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Due: $dueDateFormatted",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
