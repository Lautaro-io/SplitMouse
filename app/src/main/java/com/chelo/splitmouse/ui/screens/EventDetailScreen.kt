package com.chelo.splitmouse.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.InputChipDefaults.inputChipColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel


@Composable
fun EventDetailScreen(onBack: () -> Unit, viewModel: EventDetailViewModel) {

    val state by viewModel.uiState.collectAsState()
    val event = state.event
    var showBottomModal by remember { mutableStateOf(false) }

    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier.size(32.dp),
                        tint = VioletaFuerte
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "Detalle del Evento",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    color = VioletaFuerte,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
        },
        floatingActionButton = {
            Button(
                onClick = {showBottomModal = true},
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VioletaFuerte,
                    contentColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.AddCard, contentDescription = "Agregar gasto")
                    Text("Agregar gasto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            event?.let {
                ContentDetailHeader(event)
                if (showBottomModal){
                    AddExpenseBottomSheet(onDismiss = { showBottomModal = false })
                }
            }
        }

    }
}


@Composable
fun ContentDetailHeader(event: Event) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column() {
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

        }
        CardDescriptionDetail(event.description)
        CardTotalSpent(event)
        ParticipantPanel()

        Text(
            "Gastos",
            color = VioletaFuerte,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

    }

}


@Composable
fun CardDescriptionDetail(text: String = "Descripcion del evento") {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Pink40
    ) {
        Text(
            text,
            color = VioletaFuerte,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}

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


@Preview
@Composable
fun ParticipantsTip(text: String = "5\nParticipants") {
    Surface(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Purple40),
        color = VioletaFuerte
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Group, contentDescription = "", tint = Color.White)
            Text(text, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(16.dp))
        }
    }

}

val participantList = listOf(
    Participant(id = 1, name = "Lucas (Vos)", eventId = 1),
    Participant(id = 2, name = "Santi", eventId = 1),
    Participant(id = 3, name = "Zaira", eventId = 1),
    Participant(id = 4, name = "Joaquín", eventId = 1),
    Participant(id = 5, name = "Matias", eventId = 1),
    Participant(id = 6, name = "Ivan", eventId = 1)
)

@Preview
@Composable
fun ParticipantPanel(
    participants: List<Participant> = participantList,
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
        LazyRow() {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseBottomSheet(onDismiss: () -> Unit, participants : List<Participant> = participantList, onParticipantClick: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

//    val state by viewmodel.formState.collectAsState()


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                "Agregar gasto",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                "Lleva la cuenta de cada juntada con tus amigos.",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
            Text(
                "Quien pago?",
                fontWeight = FontWeight.ExtraBold,
                color = VioletaFuerte,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
            LazyRow() {

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
            PurpleTextField(
                text = "Nombre del Producto",
                placeholder = "Coca Cola",
                value = "",
                onValueChange = { },
                leadingIcon = Icons.Default.ShoppingBag
            )
            PurpleTextField(
                text = "Valor",
                placeholder = "$5.000",
                value = "",
                onValueChange = { },
                leadingIcon = Icons.Default.AttachMoney
            )



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VioletaFuerte,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Agregar gasto",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onDismiss) {
                Text(
                    "Cancelar",
                    color = Purple40
                )
            }
        }
    }
}