package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EventActionsMenu(
    modifier : Modifier = Modifier,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier

    ) {
        DropdownMenuItem(
            text = { Text("Editar") },
            onClick = { onEdit(); onDismiss() },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Editar") }
        )
        DropdownMenuItem(
            text = { Text("Eliminar", color = Color.Red) },
            onClick = { onDelete(); onDismiss() },
            leadingIcon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "ELiminar",
                    tint = Color.Red
                )
            }
        )
    }
}
