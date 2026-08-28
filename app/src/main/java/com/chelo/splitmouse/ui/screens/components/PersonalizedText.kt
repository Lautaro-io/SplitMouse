package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun PersonalizedText(
    fullText: String,
    highlightText: List<String>,
    fontSize: Int,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val highlightColor = if (color == Color.Unspecified) MaterialTheme.colorScheme.primary else color
    val annotatedString = buildAnnotatedString {
        val parts = fullText.split(" ")
        parts.forEach { word ->
            if (highlightText.any { it.equals(word, ignoreCase = true) }) {
                withStyle(style = SpanStyle(color = highlightColor, fontWeight = FontWeight.Bold)) {
                    append("$word ")
                }
            } else {
                append("$word ")
            }

        }
    }
    Text(
        text = annotatedString,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        maxLines = 2,
        color = MaterialTheme.colorScheme.onSurface
    )
}
