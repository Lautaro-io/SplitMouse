package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Composable
fun CardDescriptionDetail(text: String = "Descripcion del evento") {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Pink40
    ) {
        Text(
            text,
            color = VioletaFuerte,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}