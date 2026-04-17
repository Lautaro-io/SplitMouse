package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    onParticipantClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Participantes",
                color = VioletaFuerte,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            TextButton(onClick = { TODO() }) {
                Text(
                    text = "Ver Más",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = VioletaFuerte
                )

            }
        }
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
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
                InputChip(
                    selected = false,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = onParticipantClick,
                    label = { Text(participant.name) },
                    shape = RoundedCornerShape(32.dp),
                    colors = inputChipColors(
                        containerColor = Pink40,
                        labelColor = Purple40
                    )
                )
            }
        }
    }
}
