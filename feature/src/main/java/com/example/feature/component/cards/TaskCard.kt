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
import com.example.feature.screen.home.HomeScreen

@Composable
fun TaskCard(
    task: Task,
    state: HomeScreen.HomeState,
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
            // TODO
        },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CheckBox(task = task, state = state)
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
    state: HomeScreen.HomeState,
) {
    Checkbox(
        modifier =
            Modifier
                .scale(1.25f)
                .padding(8.dp),
        checked = task.isCompleted,
        onCheckedChange = {
            state.event(HomeScreen.HomeEvent.UpdateTask(task.copy(isCompleted = it)))
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
        state = HomeScreen.HomeState {},
    )
}
