package com.example.feature.component.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TabBar(
    labels: List<String>,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    when {
        labels.isEmpty() -> NoTabBarRow()
        else -> ScrollTabRow(labels, tabIndex, onTabSelected)
    }
}

@Composable
private fun NoTabBarRow() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(112.dp)
                .background(color = NavigationBarDefaults.containerColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text("No Directory")
    }
}

@Composable
private fun ScrollTabRow(
    labels: List<String>,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Spacer(Modifier.height(64.dp))
        ScrollableTabRow(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.contentColorFor(NavigationBarDefaults.containerColor),
            edgePadding = 0.dp,
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            },
        ) {
            labels.forEachIndexed { index, label ->
                Tab(
                    text = { Text(label) },
                    selected = tabIndex == index,
                    onClick = { onTabSelected(index) },
                )
            }
        }
    }
}

@Composable
@Preview
fun TabBarPreview() {
    val labels = listOf("aaa", "bbb", "ccc", "aaa", "bbb", "ccc")
    TabBar(labels, tabIndex = 0, onTabSelected = {})
}
