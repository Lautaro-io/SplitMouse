package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.formatFecha
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.ui.toArgentineCurrency

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardEvent(
    event: Event,
    menuExpanded: Boolean,
    onLongPress: (Long) -> Unit,
    onEventClick: () -> Unit,
    onDotsClick: () -> Unit,
    onDismissMenu: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { onEventClick() },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPress(event.id)
                }
            ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.name,
                    fontSize = 26.sp,
                    color = BlackPurple,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Box {
                    IconButton(onClick = onDotsClick) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    EventActionsMenu(
                        expanded = menuExpanded,
                        onDismiss = onDismissMenu,
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                }
            }

            if (event.description.isNotEmpty()) {
                Text(
                    text = event.description,
                    color = Color.Gray,
                    fontSize = 22.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(Pink40),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formatFecha(event.date),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Start,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("GASTADO")
                    Text(
                        text = event.totalAmount.toArgentineCurrency(),
                        fontWeight = FontWeight.ExtraBold,
                        color = VioletaFuerte,
                        fontSize = 22.sp,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}