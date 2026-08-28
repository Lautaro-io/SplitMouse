package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.chelo.splitmouse.R

@Composable
fun AddParticipantDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_participant_title), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) },
        text = {
            PurpleTextField(
                text = stringResource(R.string.name_label),
                placeholder = stringResource(R.string.name_placeholder),
                value = name,
                onValueChange = { name = it },
                leadingIcon = Icons.Default.PersonAdd
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name)}, enabled = name.isNotBlank()) {
                Text(stringResource(R.string.add_participant_action))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel_action), color = MaterialTheme.colorScheme.error)
            }
        }
    )

}