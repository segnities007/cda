package com.example.feature.component.buttons.multi_fabs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.presentation.FloatingActionButtonItem
import com.example.domain.presentation.HomeStatus
import com.example.feature.R
import com.example.feature.screen.home.HomeScreen

@Composable
fun MultiFloatingActionButton(
    state: HomeScreen.HomeState,
    items: List<FloatingActionButtonItem>,
) {
    var currentState by remember { mutableStateOf(MultiFloatingActionButtonState.COLLAPSED) }
    val stateTransition = updateTransition(targetState = currentState, label = "")
    val stateChange: () -> Unit = {
        currentState =
            when {
                currentState == MultiFloatingActionButtonState.EXPANDED -> {
                    MultiFloatingActionButtonState.COLLAPSED
                }
                currentState == MultiFloatingActionButtonState.COLLAPSED && state.homeStatus != HomeStatus.DEFAULT -> {
                    state.event(HomeScreen.HomeEvent.UpdateHomeStatus(HomeStatus.DEFAULT))
                    MultiFloatingActionButtonState.COLLAPSED
                }
                else -> {
                    MultiFloatingActionButtonState.EXPANDED
                }
            }
    }
    val rotation: Float by stateTransition.animateFloat(
        transitionSpec = {
            if (targetState == MultiFloatingActionButtonState.EXPANDED) {
                spring(stiffness = Spring.StiffnessLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "",
    ) { state ->
        if (state == MultiFloatingActionButtonState.EXPANDED) 45f else 0f
    }
    val isEnable = currentState == MultiFloatingActionButtonState.EXPANDED

    BackHandler(isEnable) {
        currentState = MultiFloatingActionButtonState.COLLAPSED
    }

    val close = {
        currentState = MultiFloatingActionButtonState.COLLAPSED
    }

    val modifier =
        if (currentState == MultiFloatingActionButtonState.EXPANDED) {
            Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    close()
                }
        } else {
            Modifier.fillMaxSize()
        }

    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(400.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            val drawCircleColor = MaterialTheme.colorScheme.secondary
            if (currentState == MultiFloatingActionButtonState.EXPANDED) {
                Canvas(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                scaleX = 2.4f
                                scaleY = 2.4f
                            },
                ) {
                    translate(150f, top = 300f) {
                        scale(5f) {}
                        drawCircle(color = drawCircleColor, alpha = 0.8f, radius = 200.dp.toPx())
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
            ) {
                items.forEach { item ->
                    SmallFloatingActionButtonRow(
                        item = item,
                        stateTransition = stateTransition,
                        close = close,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        stateChange()
                    },
                ) {
                    when (state.homeStatus) {
                        HomeStatus.DEFAULT -> {
                            Icon(
                                painter = painterResource(R.drawable.baseline_settings_24),
                                contentDescription = "home",
                                tint = Color.White,
                            )
                        }
                        else -> {
                            Icon(
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.rotate(rotation),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SmallFloatingActionButtonRow(
    item: FloatingActionButtonItem,
    stateTransition: Transition<MultiFloatingActionButtonState>,
    close: () -> Unit,
) {
    val alpha: Float by stateTransition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "",
    ) { state ->
        if (state == MultiFloatingActionButtonState.EXPANDED) 1f else 0f
    }
    val scale: Float by stateTransition.animateFloat(
        label = "",
    ) { state ->
        if (state == MultiFloatingActionButtonState.EXPANDED) 1.0f else 0f
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .alpha(animateFloatAsState((alpha), label = "").value)
                .scale(animateFloatAsState(targetValue = scale, label = "").value),
    ) {
        Text(
            text = item.label,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
        )
        SmallFloatingActionButton(
            shape = CircleShape,
            modifier =
                Modifier.padding(4.dp),
            onClick = {
                item.onClick(item.homeStatus)
                close()
            },
            contentColor = Color.White,
        ) {
            Icon(
                painter = item.icon,
                contentDescription = item.label,
            )
        }
    }
}

@Composable
@Preview
private fun MultiFloatingActionButtonPreview() {
    val items =
        listOf(
            FloatingActionButtonItem(
                icon = painterResource(R.drawable.baseline_home_24),
                label = "home",
                homeStatus = HomeStatus.DEFAULT,
                onClick = {},
            ),
            FloatingActionButtonItem(
                icon = painterResource(R.drawable.baseline_settings_24),
                label = "settings",
                homeStatus = HomeStatus.DEFAULT,
                onClick = {},
            ),
        )
    MultiFloatingActionButton(
        items = items,
        state = HomeScreen.HomeState {},
    )
}
