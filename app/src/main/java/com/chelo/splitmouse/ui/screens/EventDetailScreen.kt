package com.chelo.splitmouse.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.ui.screens.components.AddExpenseBottomSheet
import com.chelo.splitmouse.ui.screens.components.AddParticipantDialog
import com.chelo.splitmouse.ui.screens.components.ContentDetail
import com.chelo.splitmouse.ui.screens.components.DialogRoulette
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel


@Composable
fun EventDetailScreen(
    onBack: () -> Unit,
    navToDebt: (Long) -> Unit,
    viewModel: EventDetailViewModel,
) {

    val state by viewModel.uiState.collectAsState()
    var showDialogParticipant by remember { mutableStateOf(false) }
    var showBottomModal by remember { mutableStateOf(false) }
    var showDialogDeleteExpense by remember { mutableStateOf(false) }
    var showDeleteParticipantDialog by remember { mutableStateOf(false) }
    var showRouletteDialog by remember { mutableStateOf(false) }
    var participantId: Long? by remember { mutableStateOf(null) }
    val bgColor = Brush.verticalGradient(
        0.7f to Color.Transparent,
        1.0f to Pink40
    )
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
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
                    DeleteDialog(
                        title = "Eliminar participante",
                        label = "Estas seguro que quieres eliminar a",
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

            showRouletteDialog -> DialogRoulette(state.participants) { showRouletteDialog = false }

        }


        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                ContentDetail(
                    detailViewModel = viewModel,
                    onAddParticipantClick = { showDialogParticipant = true },
                    onDeleteParticipant = {
                        showDeleteParticipantDialog = true; participantId = it
                    },
                    onExpenseEdit = {
                        selectedExpense = it
                        showBottomModal = true
                        viewModel.updateExpense(it)
                    },
                    onExpenseDelete = {
                        selectedExpense = it
                        showDialogDeleteExpense = true
                    },
                    onButtonRouletteClick = {
                        showRouletteDialog = true
                    },
                    onNewExpenseClick = {
                        showBottomModal = true
                    },
                    onDebtButtonClick = {
                        navToDebt(viewModel.eventId)
                    }

                )
                when {
                    showBottomModal -> {
                        AddExpenseBottomSheet(
                            selectedExpense,
                            onDismiss = { showBottomModal = false },
                            state.participants,
                            onAddExpenseClick = { amount, description, payerId ->
                                if (selectedExpense != null) {
                                    val updatedExpense = selectedExpense!!.copy(
                                        amount = amount,
                                        description = description,
                                        payerId = payerId
                                    )
                                    viewModel.updateExpense(updatedExpense)
                                } else {
                                    viewModel.addExpense(amount, description, payerId)
                                }
                                showBottomModal = false
                            })
                    }

                    showDialogDeleteExpense ->
                        selectedExpense?.let { expense ->
                            DeleteDialog(
                                onDismiss = {
                                    showDialogDeleteExpense = false
                                },
                                onConfirm = {
                                    showDialogDeleteExpense = false
                                    viewModel.deleteExpense(expense)
                                },
                                name = expense.description,
                                title = "Eliminar gasto",
                                label = "Desea eliminar el gasto ${expense.description}"
                            )
                        }


                }


            }

        }


    }
}

@Composable
fun DeleteDialog(
    title: String = "Eliminar Participante",
    label: String = "",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    name: String,
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Text(label)
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


@Composable
fun TextEmptyParticipants(text: String, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(R.drawable.ic_cash),
            modifier = Modifier.size(64.dp),
            contentDescription = "Agrega nuevos participantes.",
            tint = VioletaFuerte
        )

        Text(
            text,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BlackPurple,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }


}








