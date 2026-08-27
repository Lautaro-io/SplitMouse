package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.ui.screens.TextEmptyParticipants
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.EventDetailViewModel

enum class DragValue { Settled, Open }

@Composable
fun ContentDetail(
    detailViewModel: EventDetailViewModel,
    onAddParticipantClick: () -> Unit,
    onDeleteParticipant: (Long) -> Unit,
    onExpenseEdit: (Expense) -> Unit = {},
    onExpenseDelete: (Expense) -> Unit = {},
    onButtonRouletteClick: () -> Unit,
    onDebtButtonClick: () -> Unit,
    onNewExpenseClick: () -> Unit,
) {
    val state by detailViewModel.uiState.collectAsState()
    val event = state.event ?: return
    val participants = state.participants
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        LazyColumn() {


            item {
                ContentDetailHeader(
                    event,
                    participants,
                    onAddParticipantClick,
                    onDeleteParticipant,
                    onButtonRouletteClick
                )

                Text(
                    stringResource(R.string.expenses_label),
                    color = VioletaFuerte,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = stringResource(R.string.info_icon_desc),
                        tint = VioletaFuerte
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        stringResource(R.string.swipe_info),
                        color = VioletaFuerte,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                }


            }
            when {
                participants.isEmpty() ->
                    item { TextEmptyParticipants(stringResource(R.string.add_participants_msg)) }

                state.expenses.isEmpty() -> item { TextEmptyParticipants(stringResource(R.string.add_expenses_msg)) }
                else -> {

                    items(state.expenses, key = { it.id }) { expense ->
                        SweepableCardExpense(
                            expense,
                            participants,
                            onEdit = { onExpenseEdit(expense) },
                            onDelete = { onExpenseDelete(expense) })


                    }

                }
            }
            item {
                Column() {
                    if (state.debts.isNotEmpty()) {
                        Button(
                            onClick =
//                                        navToDebt(viewModel.eventId)
                                onDebtButtonClick
                            ,
                            modifier = Modifier.fillMaxWidth().padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
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
                                Icon(
                                    Icons.Default.Checklist,
                                    contentDescription = stringResource(R.string.split_expenses_icon_desc)
                                )
                                Text(
                                    stringResource(R.string.split_expenses_action),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                        }

                    }
                    if (state.participants.isNotEmpty()) {

                        Button(
                            onClick = onNewExpenseClick
//                                        showBottomModal = true
                            ,
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
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
                                Icon(
                                    Icons.Default.AddCard,
                                    contentDescription = stringResource(R.string.add_expense_title)
                                )
                                Text(
                                    stringResource(R.string.add_expense_title),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                        }
                    }
                }
            }

        }


    }

}
