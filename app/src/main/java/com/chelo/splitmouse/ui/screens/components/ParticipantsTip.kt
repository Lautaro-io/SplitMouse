package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Preview
@Composable
fun ParticipantsTip(text: String = "5\nParticipants") {
    Surface(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Purple40),
        color = VioletaFuerte
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Group, contentDescription = "", tint = Color.White)
            Text(text, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(16.dp))
        }
    }

}
