package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddExpenseBottomSheet(
    expense: Expense? = null,
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
    var selectedParticipantId by remember { mutableStateOf<Long>(participants[0].id) }
    var isSelected by remember { mutableStateOf(false) }

    expense?.let {
        amount = it.amount
        nameExpense = it.description
        selectedParticipantId = it.payerId
    }
    fun clearFields() {
        amount = 0.0
        nameExpense = ""
        selectedParticipantId = participants.firstOrNull()?.id ?: 0L
    }


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .imePadding()
                .imeNestedScroll()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                stringResource(R.string.add_expense_title),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                stringResource(R.string.event_description_subtitle),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
            Text(
                stringResource(R.string.who_paid_label),
                fontWeight = FontWeight.ExtraBold,
                color = VioletaFuerte,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
            LazyRow() {
                if (participants.isEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.at_least_one_participant_error),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                items(participants) { participant ->
                    isSelected = selectedParticipantId == participant.id
                    InputChip(
                        selected = isSelected,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = {
                            selectedParticipantId = participant.id
                            participantId = selectedParticipantId
                        },
                        label = { Text(participant.name) },
                        shape = RoundedCornerShape(32.dp),
                        colors = inputChipColors(
                            selectedContainerColor = VioletaFuerte,
                            selectedLabelColor = Color.White,
                            containerColor = Pink40,
                            labelColor = Purple40
                        )
                    )
                }
            }
            PurpleTextField(
                text = stringResource(R.string.product_name_label),
                placeholder = stringResource(R.string.product_name_placeholder),
                value = nameExpense,
                onValueChange = { nameExpense = it },
                leadingIcon = Icons.Default.ShoppingBag
            )
            PurpleTextField(
                text = stringResource(R.string.value_label),
                placeholder = stringResource(R.string.value_placeholder),
                value = amount.toInt().toString(),
                onValueChange = { amount = it.toDouble() },
                leadingIcon = Icons.Default.AttachMoney,
                isNumber = true
            )



            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onAddExpenseClick(amount, nameExpense, selectedParticipantId)
                    clearFields()
                    onDismiss()
                },
                enabled = amount > 0 && nameExpense.isNotBlank(),
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
                    text = if (expense != null) stringResource(R.string.update_expense_action) else stringResource(R.string.add_expense_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.cancel_action),
                    color = Purple40
                )
            }
        }
    }
}