package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeMenuBackground(onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onEdit,
            modifier = Modifier.background(Color.Blue, CircleShape)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = onDelete, modifier = Modifier.background(Color.Red, CircleShape)) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)

        }
    }
}