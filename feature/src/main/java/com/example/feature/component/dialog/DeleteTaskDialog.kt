package com.example.feature.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.DirectoryWithTasks
import com.example.domain.presentation.HomeStatus
import com.example.feature.screen.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTaskDialog(
    state: HomeScreen.HomeState,
) {
    BasicAlertDialog(
        modifier =
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.background),
        onDismissRequest = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(HomeStatus.DEFAULT)) },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(0.dp))
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = Icons.Outlined.Delete.toString())
            Text(
                text = "チェック済みのTodoを\n削除しますか？",
                textAlign = TextAlign.Center,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = { state.event(HomeScreen.HomeEvent.UpdateHomeStatus(HomeStatus.DEFAULT)) },
                ) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        state.event(HomeScreen.HomeEvent.DeleteTask(state.directories[state.selectedDirectoryIndex]))
                        state.event(HomeScreen.HomeEvent.UpdateHomeStatus(HomeStatus.DEFAULT))
                    },
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
@Preview
private fun DeleteTaskDialogPreview() {
    DeleteTaskDialog(
        state = HomeScreen.HomeState{}
    )
}
