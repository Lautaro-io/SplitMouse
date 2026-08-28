package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chelo.splitmouse.R

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
            text = { Text(stringResource(R.string.edit_action)) },
            onClick = { onEdit(); onDismiss() },
            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit_icon_desc)) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.delete_action), color = MaterialTheme.colorScheme.error) },
            onClick = { onDelete(); onDismiss() },
            leadingIcon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_icon_desc),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}
