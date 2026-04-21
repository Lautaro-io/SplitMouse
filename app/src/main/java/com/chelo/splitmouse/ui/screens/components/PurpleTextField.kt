package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.ui.theme.BlackPurple
import com.chelo.splitmouse.ui.theme.Pink40
import com.chelo.splitmouse.ui.theme.PurpleGrey80
import com.chelo.splitmouse.ui.theme.VioletaFuerte

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PurpleTextField(
    text: String = "Event Name",
    placeholder: String = "Asado con amigos",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    trailingIcon: ImageVector? = null,
    leadingIcon: ImageVector? = null,
    showDatePicker: () -> Unit = {},
    isNumber: Boolean = false,
) {
    val keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            color = BlackPurple,
            textAlign = TextAlign.Start
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()

                .clip(RoundedCornerShape(32.dp))
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { if (!readOnly) onValueChange(it) },
                modifier = Modifier
                    .fillMaxWidth().padding(4.dp),
                shape = RoundedCornerShape(32.dp),
                maxLines = 1,
                readOnly = readOnly,
                placeholder = {
                    Text(
                        placeholder,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(4.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Pink40,
                    focusedContainerColor = Color.White,
                    unfocusedPlaceholderColor = PurpleGrey80,
                    focusedBorderColor = Transparent,
                    unfocusedBorderColor = Transparent

                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                trailingIcon = {
                    trailingIcon?.let {
                        Icon(
                            trailingIcon,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 16.dp, start = 8.dp),
                            tint = VioletaFuerte,
                        )

                    }
                },
                leadingIcon = {
                    leadingIcon?.let {
                        Icon(
                            leadingIcon,
                            contentDescription = "",
                            modifier = Modifier.padding(end = 16.dp, start = 8.dp),
                            tint = VioletaFuerte
                        )
                    }
                })
            if (readOnly) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker() })
            }

        }
    }

}
