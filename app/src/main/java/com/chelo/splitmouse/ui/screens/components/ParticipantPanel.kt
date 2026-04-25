package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.InputChipDefaults.inputChipColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Composable
fun ParticipantPanel(
    participants: List<Participant>,
    onAddParticipantClick: () -> Unit = {},
    onLongPress: (Long) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "Participantes",
            color = VioletaFuerte,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )


        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                InputChip(
                    selected = false,
                    onClick = onAddParticipantClick,
                    label = { Text("Add", fontWeight = FontWeight.Bold) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar participante",
                            modifier = Modifier.size(InputChipDefaults.IconSize)
                        )
                    },
                    shape = RoundedCornerShape(32.dp),
                    colors = inputChipColors(
                        containerColor = Pink40,
                        labelColor = Purple40
                    )
                )
            }
            items(participants) { participant ->
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .combinedClickable(
                            onClick = { },
                            onLongClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onLongPress(participant.id)
                            }
                        ),
                    shape = RoundedCornerShape(32.dp),
                    color = Pink40
                ) {
                    Text(
                        text = participant.name,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        color = Purple40,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
