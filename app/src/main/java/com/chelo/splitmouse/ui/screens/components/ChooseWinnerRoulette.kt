package com.chelo.splitmouse.ui.screens.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Participant
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.LowYellow
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.Pink80
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ChooseWinnerRoulette(participants: List<Participant>, modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        val angle = 360f / participants.size
        val colorList = listOf(
            Color(0xFFF44336),
            Color(0xFFE91E63),
            Color(0xFF9C27B0),
            Color(0xFF2196F3),
            Color(0xFF4CAF50),
            Color(0xFFFFEB3B)
        )
        val canvasCenter = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2

        participants.forEachIndexed { index, participant ->
            val startAngle = angle * index
            val color = colorList[index % colorList.size]

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = angle,
                useCenter = true,
                size = size
            )

            val middleAngle = (startAngle + angle / 2) * (Math.PI / 180f).toFloat()
            val textDistance = radius * 0.6f
            val x = canvasCenter.x + textDistance * kotlin.math.cos(middleAngle)
            val y = canvasCenter.y + textDistance * kotlin.math.sin(middleAngle)

            val textLayoutResult = textMeasurer.measure(
                text = participant.name.capitalize(Locale.ROOT),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            rotate(
                degrees = startAngle + angle / 2,
                pivot = Offset(x, y)
            ) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2,
                        y - textLayoutResult.size.height / 2
                    )
                )
            }
        }
    }
}

@Composable
fun DialogRoulette(participants: List<Participant>, onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }
    var winner by remember { mutableStateOf<Participant?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onDismiss) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_dialog_desc),
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.roulette_title_dialog),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BlackPurple
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.roulette_description),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = BlackPurple
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.clip(CircleShape).background(Pink80)) {
                        ChooseWinnerRoulette(
                            participants,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                                .graphicsLayer(rotationZ = rotation.value)
                        )
                        Surface(
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center),
                            shape = CircleShape,
                            color = Color.White,
                            tonalElevation = 4.dp,
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_dice),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFF6200EE)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .offset(y = (-20).dp),
                            tint = Color.Red
                        )
                    }
                    winner?.let {
                        CardWinner(it.name)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Pink40,
                                contentColor = VioletaFuerte
                            )
                        ) {
                            Text(stringResource(R.string.cancel_uppercase), fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = {
                                val indexGanador = participants.indices.random()
                                val sweep = 360f / participants.size
                                val anguloGanador = 270f - (indexGanador * sweep) - (sweep / 2)

                                scope.launch {
                                    rotation.snapTo(rotation.value % 360f)
                                    rotation.animateTo(
                                        targetValue = rotation.value + (360f * 5) + (anguloGanador - (rotation.value % 360f)),
                                        animationSpec = tween(3000, easing = FastOutSlowInEasing)
                                    )
                                    winner = participants[indexGanador]
                                }
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = VioletaFuerte,
                                contentColor = Pink40
                            ),
                            modifier = Modifier.padding(top = 24.dp)
                        ) {
                            Text(stringResource(R.string.spin_action))
                        }
                    }


                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardWinner(name: String = "Marco") {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(32.dp),
        color = LowYellow

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.game_over), fontSize = 18.sp)
            Text(stringResource(R.string.winner_pays_all, name), fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        }
    }
}