package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.LowYellow
import com.chelo.splitmouse.ui.theme.Yellow

@Composable
fun ButtonRoulette(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = LowYellow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 16.dp , horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(52.dp)
                    .background(Yellow),
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(R.drawable.ic_dice), contentDescription = "Dado", Modifier.size(48.dp))
            }
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 4.dp)) {
                Text("ROULETTE", fontWeight = FontWeight.SemiBold )
                Text("Deja a la suerte quien paga!")
            }
            TextButton(onClick = onClick) { Text("JUGAR AHORA>", color = BlackPurple, fontWeight = FontWeight.Bold) }
        }
    }
}