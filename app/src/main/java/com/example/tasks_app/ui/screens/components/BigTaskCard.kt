package com.example.tasks_app.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tasks_app.model.Task
import java.util.Locale

@Composable
fun BigTaskCard(
    task: Task,
    onTaskClick: () -> Unit,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onTaskClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Text(
            text = task.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = task.priority,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = task.description,
            modifier = Modifier.padding(8.dp),
        )
    }
}