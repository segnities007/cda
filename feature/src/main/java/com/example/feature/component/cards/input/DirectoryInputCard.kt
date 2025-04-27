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

@Composable
fun DirectoryInputCard(
    onCreate: (title: String) -> Unit,
    onUpdateHomeStatus: (HomeStatus) -> Unit,
) {
    var title by remember { mutableStateOf("") }

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
                Input(value = title, onValueChange = { title = it })
                EnterButton(onClick = {
                    onCreate(title)
                    onUpdateHomeStatus(HomeStatus.DEFAULT)
                })
            }
        }
    }
}

@Composable
private fun Input(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        label = { Text(text = "title") },
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        minLines = 1,
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
private fun DirectoryInputCardPreview() {
    DirectoryInputCard(
        onCreate = { _ -> },
        onUpdateHomeStatus = {},
    )
}
