package com.example.feature.screen.tododetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.component.bars.TopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent

@CircuitInject(TodoDetailScreen::class, SingletonComponent::class)
@Composable
fun TodoDetail(
    state: TodoDetailScreen.TodoDetailState,
    modifier: Modifier,
) {
    TodoDetailUi(
        state = state,
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = state.task.title)
            Card(
                modifier =
                    Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                colors =
                    CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                ) {
                    Spacer(Modifier.weight(1f))
                    Text(text = state.task.description)
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TodoDetailUi(
    state: TodoDetailScreen.TodoDetailState,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        topBar = { TopBar { state.event(TodoDetailScreen.TodoDetailEvent.BackNavigate) } },
    ) {
        content(Modifier.padding(it))
    }
}

@Composable
@Preview
private fun TodoDetailPreview() {
    TodoDetail(
        state = TodoDetailScreen.TodoDetailState {},
        modifier = Modifier,
    )
}
