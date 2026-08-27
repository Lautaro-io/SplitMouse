package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.formatFecha
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Composable
fun ContentDetailHeader(
    event: Event,
    participants: List<Participant>,
    onAddParticipantClick: () -> Unit,
    onDeleteParticipant: (Long) -> Unit,
    onButtonRouletteClick: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            event.name,
            color = BlackPurple,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CalendarMonth,
                contentDescription = stringResource(R.string.event_date_label),
                tint = VioletaFuerte
            )
            Spacer(Modifier.width(4.dp))
            Text(
                formatFecha(event.date),
                color = VioletaFuerte,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

        }
        if (event.description.isNotEmpty()) {
            CardDescriptionDetail(event.description)
        }
        CardTotalSpent(event, participants.size)
        if (participants.isNotEmpty()){
            ButtonRoulette(onButtonRouletteClick)
        }

        ParticipantPanel(
            participants,
            onAddParticipantClick = onAddParticipantClick,
            onLongPress = { onDeleteParticipant(it) })


    }
}