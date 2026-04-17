package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Composable
fun CardTotalSpent(
    event: Event = Event(
        id = 1L,
        name = "Asado en lo de Juan",
        date = "15 Abr 2026, 21:00",
        description = "Asado con los chicos del secundario. Traer bebidas y buen humor. Se divide todo al final.",
        totalAmount = 45500.0
    ),
) {
    val color = Brush.verticalGradient(listOf(Purple40, BlackPurple))
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = VioletaFuerte,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(48.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Total Spent",
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 18.dp),
                    textAlign = TextAlign.Start
                )
                Text(
                    "$ ${event.totalAmount.toInt()}",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp),
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ParticipantsTip()
                    Text(
                        "REPARTIENDO EQUITATIVAMENTE",
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                }

            }

        }
    }
}
