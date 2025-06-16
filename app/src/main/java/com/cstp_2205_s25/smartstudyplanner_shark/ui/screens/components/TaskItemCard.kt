package com.cstp_2205_s25.smartstudyplanner_shark.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cstp_2205_s25.smartstudyplanner_shark.data.local.StudyTask
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import java.time.format.DateTimeFormatter

@Composable
fun TaskItemCard(
    task: StudyTask,
    viewModel: TaskViewModel
) {
    Surface(
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Subject: ${task.subject}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Due: ${task.dueDate.format(DateTimeFormatter.ofPattern("EEE, MMM d"))}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    viewModel.toggleTaskCompletion(task.copy(isCompleted = it))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
