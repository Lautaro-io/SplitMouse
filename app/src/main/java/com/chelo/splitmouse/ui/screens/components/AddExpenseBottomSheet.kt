package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults.inputChipColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseBottomSheet(
    onDismiss: () -> Unit,
    participants: List<Participant>,
    onAddExpenseClick: (Double, String, Long) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var participantId by remember { mutableLongStateOf(0L) }
    var amount by remember { mutableDoubleStateOf(0.0) }
    var nameExpense by remember { mutableStateOf("") }

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
                        onClick = { participantId = participant.id },
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
                value = nameExpense,
                onValueChange = { nameExpense = it },
                leadingIcon = Icons.Default.ShoppingBag
            )
            PurpleTextField(
                text = "Valor",
                placeholder = "$5.000",
                value = amount.toInt().toString(),
                onValueChange = { amount = it.toDouble() },
                leadingIcon = Icons.Default.AttachMoney,
                isNumber = true
            )



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onAddExpenseClick(amount,nameExpense, participantId)
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