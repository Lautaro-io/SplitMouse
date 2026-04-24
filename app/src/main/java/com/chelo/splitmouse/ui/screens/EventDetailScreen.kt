package com.chelo.splitmouse.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.screens.components.AddExpenseBottomSheet
import com.chelo.splitmouse.ui.screens.components.AddParticipantDialog
import com.chelo.splitmouse.ui.screens.components.ContentDetailHeader
import com.chelo.splitmouse.ui.screens.components.SettlementScreen
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel


@Composable
fun EventDetailScreen(onBack: () -> Unit, navToDebt: (Long) -> Unit, viewModel: EventDetailViewModel) {

    val state by viewModel.uiState.collectAsState()
    var showDialogParticipant by remember { mutableStateOf(false) }
    var showBottomModal by remember { mutableStateOf(false) }
    var showDeleteParticipantDialog by remember { mutableStateOf(false) }
    var participantId: Long? by remember { mutableStateOf(null) }
    val bgColor = Brush.verticalGradient(
        0.7f to Color.Transparent,
        1.0f to Pink40
    )
    var goToSettlement by remember { mutableStateOf(false) }
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
                        imageVector = Icons.Default.ArrowBackIosNew,
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
            Column() {
                if (state.debts.isNotEmpty()){
                    Button(
                        onClick = { navToDebt(viewModel.eventId) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green.copy(alpha = 0.5f, blue = .2f),
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Checklist, contentDescription = "Repartir gastos")
                            Text("Repartir gastos", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                    }
                }

                Button(
                    onClick = { showBottomModal = true },
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
        }
    ) { innerPadding ->
        when {
            showDialogParticipant -> {
                AddParticipantDialog(
                    onDismiss = { showDialogParticipant = false },
                    onConfirm = {
                        viewModel.addParticipant(it)
                        showDialogParticipant = false
                    })

            }

            showDeleteParticipantDialog -> {
                participantId?.let {
                    DeleteParticipantDialog(
                        onDismiss = { showDeleteParticipantDialog = false },
                        onConfirm = {
                            viewModel.deleteParticipant(it)
                            showDeleteParticipantDialog = false
                        },
                        name = (state.participants.find { participant -> participant.id == participantId }?.name
                            ?: "")
                    )
                }

            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (goToSettlement) {
                SettlementScreen(
                    onBack = onBack,
                    viewModel
                )
            } else {
                ContentDetailHeader(
                    detailViewModel = viewModel,
                    onAddParticipantClick = { showDialogParticipant = true },
                    onDeleteParticipant = {
                        showDeleteParticipantDialog = true; participantId = it
                    })
                if (showBottomModal) {
                    AddExpenseBottomSheet(
                        onDismiss = { showBottomModal = false },
                        state.participants,
                        onAddExpenseClick = { amount, description, payerId ->
                            viewModel.addExpense(amount, description, payerId)
                            showBottomModal = false
                        })
                }

            }

        }


    }
}

@Composable
fun DeleteParticipantDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, name: String) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text("Eliminar Participante", fontWeight = FontWeight.Bold) },
        text = {
            Text("Estas seguro que quieres eliminar a $name")
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Eliminar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}













