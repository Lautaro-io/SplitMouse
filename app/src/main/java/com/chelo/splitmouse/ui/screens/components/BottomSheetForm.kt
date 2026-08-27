package com.chelo.splitmouse.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.theme.Purple40
import com.chelo.splitmouse.ui.theme.VioletaFuerte
import com.chelo.splitmouse.viewmodel.AddEventViewModel
import com.chelo.splitmouse.viewmodel.FieldType
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomForm(
    event: Event? = null,
    onDismiss: () -> Unit,
    viewmodel: AddEventViewModel = koinViewModel(),
    navigate: (Long) -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showDatePicker by remember { mutableStateOf(false) }
    val state by viewmodel.formState.collectAsState()
    event?.let {
        viewmodel.updateFormState(FieldType.ID, it.id.toString())
        viewmodel.updateFormState(FieldType.NAME, it.name)
        viewmodel.updateFormState(FieldType.DESCRIPTION, it.description)
        viewmodel.updateFormState(FieldType.DATE, it.date)
    }
    val onSaveAction = {
        if (event == null) {
            viewmodel.addEvent(onSuccess = { newId ->
                navigate(newId)
                onDismiss()
            })
        } else {
            viewmodel.updateEvent(onSuccess = {
                onDismiss()
            })
        }
    }


    val focusManager = LocalFocusManager.current
    ModalBottomSheet(
        onDismissRequest = {
            focusManager.clearFocus()
            onDismiss()
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .imePadding()
                .imeNestedScroll()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                if (event == null) stringResource(R.string.new_event) else stringResource(R.string.update_event),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Text(
                stringResource(R.string.event_description_subtitle),
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start,
                color = Color.Gray
            )
            PurpleTextField(
                text = stringResource(R.string.event_name_label),
                placeholder = stringResource(R.string.event_name_placeholder),
                value = state.name,
                onValueChange = { viewmodel.updateFormState(FieldType.NAME, it) },
                trailingIcon = Icons.Default.Preview
            )
            PurpleTextField(
                text = stringResource(R.string.event_date_label),
                placeholder = stringResource(R.string.event_date_placeholder),
                value = state.date,
                readOnly = true,
                onValueChange = { },
                trailingIcon = Icons.Default.CalendarMonth,
                showDatePicker = { showDatePicker = true }
            )
            if (showDatePicker) {
                DatePickerField(
                    onDateSelected = { viewmodel.updateFormState(FieldType.DATE, it) },
                    onDismiss = { showDatePicker = false }
                )
            }


            PurpleTextField(
                text = stringResource(R.string.event_description_label),
                placeholder = stringResource(R.string.event_description_placeholder),
                value = state.description ?: "",
                onValueChange = { viewmodel.updateFormState(FieldType.DESCRIPTION, it) },
                trailingIcon = null,
                imeAction = ImeAction.Done,
                onAction = onSaveAction
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveAction,
                enabled = viewmodel.isFormValid.collectAsState().value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VioletaFuerte,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = if (event != null) stringResource(R.string.update_action) else stringResource(R.string.create_event_action),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.cancel_action),
                    color = Purple40
                )
            }
        }
    }
}