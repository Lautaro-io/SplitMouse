package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParticipantsTip(text: String , modifier : Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(16.dp)
            ,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.secondary
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Group, contentDescription = "", tint = MaterialTheme.colorScheme.onSecondary)
            Text(
                text,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                maxLines = 2
            )
        }
    }

}
