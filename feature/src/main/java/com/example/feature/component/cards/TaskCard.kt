package com.example.feature.component.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Task
import com.example.domain.presentation.Route
import com.example.domain.presentation.Route.TodoDetail
import com.example.feature.screen.home.HomeAction

@Composable
fun TaskCard(
    task: Task,
    onNavigate: (Route) -> Unit,
    onHomeAction: (HomeAction) -> Unit,
) {
    val cardColor =
        when (task.isCompleted) {
            true -> MaterialTheme.colorScheme.secondaryContainer
            false -> MaterialTheme.colorScheme.surfaceVariant
        }

    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = cardColor,
            ),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            onNavigate(TodoDetail(task.id))
        },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CheckBox(task = task, onHomeAction = onHomeAction)
            Text(
                text = task.title,
                modifier = Modifier.scale(1.25f),
            )
        }
    }
}

@Composable
private fun CheckBox(
    task: Task,
    onHomeAction: (HomeAction) -> Unit,
) {
    Checkbox(
        modifier =
            Modifier
                .scale(1.25f)
                .padding(8.dp),
        checked = task.isCompleted,
        onCheckedChange = {
            onHomeAction(
                HomeAction.UpdateTask(
                    task = task.copy(isCompleted = it),
                ),
            )
        },
    )
}

@Composable
@Preview
private fun TaskCardPreview() {
    TaskCard(
        task =
            Task(
                title = "aaa",
                directoryId = 0,
            ),
        onHomeAction = {},
        onNavigate = {},
    )
}
