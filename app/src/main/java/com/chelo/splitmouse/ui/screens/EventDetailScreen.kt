package com.chelo.splitmouse.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.screens.components.AddExpenseBottomSheet
import com.chelo.splitmouse.ui.screens.components.AddParticipantDialog
import com.chelo.splitmouse.ui.screens.components.ContentDetailHeader
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel


@Composable
fun EventDetailScreen(onBack: () -> Unit, viewModel: EventDetailViewModel) {

    val state by viewModel.uiState.collectAsState()
    var showDialogParticipant by remember { mutableStateOf(false) }
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
    ) { innerPadding ->
        if (showDialogParticipant) {
            AddParticipantDialog(
                onDismiss = { showDialogParticipant = false },
                onConfirm = {
                    viewModel.addParticipant(Participant(0, it, state.event?.id!!))
                    showDialogParticipant = false
                })

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            ContentDetailHeader(
                detailViewModel = viewModel,
                onAddParticipantClick = { showDialogParticipant = true })
            if (showBottomModal) {
                AddExpenseBottomSheet(onDismiss = { showBottomModal = false }, state.participants)
            }

        }

    }
}















