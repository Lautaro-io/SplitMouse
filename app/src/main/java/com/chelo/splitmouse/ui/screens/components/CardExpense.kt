package com.chelo.splitmouse.ui.screens.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Expense
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.ui.toArgentineCurrency
import kotlin.math.roundToInt


@Composable
fun CardExpense(name : String, expense: Expense = Expense(1, 1000.0, "Coca Cola", 1, 1)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding( 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Pink40)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pergamino),
                    contentDescription = "Expense",
                    tint = VioletaFuerte,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(expense.description, fontWeight = FontWeight.Bold , fontSize = 24.sp)
                PersonalizedText("Paid by $name", listOf(name), 16)
            }
            Column(modifier = Modifier.weight(1f).padding(vertical = 8.dp), horizontalAlignment = Alignment.End) {
                Text(expense.amount.toArgentineCurrency(), fontWeight = FontWeight.ExtraBold , fontSize = 24.sp)
            }

        }
    }
}