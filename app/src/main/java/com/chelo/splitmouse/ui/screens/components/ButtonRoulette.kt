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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chelo.splitmouse.R

@Composable
fun ButtonRoulette(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
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
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(R.drawable.ic_dice), contentDescription = stringResource(R.string.dice_icon_desc), Modifier.size(48.dp))
            }
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(start = 4.dp)) {
                Text(stringResource(R.string.roulette_label), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onTertiary)
                Text(stringResource(R.string.roulette_title), color = MaterialTheme.colorScheme.onTertiary)
            }
            TextButton(onClick = onClick) { Text(stringResource(R.string.play_now_action), color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold) }
        }
    }
}