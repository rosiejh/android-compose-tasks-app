package com.example.tasks_app.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasks_app.model.Task
import java.util.Locale

// https://medium.com/@acceldia/jetpack-compose-creating-expandable-cards-with-content-9ea1eae09efe
@Composable
fun MiniTaskCard(
    task: Task,
    onTaskClick: () -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onTaskClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = task.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            modifier = Modifier.padding(8.dp, 8.dp, 8.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = task.priority,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.tertiary
        )

        if (expanded) {
            Text(
                text = task.description,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}