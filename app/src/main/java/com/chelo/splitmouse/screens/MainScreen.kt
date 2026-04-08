package com.chelo.splitmouse.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.PurpleGrey80
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController? = null, viewmodel: MainViewModel = koinViewModel()) {

    val state by viewmodel.uiState.collectAsState()
    val events = state.events


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(imageVector = Icons.Default.SupervisedUserCircle, contentDescription = "")
                Icon(imageVector = Icons.Default.SupervisedUserCircle, contentDescription = "")
                Icon(imageVector = Icons.Default.SupervisedUserCircle, contentDescription = "")
            }
        },

        ) { innerPadding ->
        MainContent(modifier = Modifier.padding(innerPadding), viewmodel)


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, viewmodel: MainViewModel) {
    val state by viewmodel.uiState.collectAsState()
    val events = state.events
    var showBottomModal by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Column(
        modifier = modifier,

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    onClick = {
                        showBottomModal = true
                    },
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
//        LazyColumn() {
//            items(events) {
//                Text(it.name)
//            }
//        }
        if (showBottomModal) {
            ModalBottomSheet(
                onDismissRequest = { showBottomModal = false },
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
                        "Create Event",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        "Set up a new shared ledger for your friends.",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, bottom = 16.dp),
                        textAlign = TextAlign.Start,
                        color = Color.Gray
                    )
                    PurpleTextField(
                        text = "Nombre del evento",
                        placeholder = "Asado con amigos",
                        value = "",
                        onValueChange = { },
                        icon = Icons.Default.Preview
                    )
                    PurpleTextField(
                        text = "Cuando es?",
                        placeholder = "Hoy, 12:00",
                        value = "",
                        onValueChange = { },
                        icon = Icons.Default.CalendarMonth
                    )
                    PurpleTextField(
                        text = "Descripcion (Opcional.. )",
                        placeholder = "Describe algo del evento",
                        value = "",
                        onValueChange = { },
                        icon = null
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            showBottomModal = true
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

                    TextButton(onClick = { showBottomModal = false }) {
                        Text(
                            "Cancelar",
                            color = Purple40
                        )
                    }
                }
            }
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
    icon: ImageVector? = Icons.Default.ArrowCircleRight,
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
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
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
    }

}

