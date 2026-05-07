package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.Participant
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SweepableCardExpense(
    expense: Expense,
    participants: List<Participant>,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val density = LocalDensity.current
    val anchorSize = with(density) { 130.dp.toPx() }
    val scope = rememberCoroutineScope()

    val anchorState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Settled,
            anchors = DraggableAnchors {
                DragValue.Settled at 0f
                DragValue.Open at -anchorSize
            },

            )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            SwipeMenuBackground(
                onEdit = { scope.launch { anchorState.animateTo(DragValue.Settled) }; onEdit() },
                onDelete = { scope.launch { anchorState.animateTo(DragValue.Settled) }; onDelete() }
            )
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = anchorState.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = anchorState,
                    orientation = Orientation.Horizontal
                )
        ) {
            CardExpense(
                expense = expense,
                name = participants.find { it.id == expense.payerId }?.name
                    ?: ""
            )
        }
    }
}