package com.example.feature.component.cards.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.presentation.HomeStatus
import com.example.feature.screen.home.HomeScreen

@Composable
fun TaskInputCard(
    state: HomeScreen.HomeState
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                .padding(16.dp),
    ) {
        ElevatedCard(
            modifier =
                Modifier
                    .align(Alignment.Center),
            shape = RoundedCornerShape(4.dp),
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Input(label = "title", value = title, onValueChange = { title = it })
                Input(label = "description", value = description, lines = 10, onValueChange = { description = it })
                // Priority
                EnterButton(onClick = {
                    state.event(HomeScreen.HomeEvent.InsertTask(title = title, description = description))
                    state.event(HomeScreen.HomeEvent.UpdateHomeStatus(HomeStatus.DEFAULT))
                })
            }
        }
    }
}

@Composable
private fun Input(
    value: String,
    label: String,
    lines: Int = 1,
    onValueChange: (String) -> Unit,
) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        maxLines = lines,
        minLines = lines,
    )
}

@Composable
private fun EnterButton(onClick: () -> Unit) {
    ElevatedButton(
        shape = RoundedCornerShape(4.dp),
        colors =
            ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        onClick = onClick,
    ) {
        Text(text = "Enter")
    }
}

@Composable
@Preview
private fun TaskInputCardPreview() {
    TaskInputCard(
        state = HomeScreen.HomeState{}
    )
}
