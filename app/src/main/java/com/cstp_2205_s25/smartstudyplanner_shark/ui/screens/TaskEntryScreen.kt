package com.cstp_2205_s25.smartstudyplanner_shark.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.cstp_2205_s25.smartstudyplanner_shark.viewmodel.TaskViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskEntryScreen(viewModel: TaskViewModel) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var dueDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var dueDateText by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("2") } // Default = Medium

    val priorities = listOf("1", "2", "3") // Low, Medium, High

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Add New Task", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
        )

        OutlinedTextField(
            value = dueDateText,
            onValueChange = {}, // Disable manual editing
            label = { Text("Due Date & Time") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val now = dueDateTime ?: LocalDateTime.now()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val pickedDate = now.withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val pickedDateTime = pickedDate.withHour(hour).withMinute(minute)
                                    dueDateTime = pickedDateTime
                                    dueDateText = pickedDateTime.format(dateFormatter)
                                },
                                now.hour, now.minute, true
                            ).show()
                        },
                        now.year, now.monthValue - 1, now.dayOfMonth
                    ).show()
                },
            enabled = false,
            readOnly = true
        )

        // Priority Selector
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Priority: ")
            Spacer(modifier = Modifier.width(8.dp))
            priorities.forEach { level ->
                val isSelected = priority == level
                AssistChip(
                    onClick = { priority = level },
                    label = { Text(level) },
                    colors = if (isSelected) AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ) else AssistChipDefaults.assistChipColors(),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Button(
            onClick = {
                if (title.isBlank() || subject.isBlank() || dueDateTime == null) {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addNewTask(
                        title = title,
                        subject = subject,
                        description = null,
                        dueDate = dueDateTime!!,
                        priority = priority.toInt()
                    )
                    Toast.makeText(context, "Task added!", Toast.LENGTH_SHORT).show()
                    title = ""; subject = ""; dueDateText = ""; dueDateTime = null; priority = "2"
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Task")
        }
    }
}
