package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AddParticipantDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text("Agregar Participante", fontWeight = FontWeight.Bold) },
        text = {
            PurpleTextField(
                text = "Nombre",
                placeholder = "Nombre del participante",
                value = name,
                onValueChange = { name = it },
                leadingIcon = Icons.Default.PersonAdd
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name) }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onConfirm(name) }) {
                Text("Cancelar", color = Color.Red)
            }
        }
    )

}