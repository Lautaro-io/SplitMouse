package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel

@Composable
fun ContentDetailHeader(
    detailViewModel: EventDetailViewModel,
    onAddParticipantClick: () -> Unit,
    onDeleteParticipant: (Long) -> Unit,
) {
    val state by detailViewModel.uiState.collectAsState()
    val event = state.event ?: return
    val participants = state.participants
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn() {
            item {
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
                            contentDescription = "Fecha del evento",
                            tint = VioletaFuerte
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            event.date,
                            color = VioletaFuerte,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )

                    }
                    CardDescriptionDetail(event.description)
                    CardTotalSpent(event , participants.size)
                    ParticipantPanel(
                        participants,
                        onAddParticipantClick = onAddParticipantClick,
                        onLongPress = { onDeleteParticipant(it) })


                }

                Text(
                    "Gastos",
                    color = VioletaFuerte,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )


            }
            items(state.expenses) { expense ->
                CardExpense(
                    expense = expense,
                    name = participants.find { it.id == expense.payerId }?.name ?: ""
                )
            }
        }



    }

}