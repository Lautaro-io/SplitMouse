package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

@Composable
fun CardEvent(event: Event, onEventClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEventClick() },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(4.dp),
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    event.name,
                    fontSize = 26.sp,
                    color = BlackPurple,
                    fontWeight = FontWeight.Bold
                )
                Text("")
            }
            if (event.description.isNotEmpty()) {
                Text(
                    event.description,
                    color = Color.Gray,
                    fontSize = 22.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(Pink40)
                    ,
                    contentAlignment = Alignment.Center) {
                    Text(
                        formatFecha(event.date), fontSize = 14.sp,
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("GASTADO")
                    Text(
                        event.totalAmount.toArgentineCurrency(),
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
