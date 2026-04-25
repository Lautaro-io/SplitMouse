package com.chelo.splitmouse.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Debt
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.screens.components.ParticipantsTip
import com.chelo.splitmouse.ui.screens.components.PersonalizedText
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink123
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.ui.toArgentineCurrency
import com.chelo.splitmouse.viewmodel.EventDetailViewModel

@Composable
fun SettlementScreen(
    onBack: () -> Unit,
    viewModel: EventDetailViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    val event = state.event ?: return

    BackHandler() {
        onBack()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CardHeader(event, state.participants.size, onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Liquidacion de pagos",
                color = BlackPurple,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            LazyColumn() {
                items(state.debts.reversed()) { debt ->
                    DebtItem(debt)
                }
            }
        }
    }

}

@Composable
fun DebtItem(debt: Debt) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Pink40)
            ) {
                Text(
                    debt.from.take(2).uppercase(),
                    color = VioletaFuerte,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )
            }


            PersonalizedText(
                "${debt.from} paga a ${debt.to}",
                listOf(debt.from, debt.to),
                16,
                color = BlackPurple,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = debt.amount.toArgentineCurrency(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

        }

    }
}


@Composable
fun CardHeader(event: Event, participantSize: Int = 5, onBack: () -> Unit = {}) {
    val bgColor = Brush.linearGradient(
        colors = listOf(Purple40, VioletaFuerte),
        start = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
        end = Offset(0f, 0f)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(bgColor),

        ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopStart)
                .padding(start = 8.dp),

            ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "Atras",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            modifier = Modifier
                .matchParentSize()
                .statusBarsPadding()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "PLAN DE PAGO",
                        color = Color.LightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 24.sp
                    )
                    Text(
                        event.name,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 32.sp
                    )
                }
                ParticipantsTip("$participantSize \npersonas", modifier = Modifier.weight(1f))

            }

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                PersonalizedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                ) {
                    Text(
                        "Total gastado.",
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        event.totalAmount.toArgentineCurrency(),
                        maxLines = 1,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                PersonalizedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                ) {
                    Text(
                        "Cuota fija",
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val quote = event.totalAmount / participantSize
                    Text(
                        quote.toArgentineCurrency(),
                        maxLines = 1,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "cada uno",
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }


        }


    }
}


@Composable
fun PersonalizedCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Pink123, contentColor = Color.White),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }

}
















