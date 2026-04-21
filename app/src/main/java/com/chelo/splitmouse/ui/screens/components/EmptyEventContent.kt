package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Pink80
import com.chelo.splitmouse.ui.theme.Purple80
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Preview(showBackground = true)
@Composable
fun EmptyEventContent(onButtonClick:()-> Unit = {}) {
    val btnbg = Brush.horizontalGradient(listOf(VioletaFuerte, Purple80))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardEmptyEvent()
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
                .background(Purple80.copy(alpha = 0.1f))
        ) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .rotate(-10f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.pergamino),
                        contentDescription = "Evento vacio",
                        tint = VioletaFuerte,
                        modifier = Modifier.size(120.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .width(30.dp),
                        thickness = 4.dp,
                        color = Pink80.copy(0.4f)
                    )
                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .width(20.dp),
                        thickness = 4.dp,
                        color = Pink80.copy(0.4f)
                    )
                }

            }
        }
        Text("No hay eventos aun", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text(
            "Aun no tienes eventos activos. Crea uno para empezar a organizar tus gastos!",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(btnbg)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Agregar evento",
                        tint = Color.White
                    )
                    Text("Crear nuevo evento", color = Color.White , fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}


@Composable
fun CardEmptyEvent(){
    val bgColor = Brush.horizontalGradient(
        0.7f to Color.Transparent,
        1.0f to Pink40
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
        ,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 0.1.dp,


        ) {
        Column(
            modifier = Modifier.background(bgColor).padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PersonalizedText("Bienvenido a Split Mouse !", listOf("Split", "Mouse"), fontSize = 32)
            Text(
                "Empeza a organizar tus juntadas y repartir gastos facilmente.",
                modifier = Modifier.padding(vertical = 24.dp),
                color = Color.Gray,
                fontSize = 18.sp,
            )

        }

    }
}