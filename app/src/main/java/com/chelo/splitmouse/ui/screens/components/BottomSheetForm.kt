package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.AddEventViewModel
import com.chelo.splitmouse.viewmodel.FieldType
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomForm(onDismiss: () -> Unit, viewmodel: AddEventViewModel = koinViewModel()) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showDatePicker by remember { mutableStateOf(false) }
    val state by viewmodel.formState.collectAsState()


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
                "Nuevo Evento",
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
            PurpleTextField(
                text = "Nombre del evento",
                placeholder = "Asado con amigos",
                value = state.name,
                onValueChange = { viewmodel.updateFormState(FieldType.NAME, it) },
                trailingIcon = Icons.Default.Preview
            )
            PurpleTextField(
                text = "Cuando es?",
                placeholder = "Hoy, 12:00",
                value = state.date,
                readOnly = true,
                onValueChange = { },
                trailingIcon = Icons.Default.CalendarMonth,
                showDatePicker = { showDatePicker = true }
            )
            if (showDatePicker) {
                DatePickerField(
                    onDateSelected = { viewmodel.updateFormState(FieldType.DATE, it) },
                    onDismiss = { showDatePicker = false }
                )
            }


            PurpleTextField(
                text = "Descripcion (Opcional.. )",
                placeholder = "Describe algo del evento",
                value = state.description ?: "",
                onValueChange = { viewmodel.updateFormState(FieldType.DESCRIPTION, it) },
                trailingIcon = null
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewmodel.addEvent(onSuccess = {
                        onDismiss()
                    })
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
                    text = "Crear Evento",
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