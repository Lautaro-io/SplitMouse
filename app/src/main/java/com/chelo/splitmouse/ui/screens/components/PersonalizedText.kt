package com.chelo.splitmouse.ui.screens.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Composable
fun PersonalizedText( fullText : String , highlightText : List<String> , fontSize : Int , color : Color = VioletaFuerte, fontWeight: FontWeight = FontWeight.Bold){
    val annotatedString = buildAnnotatedString {
        val parts = fullText.split(" ")
        parts.forEach { word ->
            if (highlightText.contains(word)){
                withStyle(style = SpanStyle(color = color , fontWeight = FontWeight.Bold)){
                    append("$word " )
                }
            }else{
                append("$word ")
            }

        }
    }
    Text(text = annotatedString, fontSize = fontSize.sp, fontWeight = fontWeight , maxLines = 2 )

}