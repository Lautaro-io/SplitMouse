package com.chelo.splitmouse.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.PurpleGrey80
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.AddEventViewModel
import com.chelo.splitmouse.viewmodel.FieldType
import com.chelo.splitmouse.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(navController: NavController? = null, viewmodel: MainViewModel = koinViewModel()) {

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.SupervisedUserCircle,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    "SplitMouse",
                    fontSize = 38.sp,
                    fontStyle = FontStyle.Italic,
                    color = Purple40,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "",
                    tint = Purple40,
                    modifier = Modifier.size(32.dp)
                )
            }
        },

        ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 32.dp), viewmodel
        )


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, eventViewModel: MainViewModel) {
    val state by eventViewModel.uiState.collectAsState()
    val events = state.events
    var showBottomModal by remember { mutableStateOf(false) }


    var itemsCount by remember { mutableIntStateOf(3) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            item {
                CardAddEvent(onButtonClick = { showBottomModal = true })
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Eventos Activos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = BlackPurple
                    )
                    TextButton(onClick = { itemsCount = events.size }) {
                        Text(
                            text = if (itemsCount == events.size) "Ver Menos" else "Ver Más",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Purple40
                        )

                    }

                }
            }
            items(events.reversed().take(itemsCount)) {
                EventCard(it)
            }
        }
        if (showBottomModal) {
            BottomForm(onDismiss = { showBottomModal = false })
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PurpleTextField(
    text: String = "Event Name",
    placeholder: String = "Asado con amigos",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    icon: ImageVector? = Icons.Default.ArrowCircleRight,
    showDatePicker: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            color = BlackPurple,
            textAlign = TextAlign.Start
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { if (!readOnly) onValueChange(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                readOnly = readOnly,
                placeholder = {
                    Text(
                        placeholder,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Pink40,
                    focusedContainerColor = Color.White,
                    unfocusedPlaceholderColor = PurpleGrey80,
                    focusedBorderColor = Transparent,
                    unfocusedBorderColor = Transparent

                ),
                trailingIcon = {
                    icon?.let {
                        Icon(
                            icon,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 16.dp),
                            tint = Purple40,
                        )

                    }
                })
            if (readOnly) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker() })
            }

        }
    }

}


@Composable
fun DatePickerField(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val date = datePickerState.selectedDateMillis
                if (date != null) {
                    onDateSelected(
                        SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(Date(date))
                    )
                    onDismiss()
                }


                onDismiss()

            }) { Text("Aceptar") }
        },
    ) {
        DatePicker(state = datePickerState)
    }

}


@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    event.name,
                    fontSize = 26.sp,
                    color = BlackPurple,
                    fontWeight = FontWeight.Bold
                )
                Text("")
            }
            Text(
                event.description,
                color = Color.Gray,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    event.date, fontSize = 20.sp,
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 16.dp),
                    textAlign = TextAlign.Start,
                    color = Color.Gray
                )
                Column {
                    Text("Gastado")
                    Text(event.totalAmount.toString())

                }

            }

        }

    }

}


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
                icon = Icons.Default.Preview
            )
            PurpleTextField(
                text = "Cuando es?",
                placeholder = "Hoy, 12:00",
                value = state.date,
                readOnly = true,
                onValueChange = { },
                icon = Icons.Default.CalendarMonth,
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
                icon = null
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


@Composable
fun CardAddEvent(onButtonClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Pink40,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "¿Listos para otra salida?",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Text(
                "Organiza gastos, divide la cuenta y disfruta el momento",
                modifier = Modifier.padding(vertical = 24.dp),
                fontSize = 18.sp,
            )
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = ""
                    )
                    Text(
                        "Crear Nuevo Evento",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}
