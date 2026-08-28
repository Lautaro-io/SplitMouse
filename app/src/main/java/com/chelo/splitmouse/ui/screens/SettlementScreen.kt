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
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Debt
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.screens.components.PersonalizedText
import com.chelo.splitmouse.ui.theme.Pink123
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

    BackHandler {
        onBack()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        CardHeader(event, state.participants.size, onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                stringResource(R.string.settlement_title),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            LazyColumn {
                items(state.debts.reversed()) { debt ->
                    DebtItem(debt)
                }
                item {
                    Spacer(modifier = Modifier.height(64.dp))
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    debt.from.take(2).uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )
            }

            PersonalizedText(
                stringResource(R.string.debt_payment_text, debt.from.take(8), debt.to.take(8)),
                listOf(debt.from, debt.to),
                16,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = debt.amount.toArgentineCurrency(),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.sp
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
            .fillMaxHeight(0.5f)
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
                contentDescription = stringResource(R.string.back_icon_desc),
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
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.payment_plan_label),
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
                // Usamos Pink123 para el tip de participantes dentro del header violeta para que no cambie en dark mode
                ParticipantsTipHeader(stringResource(R.string.people_count_label, participantSize), modifier = Modifier.weight(1f))
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
                        stringResource(R.string.total_spent_label),
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        event.totalAmount.toArgentineCurrency(),
                        maxLines = 1,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                PersonalizedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(180.dp)
                ) {
                    Text(
                        stringResource(R.string.fixed_fee_label),
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val quote = if (participantSize > 0) event.totalAmount / participantSize else 0.0
                    Text(
                        quote.toArgentineCurrency(),
                        maxLines = 1,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        stringResource(R.string.each_one_label),
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
        colors = CardDefaults.cardColors(
            containerColor = Pink123, // Mantenemos el lila fijo para estas cards del header
            contentColor = Color.White
        ),
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

@Composable
fun ParticipantsTipHeader(text: String , modifier : Modifier = Modifier) {
    androidx.compose.material3.Surface(
        modifier = modifier
            .padding(16.dp)
            ,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 2.dp,
        color = Pink123 // Color fijo para el header
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Group, contentDescription = "", tint = Color.White)
            Text(
                text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                maxLines = 2
            )
        }
    }
}
