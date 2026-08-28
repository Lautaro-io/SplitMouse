package com.chelo.splitmouse.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelo.splitmouse.R
import com.chelo.splitmouse.domain.model.Event
import com.chelo.splitmouse.ui.screens.components.BottomForm
import com.chelo.splitmouse.ui.screens.components.CardAddEvent
import com.chelo.splitmouse.ui.screens.components.CardEvent
import com.chelo.splitmouse.ui.screens.components.EmptyEventContent
import com.chelo.splitmouse.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navigateToDetail: (Long) -> Unit, viewmodel: MainViewModel = koinViewModel()) {
    val bgColor = Brush.verticalGradient(
        0.3f to Color.Transparent,
        1.0f to MaterialTheme.colorScheme.surfaceVariant
    )

    Scaffold(

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    stringResource(R.string.app_name),
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                )
                Icon(
                    painterResource(R.drawable.ic_app),
                    contentDescription = stringResource(R.string.app_icon_desc),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
            }
        },


        ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .background(bgColor)
                .padding(innerPadding)
                .padding(16.dp),
            eventViewModel = viewmodel,
            navigateToDetail = { navigateToDetail(it) }
        )


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    eventViewModel: MainViewModel,
    navigateToDetail: (Long) -> Unit,
) {
    val state by eventViewModel.uiState.collectAsState()
    val events = state.events
    var showBottomModal by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var eventSelected by remember { mutableStateOf<Event?>(null) }
    var dotsExpanded by remember { mutableStateOf<Long?>(null) }
    var itemsCount by remember { mutableIntStateOf(3) }
    val visibleEvents = remember(events, itemsCount) {
        events.take(itemsCount)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            state.events.isEmpty() -> EmptyEventContent(onButtonClick = { showBottomModal = true })
            else -> {

                LazyColumn() {
                    item {
                        CardAddEvent(onButtonClick = {eventSelected = null ; showBottomModal = true })
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.active_events_title),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 28.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            TextButton(onClick = { itemsCount = events.size }) {
                                Text(
                                    text = if (itemsCount == events.size) stringResource(R.string.view_less) else stringResource(R.string.view_more),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                            }

                        }
                    }
                    items(visibleEvents, key = { event -> event.id }) { event ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CardEvent(
                                event = event,
                                menuExpanded = dotsExpanded == event.id,
                                onLongPress = { id -> dotsExpanded = id },
                                onEventClick = { navigateToDetail(event.id) },
                                onDotsClick = { dotsExpanded = event.id },
                                onDismissMenu = { dotsExpanded = null },
                                onEditClick = {
                                    eventSelected = event
                                    showBottomModal = true
                                },
                                onDeleteClick = {
                                    eventSelected = event
                                    showDeleteDialog = true
                                }
                            )

                        }

                    }


                }

            }
        }

        val localContext = LocalContext.current
        eventSelected?.let { event ->
            if (showDeleteDialog) {
                DeleteDialog(
                    title = stringResource(R.string.delete_event_title),
                    label = stringResource(R.string.delete_event_confirmation, event.name),
                    onDismiss = { showDeleteDialog = false; eventSelected = null },
                    name = "",
                    onConfirm = {
                        eventSelected?.let { event ->
                            eventViewModel.deleteEvent(
                                event,
                                onSuccess = {
                                    showDeleteDialog = false; Toast.makeText(
                                    localContext,
                                    localContext.getString(R.string.event_deleted_msg),
                                    Toast.LENGTH_SHORT
                                ).show()
                                })

                        }
                        eventSelected = null


                    })
            }
        }

        AnimatedVisibility(showBottomModal) {
            BottomForm(
                eventSelected,
                onDismiss = { showBottomModal = false },
                navigate = { navigateToDetail(it) })
        }
    }
}











